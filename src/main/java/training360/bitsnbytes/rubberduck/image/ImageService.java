package training360.bitsnbytes.rubberduck.image;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private ImageDao imageDao;

    public ImageService(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public Image getImage(long id, long offset) {
        try {
            return imageDao.getImage(id, offset);
        } catch (EmptyResultDataAccessException sql) {
            // nothing to do here, JS handles default image
        }
        return null;
    }

    public void saveImage(Image image) {
        imageDao.saveImage(image);
    }
}
