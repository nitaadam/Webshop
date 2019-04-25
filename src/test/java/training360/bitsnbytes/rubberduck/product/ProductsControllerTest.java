package training360.bitsnbytes.rubberduck.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.category.Category;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ProductsControllerTest {

    @Autowired
    private ProductsController productsController;

    private TestingAuthenticationToken user = new TestingAuthenticationToken("user", "user", "ROLE_USER");
    private Category testCategory = new Category(1, "otherCategory", 1);
    private Product modifiedProduct = new Product(2L, "test5", "test5 duck",
            "test5-duck", "QuackQuack Inc.", 1000, ProductStatus.ACTIVE, testCategory, new byte[]{0});

    @Test
    public void listAllProducts() {
        List<Product> productlist = productsController.listAllProducts("", user);

        assertEquals(4, productlist.size());
    }

    @Test
    public void testSaveProduct() {

        Product firstProduct = new Product(4L, "alcapo", "Al Capo gumikacsa",
                "al-capo-gumikacsa", "QuackQuack Inc.", 2200, ProductStatus.ACTIVE, testCategory, new byte[]{0});

        productsController.saveProduct(firstProduct);

        List<Product> productList = productsController.listAllProducts("", user);

        assertEquals(5, productList.size());
    }


    @Test
    public void testUpdateProduct() {

        productsController.updateProduct(2L, modifiedProduct);
        Product foundProduct = (Product) productsController.findProductByAddress("robot-gumikacsa");
        assertEquals(foundProduct.getName(), "Robot gumikacsa");
    }

    @Test
    public void testUpdateProductWithEmptyCode() {

        ResponseStatus responseStatus = productsController.updateProduct(2L, new Product(2L, "", "Robot gumikacsa",
                "robot-gumikacsa", "QuackQuack Inc.", 1000, ProductStatus.ACTIVE, testCategory, new byte[]{0}));

        assertEquals(responseStatus.getMessage(), "Minden mezőt tölts ki!");
    }

    @Test
    public void testUpdateProductWithEmptyName() {

        ResponseStatus responseStatus = productsController.updateProduct(2L, new Product(2L, "premiumrobot", "",
                "robot-gumikacsa", "QuackQuack Inc.", 1000, ProductStatus.ACTIVE, testCategory, new byte[]{0}));

        assertEquals(responseStatus.getMessage(), "Minden mezőt tölts ki!");
    }

    @Test
    public void testUpdateProductWithEmptyAdress() {

        ResponseStatus responseStatus = productsController.updateProduct(2L, new Product(2L, "premiumrobot", "Robot gumikacsa",
                "", "QuackQuack Inc.", 1000, ProductStatus.ACTIVE, testCategory, new byte[]{0}));

        assertEquals(responseStatus.getMessage(), "Minden mezőt tölts ki!");
    }

    @Test
    public void testUpdateProductWithEmptyManufacturer() {

        ResponseStatus responseStatus = productsController.updateProduct(2L, new Product(2L, "premiumrobot", "Robot gumikacsa",
                "robot-gumikacsa", "", 1000, ProductStatus.ACTIVE, testCategory, new byte[]{0}));

        assertEquals(responseStatus.getMessage(), "Minden mezőt tölts ki!");
    }

    @Test
    public void testUpdateProductWithTooHighPrice() {

        ResponseStatus responseStatus = productsController.updateProduct(2L, new Product(2L, "premiumrobot", "Robot gumikacsa",
                "robot-gumikacsa", "QuackQuack Inc.", 10_000_000, ProductStatus.ACTIVE, testCategory, new byte[]{0}));

        assertEquals(responseStatus.getMessage(), "Az ár csak 2.000.000 forintnál nem nagyobb, pozitív egész szám lehet!");

    }

    @Test
    public void testDeleteProduct() {

        productsController.deleteProduct(3L);

        List<Product> productList = productsController.listAllProducts("", user);

        assertEquals(3, productList.size());
    }

    @Test
    public void getLastThreeSoldProducts() {



    }

}
