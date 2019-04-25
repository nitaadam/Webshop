package training360.bitsnbytes.rubberduck.basket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.bitsnbytes.rubberduck.product.Product;
import training360.bitsnbytes.rubberduck.product.ProductStatus;
import training360.bitsnbytes.rubberduck.product.ProductsDao;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class BasketsDaoTest {

    @Autowired
    private BasketsDao basketsDao;

    @Autowired
    private ProductsDao productsDao;

    @Test
    public void testWithInvalidIdFindBasketByUserId() {

        Optional<Basket> foundBasket = basketsDao.findBasketByUserId(333L);

        assertEquals(foundBasket.isPresent(), false);
    }

    @Test
    public void testFindBasketByUserId() {

        Optional<Basket> foundBasket = basketsDao.findBasketByUserId(2L);

        assertEquals(foundBasket.isPresent(), true);
        assertEquals(foundBasket.get().getId(), 22);
    }

    @Test
    public void testCreateBasket() {

        basketsDao.createBasket(4L);
        Optional<Basket> foundBasket = basketsDao.findBasketByUserId(4L);
        assertTrue(foundBasket.isPresent());
    }

    @Test
    public void testSaveBasketItem() {

        List<Product> products = productsDao.findProductByCode("professionsurgeon");
        basketsDao.saveBasketItem(new BasketItem(2L, products.get(0),2), 22);
        assertEquals(basketsDao.findProductById(2L).getCode(),"professionsurgeon");
        assertEquals(basketsDao.findProductById(2L).getName(),"Sebész gumikacsa");
        assertEquals(basketsDao.findProductById(2L).getAddress(),"sebesz-gumikacsa");
        assertEquals(basketsDao.findProductById(2L).getManufacturer(),"QuackQuack Inc.");
        assertEquals(basketsDao.findProductById(2L).getPrice(),2400);
        assertEquals(basketsDao.findProductById(2L).getProductStatus(),ProductStatus.ACTIVE);

    }

    @Test
    public void testFindBasketItemByUserAndProduct() {

        List<Product> products = productsDao.findProductByCode("professionsurgeon");
        BasketItem basketItem = basketsDao.findBasketItemByUserAndProduct(2L, products.get(0)).get();

        assertEquals(basketItem.getId(), 152);
        assertEquals(basketItem.getProduct().getCode(), "professionsurgeon");
        assertEquals(basketItem.getProduct().getName(), "Sebész gumikacsa");
        assertEquals(basketItem.getProduct().getAddress(), "sebesz-gumikacsa");
        assertEquals(basketItem.getProduct().getManufacturer(), "QuackQuack Inc.");
        assertEquals(basketItem.getProduct().getPrice(), 2400);
        assertEquals(basketItem.getProduct().getProductStatus(), ProductStatus.ACTIVE);
        assertEquals(basketItem.getQuantity(), 2);
    }

    @Test
    public void testUpdateBasketItem() {

        List<Product> products = productsDao.findProductByCode("professionsurgeon");
        boolean updateIsOk = basketsDao.updateBasketItem(new BasketItem(152,products.get(0), 3));
        BasketItem basketItem = basketsDao.findBasketItemByUserAndProduct(2L, products.get(0)).get();

        assertEquals(updateIsOk, true);
        assertEquals(basketItem.getQuantity(), 3);

    }

    @Test
    public void listBasketProducts() {

        List<Product> basketedProducts = basketsDao.listBasketProducts(2L);

        assertEquals(basketedProducts.size(), 2);
        assertEquals(basketedProducts.get(1).getCode(), "professionsurgeon");
        assertEquals(basketedProducts.get(1).getName(), "Sebész gumikacsa");
        assertEquals(basketedProducts.get(1).getAddress(), "sebesz-gumikacsa");
        assertEquals(basketedProducts.get(1).getManufacturer(), "QuackQuack Inc.");
        assertEquals(basketedProducts.get(1).getPrice(), 2400);
        assertEquals(basketedProducts.get(1).getProductStatus(), ProductStatus.ACTIVE);

    }

    @Test
    public void deleteFromBasket() {

        boolean deleteIsOk = basketsDao.deleteFromBasket(22L, 3L);
        List<Product> basketedProducts = basketsDao.listBasketProducts(2L);

        assertEquals(deleteIsOk, true);
        assertEquals(basketedProducts.size(), 1);
        assertEquals(basketedProducts.get(0).getCode(), "professionsurgeon");
        assertEquals(basketedProducts.get(0).getName(), "Sebész gumikacsa");
        assertEquals(basketedProducts.get(0).getAddress(), "sebesz-gumikacsa");
        assertEquals(basketedProducts.get(0).getManufacturer(), "QuackQuack Inc.");
        assertEquals(basketedProducts.get(0).getPrice(), 2400);
        assertEquals(basketedProducts.get(0).getProductStatus(), ProductStatus.ACTIVE);
    }


    @Test
    public void emptyBasket() {

        boolean emptyIsOk = basketsDao.emptyBasket(22L);
        List<Product> basketedProducts = basketsDao.listBasketProducts(2L);

        assertEquals(emptyIsOk, true);
        assertEquals(basketedProducts.size(), 0);
    }
}
