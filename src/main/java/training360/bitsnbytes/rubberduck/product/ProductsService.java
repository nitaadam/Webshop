package training360.bitsnbytes.rubberduck.product;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import training360.bitsnbytes.rubberduck.feedback.FeedbacksDao;
import training360.bitsnbytes.rubberduck.order.OrdersDao;
import training360.bitsnbytes.rubberduck.order.OrdersService;

import java.util.List;

@Service
public class ProductsService {

    private ProductsDao productsDao;
    private FeedbacksDao feedbacksDao;
    private OrdersDao ordersDao;
    private OrdersService ordersService;

    public ProductsService(ProductsDao productsDao, FeedbacksDao feedbacksDao, OrdersDao ordersDao, OrdersService ordersService) {
        this.productsDao = productsDao;
        this.feedbacksDao = feedbacksDao;
        this.ordersDao = ordersDao;
        this.ordersService = ordersService;
    }

    public Product findProductByAddress(String address) throws EmptyResultDataAccessException {
        return productsDao.findProductByAddress(address);
    }

    public void updateProduct(long id, Product product) {
        productsDao.updateProduct(id, product);
    }

    public List<Product> listAllProducts(String filter, Authentication authentication) {
        List<Product> productsList = null;
        if ("active".equals(filter)) {
            productsList = productsDao.listAllActiveItems() ;
        }
        else if (authentication != null && authentication.getAuthorities().toArray()[0].toString().equals("ROLE_ADMIN")){
            productsList = productsDao.listAllProducts();
        }
        else {
            productsList = productsDao.listAllActiveItems();
        }
        return productsList;
    }

    public long saveProduct(Product product) throws TransientDataAccessResourceException {
        return productsDao.saveProduct(product);
    }

    public List<Product> findProductByCode(String code) {
        return productsDao.findProductByCode(code);
    }

    public List<Product> findProductByName(String name) {
        return productsDao.findProductByName(name);
    }

    public void deleteProductById(long id) {
        productsDao.deleteProductById(id);
    }

    public List<Product> listProductsByCategory(long id) {
        return productsDao.listProductsByCategory(id);
    }

}
