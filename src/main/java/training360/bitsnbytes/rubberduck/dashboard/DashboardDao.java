package training360.bitsnbytes.rubberduck.dashboard;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class DashboardDao {
    private JdbcTemplate jdbcTemplate;

    public DashboardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int getTotalUsers(){
        List<Integer> resultList = new ArrayList<>();
        int result;
        resultList.addAll( jdbcTemplate.query("SELECT COUNT(username) AS totalUsers FROM users WHERE name != 'null'",
            (resultSet, i) -> resultSet.getInt("totalUsers")));

        result = resultList.get(0);
        return result;
    }

    public int getActiveProducts(){
        List<Integer> resultList = new ArrayList<>();
        int result;
        resultList.addAll( jdbcTemplate.query("SELECT COUNT(id) AS activeProducts FROM products WHERE status = 'ACTIVE'",
                (resultSet, i) -> resultSet.getInt("activeProducts")));

        result = resultList.get(0);
        return result;
    }

    public int getAllProducts(){
        List<Integer> resultList = new ArrayList<>();
        int result;
        resultList.addAll( jdbcTemplate.query("SELECT COUNT(id) AS totalProducts FROM products",
                (resultSet, i) -> resultSet.getInt("totalProducts")));

        result = resultList.get(0);
        return result;
    }

    public int getActiveOrders(){
        List<Integer> resultList = new ArrayList<>();
        int result;
        resultList.addAll( jdbcTemplate.query("SELECT COUNT(id) AS activeOrders FROM orders WHERE status != 'DELETED' ",
                (resultSet, i) -> resultSet.getInt("activeOrders")));

        result = resultList.get(0);
        return result;
    }

    public int getAllOrders(){
        List<Integer> resultList = new ArrayList<>();
        int result;
        resultList.addAll( jdbcTemplate.query("SELECT COUNT(id) AS allOrders FROM orders",
                (resultSet, i) -> resultSet.getInt("allOrders")));

        result = resultList.get(0);
        return result;
    }
}
