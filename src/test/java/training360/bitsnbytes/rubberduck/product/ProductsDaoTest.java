package training360.bitsnbytes.rubberduck.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.bitsnbytes.rubberduck.category.Category;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ProductsDaoTest {

    @Autowired
    private ProductsDao productsDao;

    private Category testCategory = new Category(1, "otherCategory", 1);
    TestingAuthenticationToken admin = new TestingAuthenticationToken("admin", "admin","ROLE_ADMIN");
    TestingAuthenticationToken user = new TestingAuthenticationToken("user", "user","ROLE_USER");

    @Test
    public void testFindProductByName() {

        Product foundProduct = productsDao.findProductByName("Robot gumikacsa").get(0);
        assertEquals(foundProduct.getCode(), "premiumrobot");
        assertEquals(foundProduct.getAddress(), "robot-gumikacsa");
        assertEquals(foundProduct.getManufacturer(), "QuackQuack Inc.");
        assertEquals(foundProduct.getPrice(), 3000);
        assertEquals(foundProduct.getProductStatus(), ProductStatus.ACTIVE);
    }

    @Test
    public void testFindProductByCode() {

        Product foundProduct = productsDao.findProductByCode("countrybigben").get(0);
        assertEquals(foundProduct.getName(), "Big Ben gumikacsa");
        assertEquals(foundProduct.getAddress(), "big-ben-gumikacsa");
        assertEquals(foundProduct.getManufacturer(), "QuackQuack Inc.");
        assertEquals(foundProduct.getPrice(), 2500);
        assertEquals(foundProduct.getProductStatus(), ProductStatus.DELETED);
    }

    @Test
    public void testDeleteProduct() {

        List<Product> products = productsDao.listAllProducts();

        Product product = products.stream().filter(e -> e.getName().equals("Minion gumikacsa")).findFirst().get();
        long id = product.getId();

        productsDao.deleteProductById(id);

        products = productsDao.listAllProducts();

        assertEquals(5, products.size());
    }

    @Test
    public void testUpdateProduct() {

        Product foundProduct = productsDao.findProductByCode("premiumrobot").get(0);
        productsDao.updateProduct(foundProduct.getId(), new Product(
                5L,
                "modpremiumrobot",
                "modRobot gumikacsa",
                "modrobot-gumikacsa",
                "modQuackQuack Inc.",
                2000,
                ProductStatus.ACTIVE,
                testCategory, new byte[]{}));
        foundProduct = productsDao.findProductByCode("modpremiumrobot").get(0);

        assertEquals(foundProduct.getCode(), "modpremiumrobot");
        assertEquals(foundProduct.getName(), "modRobot gumikacsa");
        assertEquals(foundProduct.getAddress(), "modrobot-gumikacsa");
        assertEquals(foundProduct.getManufacturer(), "modQuackQuack Inc.");
        assertEquals(foundProduct.getPrice(), 2000);
        assertEquals(foundProduct.getProductStatus(), ProductStatus.ACTIVE);
    }

    @Test
    public void testListAllProducts() {

        List<Product> products = productsDao.listAllProducts();

        assertEquals(5, products.size());
    }
}
