package training360.bitsnbytes.rubberduck.report;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import training360.bitsnbytes.rubberduck.order.Order;
import training360.bitsnbytes.rubberduck.order.OrderStatus;

import java.util.List;

@Repository
public class ReportDao {

    private JdbcTemplate jdbcTemplate;

    public ReportDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Report1> listReportByDateAndStatus() {
        return jdbcTemplate.query("SELECT year(orders.orderdate), month(orders.orderdate), status, count(*), sum(price) " +
                "FROM orders JOIN order_items on order_items.order_id = orders.id " +
                "GROUP BY year(orders.orderdate), month(orders.orderdate), status", (rs, i) -> new Report1(
                        rs.getInt("year(orders.orderdate)"),
                        rs.getInt("month(orders.orderdate)"),
                        OrderStatus.valueOf(rs.getString("status")),
                        rs.getInt("count(*)"),
                        rs.getLong("sum(price)"))
        );
    }


    public List<Report2> listReportByDateAndProduct() {
        return jdbcTemplate.query("SELECT year(orders.orderdate), month(orders.orderdate), products.code, products.price, sum(order_items.quantity) as quantity, sum(order_items.price * order_items.quantity) as totalprice " +
                "FROM order_items" +
                        " JOIN products on order_items.product_id = products.id " +
                "JOIN orders on order_items.order_id = orders.id " +
                "WHERE orders.status = 'DELIVERED' GROUP BY year(orders.orderdate), month(orders.orderdate), products.code", (rs, i) -> new Report2(
                        rs.getInt("year(orders.orderdate)"),
                        rs.getInt("month(orders.orderdate)"),
                        rs.getString("products.code"),
                        rs.getInt("quantity"),
                        rs.getLong("products.price"),
                        rs.getLong("totalprice"))
                );
    }


}

