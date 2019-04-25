package training360.bitsnbytes.rubberduck.feedback;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import training360.bitsnbytes.rubberduck.user.User;

import java.sql.*;
import java.util.Date;
import java.util.List;

@Repository
public class FeedbacksDao {
    private JdbcTemplate jdbcTemplate;

    public FeedbacksDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Feedback> listFeedbacksByUserId(long userId) {
        return jdbcTemplate.query("select feedbacks.id, product_id, user_id, stars,review, date, username, name from feedbacks join users on feedbacks.user_id = users.id where user_id = ?",
                (rs, i) -> new Feedback(rs.getLong("id"),
                        rs.getLong("product_id"),
                        new User(rs.getLong("user_id"), rs.getString("name"), rs.getString("username")),
                        rs.getInt("stars"),
                        rs.getString("review"),
                        rs.getTimestamp("date").toLocalDateTime()), userId);
    }

    public void createFeedback(Feedback feedback) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("insert into feedbacks ( product_id, user_id, stars, review, date ) values (?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, feedback.getProductId());
                ps.setLong(2, feedback.getUser().getId());
                ps.setInt(3, feedback.getStars());
                ps.setString(4, feedback.getReview());
                ps.setTimestamp(5, new Timestamp(new Date().getTime()));
                return ps;
            }
        }, keyHolder);
    }


    public void modifyFeedback(Feedback feedback, long userId, long productId){
        jdbcTemplate.update("UPDATE feedbacks SET review = ?, stars = ? WHERE user_id= ? AND product_id = ?",
                feedback.getReview(),feedback.getStars(),userId,productId);
    }

    public void deleteFeedbackById(long id){
        jdbcTemplate.update("DELETE FROM feedbacks WHERE id= ? ", id);
    }

    public FeedbackData getNumberOfDeliveredOrdersByUserForProduct(long userId, long productId){

        String sqlDelivered = "select * from products join \n" +
                "order_items on order_items.product_id = products.id join orders on orders.id=order_items.order_id\n" +
                "join users on users.id=orders.user_id \n" +
                "where users.id=? and products.id=? and orders.status='DELIVERED'";

        String sqlFeedbacks = "select *from feedbacks where user_id =? and product_id = ?";

        RowCountCallbackHandler handlerDelivered = new RowCountCallbackHandler();

        RowCountCallbackHandler handlerFeedbacks = new RowCountCallbackHandler();

        jdbcTemplate.query(sqlDelivered, handlerDelivered, userId, productId);
        jdbcTemplate.query(sqlFeedbacks, handlerFeedbacks, userId, productId);

        return new FeedbackData(handlerDelivered.getRowCount(), handlerFeedbacks.getRowCount());

    }

    public List<Feedback> listFeedbacksByProductId(long productId){
        return jdbcTemplate.query("select feedbacks.id,feedbacks.product_id, feedbacks.user_id, feedbacks.stars,feedbacks.review,feedbacks.date, users.name, users.username as username FROM feedbacks JOIN products ON feedbacks.product_id = products.id JOIN users ON feedbacks.user_id = users.id WHERE products.id = ? AND products.status = 'ACTIVE' order by feedbacks.date DESC",
                (rs, i) -> new Feedback(rs.getLong("id"),
                        rs.getLong("product_id"),
                        new User(rs.getLong("user_id"), rs.getString("users.name"), rs.getString("username")),
                        rs.getInt("stars"),
                        rs.getString("review"),
                        rs.getTimestamp("date").toLocalDateTime()
                ), productId);
    }

}

