package training360.bitsnbytes.rubberduck.product;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.bitsnbytes.rubberduck.category.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProductsDao {

    private JdbcTemplate jdbcTemplate;
    private ProductAddressGenerator productAddressGenerator = new ProductAddressGenerator();

    public ProductsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findProductByAddress(String address) throws EmptyResultDataAccessException {

            return jdbcTemplate.queryForObject("SELECT products.id, code, products.name, address, manufacturer, price, status, categories.id, categories.name, categories.view_order, image FROM products JOIN categories ON products.category_id = categories.id WHERE address=?", (rs, i) -> new Product(
                    rs.getLong("id"),
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("manufacturer"),
                    rs.getInt("price"),
                    ProductStatus.valueOf(rs.getString("status")),
                    new Category(rs.getLong("categories.id"), rs.getString("categories.name"), rs.getInt("categories.view_order")),
                    rs.getBytes("image")
            ), address);

    }

    public List<Product> listAllActiveItems(){
        return jdbcTemplate.query("SELECT products.id, code, products.name, address, manufacturer, price, status, categories.id, categories.name, categories.view_order, image FROM products JOIN categories ON products.category_id = categories.id where status = 'ACTIVE' order by categories.view_order, products.name, manufacturer", new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int i) throws SQLException {
                return new Product(rs.getLong("id"), rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("manufacturer"),
                        rs.getInt("price"),
                        ProductStatus.valueOf(rs.getString("status")),
                new Category(rs.getLong("categories.id"), rs.getString("categories.name"), rs.getInt("categories.view_order")),
                        rs.getBytes("image"));
             }
        });
    }

    public long saveProduct(Product product) throws TransientDataAccessResourceException {
          KeyHolder keyHolder = new GeneratedKeyHolder();
          jdbcTemplate.update(connection -> {
                      PreparedStatement ps = connection.prepareStatement("insert into products (code, name, address, manufacturer, price, status, category_id, image) values (?,?,?,?,?,?,?,?)",
                              Statement.RETURN_GENERATED_KEYS);
                      ps.setString(1, product.getCode());
                      ps.setString(2, product.getName());
                      ps.setString(3, productAddressGenerator.generateAddress(product));
                      ps.setString(4, product.getManufacturer());
                      ps.setInt(5, product.getPrice());
                      ps.setString(6, product.getProductStatus().name());
                      ps.setLong(7, product.getCategory().getId());
                      ps.setBytes(8, product.getImage());
                      return ps;
                  }, keyHolder
          );
          long id = keyHolder.getKey().longValue();
          updateProductAddress(findProductById(id));
          return id;
    }

    private void updateProductAddress(Product product) {
        jdbcTemplate.update("update products set address = ? where id = ?", productAddressGenerator.generateAddress(product),  product.getId());
    }

    private Product findProductById(long id){
        return jdbcTemplate.queryForObject("select id, code, name, address, manufacturer, price, status from products where id =?", (rs, i) -> new Product(
                rs.getLong("id"),
                rs.getString("code"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("manufacturer"),
                rs.getInt("price"),
                ProductStatus.valueOf(rs.getString("status"))
        ), id);
    }

    public void updateProduct(long id, Product product) {
        jdbcTemplate.update("UPDATE products SET code=?, name=?, address=?, manufacturer=?, price=?, category_id=? where id=?",
                product.getCode(),
                product.getName(),
                product.getAddress(),
                product.getManufacturer(),
                product.getPrice(),
                product.getCategory().getId(),
                id);
    }

    public List<Product> findProductByName(String name){
        return jdbcTemplate.query("SELECT id,code, name, address, manufacturer, price, status FROM products WHERE name=? order by name, manufacturer", (rs, i) -> new Product(
                rs.getLong("id"),
                rs.getString("code"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("manufacturer"),
                rs.getInt("price"),
                ProductStatus.valueOf(rs.getString("status"))
        ), name);
    }

    public List<Product> findProductByCode(String code){
        return jdbcTemplate.query("SELECT id,code, name, address, manufacturer, price, status FROM products WHERE code=? order by name, manufacturer", (rs, i) -> new Product(
                rs.getLong("id"),
                rs.getString("code"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("manufacturer"),
                rs.getInt("price"),
                ProductStatus.valueOf(rs.getString("status"))
        ), code);
    }



    public List<Product> listAllProducts() {
        return jdbcTemplate.query("SELECT products.id, code, products.name, address, manufacturer, price, status, categories.id, categories.name, categories.view_order, image FROM products JOIN categories ON products.category_id = categories.id order by products.name, manufacturer", (rs, i) -> new Product(
                rs.getLong("id"),
                rs.getString("code"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("manufacturer"),
                rs.getInt("price"),
                ProductStatus.valueOf(rs.getString("status")),
                new Category(rs.getLong("categories.id"), rs.getString("categories.name"), rs.getInt("categories.view_order")),
                rs.getBytes("image")));
    }

    public void deleteProductById(long id) {
        jdbcTemplate.update("update products set status = ? where id = ?", ProductStatus.DELETED.name(), id);
    }

    public List<Product> listProductsByOrderId(long orderId){
        return jdbcTemplate.query("SELECT products.id, products.code, products.name, products.address, products.manufacturer, products.price, products.status " +
                "FROM products JOIN order_items ON products.id=order_items.product_id JOIN orders ON order_items.order_id=orders.id " +
                "WHERE orders.id=?", (rs, i) -> new Product(
                        rs.getLong("products.id"),
                        rs.getString("products.code"),
                        rs.getString("products.name"),
                        rs.getString("products.address"),
                        rs.getString("products.manufacturer"),
                        rs.getInt("products.price"),
                        ProductStatus.valueOf(rs.getString("products.status"))
               ), orderId);
    }

    public List<Product> listProductsByCategory(long id) {
        return jdbcTemplate.query("SELECT products.id, code, products.name, address, manufacturer, price, status, image, categories.id, categories.name, categories.view_order FROM products JOIN categories ON products.category_id = categories.id WHERE products.status = 'Active' AND categories.id = ? order by products.name, manufacturer", (rs, i) -> new Product(
                rs.getLong("id"),
                rs.getString("code"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("manufacturer"),
                rs.getInt("price"),
                ProductStatus.valueOf(rs.getString("status")),
                new Category(
                        rs.getLong("categories.id"),
                        rs.getString("categories.name"),
                        rs.getInt("categories.view_order")),
                rs.getBytes("image")), id);
    }


//    public void saveImage(Image image){
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(connection -> {
//                    PreparedStatement ps = connection.prepareStatement("insert into products (image) values (?) where id = 1",
//                            Statement.RETURN_GENERATED_KEYS);
//                    ps.setString(1, product.getCode());
//                    ps.setString(2, product.getName());
//                    ps.setString(3, productAddressGenerator.generateAddress(product));
//                    ps.setString(4, product.getManufacturer());
//                    ps.setInt(5, product.getPrice());
//                    ps.setString(6, product.getProductStatus().name());
//                    ps.setLong(7, product.getCategory().getId());
//                    return ps;
//                },keyHolder
//        );
////        long id = keyHolder.getKey().longValue();
//        updateProductAddress(findProductById(id));
//    }
}
