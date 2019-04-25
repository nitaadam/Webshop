package training360.bitsnbytes.rubberduck.product;

import com.mysql.cj.jdbc.exceptions.PacketTooBigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.Validator;
import training360.bitsnbytes.rubberduck.category.Category;
import training360.bitsnbytes.rubberduck.feedback.Feedback;
import training360.bitsnbytes.rubberduck.order.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductsController {

    private ProductsService productsService;

    private OrdersService ordersService;

    private Validator validator = new Validator();

    public ProductsController(ProductsService productsService, OrdersService ordersService) {
        this.productsService = productsService;
        this.ordersService = ordersService;
    }

    @GetMapping("/api/products/{address}")
    public Object findProductByAddress(@PathVariable String address) {
        try {
            return productsService.findProductByAddress(address);
        }
        catch (EmptyResultDataAccessException e){
            return new ResponseStatus(false, "Érvénytelen cím");
        }
    }

    @GetMapping("/api/products")
    public List<Product> listAllProducts(@RequestParam(required = false) String filter, Authentication authentication) {
        return productsService.listAllProducts(filter, authentication);
    }

    @GetMapping("/api/products/last3")
    public List<Product> getLastThreeSoldProducts(){
        List<String> productAddresses = new ArrayList<>();
        int counter = 0;
        List<Order> activeOrders = ordersService.listAllOrdersAsAdmin(false);
        for (Order order: activeOrders){
            for (OrderItem orderItem: order.getOrderItemList()){
                if (order.getOrderStatus() != OrderStatus.DELETED  && !productAddresses.contains(orderItem.getProduct().getAddress()) && counter<3 && orderItem.getProduct().getProductStatus() == ProductStatus.ACTIVE){
                    productAddresses.add(orderItem.getProduct().getAddress());
                    counter++;
                }
            }
        }
        List<Product> lastThreeProducts = new ArrayList<>();
        for (String address: productAddresses){
            lastThreeProducts.add((Product)findProductByAddress(address));
        }
        return lastThreeProducts;
    }


    @PostMapping("/api/products/{id}")
    public ResponseStatus updateProduct(@PathVariable long id, @RequestBody Product product) {

        if (!validator.allFieldsPopulatedUpdate(product)) {
            return new ResponseStatus(false, "Minden mezőt tölts ki!");
        }
        if (!validator.isPriceInRange(product.getPrice())) {
            return new ResponseStatus(false, "Az ár csak 2.000.000 forintnál nem nagyobb, pozitív egész szám lehet!");
        }
        if (!isCodeUnique(product.getCode(), id)) {
            return new ResponseStatus(false, "A termékkódhoz már tartozik termék az adatbázisban!");
        }
        if (!isAddressUnique(product)){
            return new ResponseStatus(false, "Ez az URL már foglalt, válassz másikat!");
        }
        productsService.updateProduct(id, product);
        return new ResponseStatus(true, "Termékadatok sikeresen módosítva.");
    }

    private boolean isAddressUnique(Product product) {
        try {
            return productsService.findProductByAddress(product.getAddress().trim()).getName().trim().equals(product.getName().trim()) ||
                    product.getId().equals(productsService.findProductByAddress(product.getAddress().trim()).getId());
        }
        catch (DataAccessException d){
            return true;
        }
    }

    @DeleteMapping("/api/products/{id}")
    public void deleteProduct(@PathVariable long id) {
        productsService.deleteProductById(id);
    }

    @PostMapping("/api/products")
    public ResponseStatus saveProduct(@RequestBody Product product) {
        try {
            if (!validator.allFieldsPopulated(product)) {
                return new ResponseStatus(false, "Minden mezőt tölts ki!");
            }
            if (!validator.isPriceInRange(product.getPrice())) {
                return new ResponseStatus(false, "Az ár 2.000.000 forintnál nem nagyobb, pozitív egész szám lehet");
            }
            if (!isCodeUnique(product.getCode())) {
                return new ResponseStatus(false, "A termékkódhoz már tartozik termék az adatbázisban!");
            }
            productsService.saveProduct(product);
            return new ResponseStatus(true, "Termék sikeresen létrehozva!");
        } catch (TransientDataAccessResourceException p) {
            return new ResponseStatus(false, "A kép mérete túl nagy!");
        }
    }

    private boolean isCodeUnique(String code, long id) {
        return productsService.findProductByCode(code.trim()).isEmpty() ||
                productsService.findProductByCode(code.trim()).get(0).getId() == id;
    }

    private boolean isCodeUnique(String code) {
        return productsService.findProductByCode(code).isEmpty();
    }



}
