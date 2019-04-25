package training360.bitsnbytes.rubberduck.basket;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.bitsnbytes.rubberduck.product.Product;
import training360.bitsnbytes.rubberduck.product.ProductStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class BasketsDao {


    private JdbcTemplate jdbcTemplate;

    public BasketsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Basket> findBasketByUserId(long userId) {
        try {
            Basket basket = jdbcTemplate.queryForObject("select id from baskets where user_id = ?", (resultSet, i) ->
                    new Basket(resultSet.getLong("id")), userId);
            return Optional.of(basket);
        }
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Basket createBasket(long userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("insert into baskets (user_id) values (?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, userId);
                return ps;
            }
        }, keyHolder
        );
        return new Basket(keyHolder.getKey().longValue());
    }

    public void saveBasketItem(BasketItem basketItem, long basketId) {
        jdbcTemplate.update("insert into basket_items (basket_id, product_id, quantity) values (?, ?, ?)", basketId, basketItem.getProduct().getId(), basketItem.getQuantity());
    }

    public List<BasketItem> listBasketItems(Basket basket){
        return jdbcTemplate.query("SELECT basket_items.id, product_id, quantity, " +
                "products.id, products.code, products.name, products.address, products.manufacturer, products.price, " +
                "products.status, image FROM basket_items JOIN baskets ON baskets.id=basket_items.basket_id JOIN products " +
                "ON basket_items.product_id=products.id WHERE baskets.id=?", (rs, i) -> new BasketItem(
                        rs.getLong("basket_items.id"),
                        new Product(
                                rs.getLong("products.id"),
                                rs.getString("products.code"),
                                rs.getString("products.name"),
                                rs.getString("products.address"),
                                rs.getString("products.manufacturer"),
                                rs.getInt("products.price"),
                                ProductStatus.valueOf(rs.getString("products.status"))),
                        rs.getInt("basket_items.quantity")), basket.getId());
    }

    public boolean updateBasketItem(BasketItem basketItem) {
        int affectedRows = jdbcTemplate.update("UPDATE basket_items SET quantity=? WHERE basket_items.id=?", basketItem.getQuantity(), basketItem.getId());
        return affectedRows == 1;
    }

    public Optional<BasketItem> findBasketItemByUserAndProduct(long userId,  Product product) {

        List<BasketItem> prevBasketItems = jdbcTemplate.query(
                "SELECT basket_items.id,quantity FROM basket_items JOIN baskets ON baskets.id=basket_items.basket_id WHERE basket_items.product_id=? AND baskets.user_id=?;",
                (rs, i) -> new BasketItem(rs.getLong("basket_items.id"), product, rs.getInt("quantity")), product.getId(), userId);
        return prevBasketItems.isEmpty()?Optional.empty():Optional.of(prevBasketItems.get(0));
    }



    public List<Product> listBasketProducts(long userId) {
        return jdbcTemplate.query(
                "SELECT  products.id, products.code, products.name, products.address, products.manufacturer, products.price, products.status \n" +
                        "    FROM products\n" +
                        "    JOIN basket_items ON products.id=basket_items.product_id\n" +
                        "    JOIN baskets ON baskets.id=basket_items.basket_id\n" +
                        "    JOIN users ON users.id=baskets.user_id\n" +
                        "    WHERE users.id=?\n" +
                        "    ORDER BY products.name;", (rs, i) -> new Product(
                        rs.getLong("products.id"),
                        rs.getString("products.code"),
                        rs.getString("products.name"),
                        rs.getString("products.address"),
                        rs.getString("manufacturer"),
                        rs.getInt("price"),
                        ProductStatus.valueOf(rs.getString("status"))),
         userId);
    }

    public Product findProductById(long id){
        return jdbcTemplate.query("SELECT id,code, name, address, manufacturer, price, status FROM products WHERE id=?", (rs, i) -> new Product(
                rs.getLong("id"),
                rs.getString("code"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("manufacturer"),
                rs.getInt("price"),
                ProductStatus.valueOf(rs.getString("status"))
        ), id).get(0);
    }

    public boolean deleteFromBasket(long basketId, long productId) {

        int affectedRows = jdbcTemplate.update("DELETE from basket_items where product_id = ? AND basket_id = ?", productId, basketId);
        return affectedRows != 0;
    }

    public boolean emptyBasket(long basketId) {
        int affectedRows = jdbcTemplate.update("delete from basket_items where basket_id = ?", basketId);
        return affectedRows != 0;
    }


}
