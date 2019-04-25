package training360.bitsnbytes.rubberduck.order;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.bitsnbytes.rubberduck.basket.BasketItem;
import training360.bitsnbytes.rubberduck.product.Product;
import training360.bitsnbytes.rubberduck.product.ProductStatus;
import training360.bitsnbytes.rubberduck.user.User;

import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import java.util.Locale;

@Repository
public class OrdersDao {

    private JdbcTemplate jdbcTemplate;

    public OrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Order createNewOrder(long userId, String address) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders (user_id, orderdate, status, delivery_address) values (?, ?, ?, ?)",
                                    Statement.RETURN_GENERATED_KEYS);
                            ps.setLong(1, userId);
                            ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                            ps.setString(3, "ACTIVE");
                            ps.setString(4, address);
                            return ps;
                        }
                    }, keyHolder
        );
        return new Order(keyHolder.getKey().longValue(), LocalDateTime.now(), OrderStatus.ACTIVE, address);
    }

    public void saveOrderItem(long orderId, BasketItem basketItem) {
        jdbcTemplate.update("INSERT INTO order_items (order_id, product_id, price, quantity) VALUES (?, ?, ?, ?)",
                orderId, basketItem.getProduct().getId(), basketItem.getProduct().getPrice(), basketItem.getQuantity());
    }

    public List<Order> listOrdersByUserId(long userId) {
        List<Order> orderList = jdbcTemplate.query(
                "SELECT orders.orderdate, orders.id, orders.status, orders.delivery_address, SUM(order_items.price * order_items.quantity) AS sum " +
                        "FROM orders JOIN users ON orders.user_id = users.id JOIN order_items ON orders.id=order_items.order_id " +
                        "WHERE users.id = ? " +
                        "GROUP BY orders.orderdate, orders.id, orders.status, orders.delivery_address " +
                        "ORDER BY orders.orderdate DESC", (rs, i) -> new Order(
                        rs.getLong("orders.id"),
                        rs.getTimestamp("orders.orderdate").toLocalDateTime(),
                        OrderStatus.valueOf(rs.getString("orders.status")),
                        rs.getString("orders.delivery_address"),
                        rs.getInt("sum")),
                userId);
        return orderList;
    }

    public List<Order> listAllOrdersAsAdmin() {
        List<Order> listAllOrders = jdbcTemplate.query(
            "SELECT users.id, users.name, users.username, orders.id, orders.orderdate, orders.status, " +
                    "orders.delivery_address, SUM(order_items.price * order_items.quantity) AS sum, " +
                    "order_items.id, order_items.order_id, " +
                    "order_items.product_id, order_items.price, order_items.quantity "+
                    "FROM users JOIN orders ON users.id=orders.user_id JOIN order_items ON orders.id=order_items.order_id " +
                    "JOIN products ON order_items.product_id=products.id " +
                    "GROUP BY users.id, users.name, users.username, orders.id, orders.orderdate, orders.status " +
                    "ORDER BY orders.orderdate DESC", (rs, i) -> new Order(
                        rs.getLong("orders.id"),
                        rs.getTimestamp("orders.orderdate").toLocalDateTime(),
                        OrderStatus.valueOf(rs.getString("orders.status")),
                        rs.getString("orders.delivery_address"),
                        new User(
                            rs.getLong("users.id"),
                            rs.getString("users.name"),
                            rs.getString("users.username")),
                        rs.getInt("sum")));
        return listAllOrders;
    }

    public List<Order> listActiveOrdersAsAdmin() {
        List<Order> listAllOrders = jdbcTemplate.query(
                "SELECT users.id, users.name, users.username, orders.id, orders.orderdate, orders.status, " +
                        "orders.delivery_address, SUM(order_items.price * order_items.quantity) AS sum, " +
                        "order_items.id, order_items.order_id, " +
                        "order_items.product_id, order_items.price, order_items.quantity " +
                        "FROM users JOIN orders ON users.id=orders.user_id JOIN order_items ON orders.id=order_items.order_id " +
                        "JOIN products ON order_items.product_id=products.id " +
                        "WHERE orders.status='ACTIVE' " +
                        "GROUP BY users.id, users.name, users.username, orders.id, orders.orderdate, orders.status " +
                        "ORDER BY orders.orderdate DESC", (rs, i) -> new Order(
                        rs.getLong("orders.id"),
                        rs.getTimestamp("orders.orderdate").toLocalDateTime(),
                        OrderStatus.valueOf(rs.getString("orders.status")),
                        rs.getString("orders.delivery_address"),
                        new User(
                                rs.getLong("users.id"),
                                rs.getString("users.name"),
                                rs.getString("users.username")),
                        rs.getInt("sum")));
        return listAllOrders;
    }

    public List<OrderItem> listOrderItemsByOrder(Order order){
        List<OrderItem> orderItemList = jdbcTemplate.query("SELECT order_items.id, order_items.order_id, " +
                "order_items.product_id, order_items.price, order_items.quantity, products.id, products.code, "+
                "products.name, products.address, products.manufacturer, products.price, products.status, image " +
                "FROM order_items JOIN orders ON order_items.order_id=orders.id JOIN products ON order_items.product_id=products.id " +
                "WHERE orders.id = ?", (rs, i) -> new OrderItem(
                        rs.getLong("order_items.id"),
                        new Product(
                            rs.getLong("products.id"),
                            rs.getString("products.code"),
                            rs.getString("products.name"),
                            rs.getString("products.address"),
                            rs.getString("products.manufacturer"),
                            rs.getInt("products.price"),
                            ProductStatus.valueOf(rs.getString("products.status"))),
                        rs.getInt("order_items.price"),
                        rs.getInt("order_items.quantity")),
                        order.getId());
        return orderItemList;
    }

    public void deleteOrderItem(long id, long productdId) {
        jdbcTemplate.update("DELETE FROM order_items WHERE order_id=? AND product_id=?", id, productdId);
    }

    public void deleteOrderById(long id) {
        jdbcTemplate.update("UPDATE orders SET status = ? WHERE id = ?", OrderStatus.DELETED.name(), id);
    }

    public void changeOrderStatusToDeliveredByOrderId(long id) {
        jdbcTemplate.update("UPDATE orders SET status = ? WHERE id = ?", OrderStatus.DELIVERED.name(), id);
    }
}

