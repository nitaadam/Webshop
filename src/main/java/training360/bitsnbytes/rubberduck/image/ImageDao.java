package training360.bitsnbytes.rubberduck.image;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ImageDao {

    private JdbcTemplate jdbcTemplate;

    public ImageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Image getImage(long ProductId, long offset) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject("select id, image_file, file_type, file_name, product_id from images where product_id = ? LIMIT 1 OFFSET ?", new ImageRowMapper(), ProductId, offset);
    }

    public void saveImage(Image image) {
        try {
            jdbcTemplate.update("insert into images (image_file, file_type, file_name, product_id) values (?, ?, ?, ?);", image.getFileBytes(), image.getMediaType().toString(), image.getFileName(), image.getProductId());

        } catch (DataAccessException daex) {
            System.out.println(daex.getMessage());
            throw new IllegalArgumentException("Cannot save image", daex);
        }
    }

    private static class ImageRowMapper implements RowMapper<Image> {
        @Override
        public Image mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            byte[] imageBytes = resultSet.getBytes("image_file");
            MediaType mediaType = MediaType.parseMediaType(resultSet.getString("file_type"));
            String fileName = resultSet.getString("file_name");
            long product_id = resultSet.getLong("product_id");
            return new Image(id, imageBytes, mediaType, fileName, product_id);
        }
    }
}