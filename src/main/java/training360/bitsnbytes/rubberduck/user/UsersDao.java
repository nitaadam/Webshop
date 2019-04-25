package training360.bitsnbytes.rubberduck.user;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersDao {

    private JdbcTemplate jdbcTemplate;

    public UsersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
           String userName = resultSet.getString("username");
            String password = resultSet.getString("password");
           UserRole userRole = UserRole.valueOf(resultSet.getString("role"));
            int enabled = resultSet.getInt("enabled");
            return new User(id, name, userName, password, enabled, userRole);
        }
    }

    public long createUser(User user) {
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                    PreparedStatement ps = connection.prepareStatement("insert into users " +
                                            "(name, username, " +
                                            "password, enabled, role) values " +
                                            "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                                    ps.setString(1, user.getName());
                                    ps.setString(2, user.getUserName());
                                    ps.setString(3, user.getPassword());
                                    ps.setInt(4, 1);
                                    ps.setString(5, user.getUserRole().name());
                                    return ps;
                                }
                            }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public Optional<User> findUserByUserName(String name) {
        try {
            User user = jdbcTemplate.queryForObject("select id, name, username, password, enabled, role from users where username = ?",
                    new UserMapper(),
                    name);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> findUserByName(String name) {
        return jdbcTemplate.query("select id, name, username, password, enabled, role from users where name = ?", (rs, i) -> new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getInt("enabled"),
                UserRole.valueOf(rs.getString("role"))
        ), name);
    }

    public Optional<User> findUserByUserId(long userId){
        try {
            User user = jdbcTemplate.queryForObject("select id, name, username, password, enabled, role from users where id = ?",
                    new UserMapper(),
                    userId);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> listAllUser(){
        return jdbcTemplate.query("select id,name,username,password,enabled,role from users",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name"),rs.getString("username"),
                        rs.getString("password"),rs.getInt("enabled"), UserRole.valueOf(rs.getString("role"))));
    }

    public List<User> listAllActiveUser(){
        return jdbcTemplate.query("SELECT id,name,username,password,enabled,role FROM users where name<> 'null'",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name"),rs.getString("username"),
                        rs.getString("password"),rs.getInt("enabled"), UserRole.valueOf(rs.getString("role"))));
    }

    public void logicalDeleteUser(String userName) {
        String role = "ROLE_NOT_AUTHORIZED";
        jdbcTemplate.update("UPDATE users SET name = 'null', username = 'null'," +
                "password = 'null', enabled = 0, role = ? where username = ?",role,userName);
    }

    public void modifyUser(String userName, User user){
        jdbcTemplate.update("UPDATE users SET name = ?, password = ? WHERE username = ?",user.getName(),user.getPassword(),userName);
    }
}
