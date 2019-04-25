package training360.bitsnbytes.rubberduck.category;

import ch.qos.logback.core.util.COWArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.bitsnbytes.rubberduck.product.Product;
import training360.bitsnbytes.rubberduck.product.ProductsDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CategoryDao {

    private JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public long createCategory(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into categories (name, view_order) values (?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            ps.setInt(2, category.getViewOrder());
            return ps;
        }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public List<Category> listCategories() {
        return jdbcTemplate.query("select id, name, view_order from categories order by view_order", new RowMapper<Category>() {
            @Override
            public Category mapRow(ResultSet rs, int i) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int viewOrder = rs.getInt("view_order");
                return new Category(id, name, viewOrder);
            }
        });
    }

    public Category getTopViewOrder() {
        return jdbcTemplate.queryForObject("select id, name, view_order from categories order by view_order desc limit 1", (rs, i) -> new Category(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("view_order")
        ));
    }

    public void unshiftViewOrder(int oldOrder) {
        jdbcTemplate.update("update categories set view_order = view_order -1 where view_order > ?", oldOrder);
    }

    public void shiftViewOrder(int oldOrder, int newOrder) {
        jdbcTemplate.update("update categories set view_order = view_order + 1 " +
                        "where view_order < ? AND view_order >= ?",
                oldOrder, newOrder );
    }

    public void shiftViewOrder(int view_order) {
        jdbcTemplate.update("update categories set view_order = view_order + 1 " +
                        "where view_order >= ?",
                view_order);
    }


    public Category findCategoryById(long id) {
        return jdbcTemplate.queryForObject("select id, name, view_order from categories where id = ?", (rs, i) -> new Category(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("view_order")), id
        );
    }

    public void deleteCategory(long id) {
        long toBeDeletedViewOrder = findCategoryById(id).getViewOrder();
       jdbcTemplate.update("update products set category_id = 6 where category_id = ?", id);
        jdbcTemplate.update("delete from categories where id = ?", id);
        jdbcTemplate.update("update categories set view_order = view_order - 1 where view_order > ?", toBeDeletedViewOrder);

    }

//    public void unshiftViewOrder(long id) {
//        for (int i = findCategoryById(id).getViewOrder(); i <= getTopViewOrder().getViewOrder(); i++) {
//            jdbcTemplate.update("update categories set view_order = view_order - 1 where view_order = ?", id);
//        }
//    }

    public void unshiftViewOrder(int oldOrder, int newOrder) {
        jdbcTemplate.update("update categories set view_order = view_order - 1 where view_order > ? AND view_order <= ?",
                oldOrder, newOrder );
    }

    public void updateCategory(Category category) {
        jdbcTemplate.update("update categories set name = ?, view_order = ? where id = ?", category.getName(), category.getViewOrder(), category.getId());
    }

    public void updateCategoryName(Category category) {
        jdbcTemplate.update("update categories set name = ? where id = ?", category.getName(), category.getId());
    }


}
