//package training360.bitsnbytes;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.TestingAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//import training360.bitsnbytes.rubberduck.basket.Basket;
//import training360.bitsnbytes.rubberduck.basket.BasketItem;
//import training360.bitsnbytes.rubberduck.basket.BasketsController;
//import training360.bitsnbytes.rubberduck.order.Order;
//import training360.bitsnbytes.rubberduck.order.OrdersController;
//import training360.bitsnbytes.rubberduck.product.Product;
//import training360.bitsnbytes.rubberduck.product.ProductStatus;
//import training360.bitsnbytes.rubberduck.product.ProductsController;
//
//import java.util.List;
//
//import static junit.framework.TestCase.assertEquals;
//import static org.hamcrest.core.IsEqual.equalTo;
//import static org.junit.Assert.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Sql(scripts = "/init.sql")
//public class BitsnbytesApplicationTests {
//
//    @Autowired
//    private OrdersController ordersController;
//
//	@Autowired
//	private ProductsController productsController;
//
//	@Autowired
//	private BasketsController basketsController;
//
//    TestingAuthenticationToken admin = new TestingAuthenticationToken("adminka", "alma","ROLE_ADMIN");
//    TestingAuthenticationToken user = new TestingAuthenticationToken("userke", "user1","ROLE_USER");
//	TestingAuthenticationToken visitor = new TestingAuthenticationToken("", "","ROLE_NOT_AUTHORIZED");
//
//	@Test
//	public void contextProductLoads() {
//		productsController.saveProduct(new Product(null, "a", "a",
//				"a", "QuackQuack Inc.", 3500, ProductStatus.ACTIVE));
//		//List<Product> products = productsController.listAllProducts("active");
//		List<Product> products = productsController.listAllProducts("active", user);
//
//
//		assertEquals(3, products.size());
//		assertEquals("a", products.get(0).getName());
//	}
//
//	@Test
//	public void testFindProductByAddress() {
//
//		Product foundProduct = productsController.findProductByAddress("test1-gumikacsa");
//		assertThat(foundProduct.getName(), equalTo("test1 gumikacsa"));
//		assertEquals(foundProduct.getCode(), "test1");
//		assertEquals(foundProduct.getName(), "test1 gumikacsa");
//		assertEquals(foundProduct.getAddress(), "test1-gumikacsa");
//		assertEquals(foundProduct.getManufacturer(), "QuackQuack Inc.");
//		assertEquals(foundProduct.getPrice(), 2000);
//		assertEquals(foundProduct.getProductStatus(), ProductStatus.ACTIVE);
//	}
//
//	@Test
//	public void testDeleteProduct() {
//		// Given
//		productsController.saveProduct(new Product(4512L, "b", "b",
//				"b", "QuackQuack Inc.", 3900, ProductStatus.ACTIVE));
//
//		List<Product> products = productsController.listAllProducts("active", user);
//
//		Product product = products.stream().filter(e -> e.getName().equals("b")).findFirst().get();
//		long id = product.getId();
//
//		productsController.deleteProduct(id);
//
//		products = productsController.listAllProducts("", user);
//
//		assertEquals(2, products.size());
//		assertEquals("test1 gumikacsa", products.get(1).getName());
//	}
//
//	@Test
//	public void testUpdateProduct() {
//
//
//		productsController.saveProduct(new Product(3718L, "mod", "Mod",
//				"mod2019", "QuackQuack Inc.", 2019, ProductStatus.DELETED));
//
//		Product foundProduct = productsController.findProductByAddress("mod2019");
//		foundProduct.setCode("modositott");
//		foundProduct.setName("Tesztkacsa Módosított");
//		foundProduct.setAddress("tesztkacsa-modositott");
//		foundProduct.setManufacturer("QuackQuack mod");
//		foundProduct.setPrice(3019);
//		foundProduct.setProductStatus(ProductStatus.DELETED);
//		productsController.updateProduct(foundProduct.getId(), foundProduct);
//
//		assertEquals(foundProduct.getCode(), "modositott");
//		assertEquals(foundProduct.getName(), "Tesztkacsa Módosított");
//		assertEquals(foundProduct.getAddress(), "tesztkacsa-modositott");
//		assertEquals(foundProduct.getManufacturer(), "QuackQuack mod");
//		assertEquals(foundProduct.getPrice(), 3019);
//		assertEquals(foundProduct.getProductStatus(), ProductStatus.DELETED);
//	}
//
//	@Test
//	public void testPutToBasket() {
//
//		Product foundProduct = productsController.findProductByAddress("test1-gumikacsa");
//		System.out.println(foundProduct.getId());
//		Authentication testAuthentication = new TestingAuthenticationToken("user", "csiga", "ROLE_USER");
//		basketsController.putToBasket(foundProduct.getId(), testAuthentication);
//
//		Product productInBasket = basketsController.listBasketProducts(testAuthentication).get(0);
//
//		assertEquals(productInBasket.getCode(), "test1");
//		assertEquals(productInBasket.getName(), "test1 gumikacsa");
//		assertEquals(productInBasket.getAddress(), "test1-gumikacsa");
//		assertEquals(productInBasket.getManufacturer(), "QuackQuack Inc.");
//		assertEquals(productInBasket.getPrice(), 2000);
//		assertEquals(productInBasket.getProductStatus(), ProductStatus.ACTIVE);
//	}
//
//	@Test
//	public void testDeleteFromBasket() {
//
//		Product foundProduct = productsController.findProductByAddress("test1-gumikacsa");
//		Authentication testAuthentication = new TestingAuthenticationToken("user", "csiga", "ROLE_USER");
//		basketsController.putToBasket(foundProduct.getId(), testAuthentication
//		);
//
//		basketsController.deleteFromBasket(foundProduct.getId(), testAuthentication);
//		List<Product> productInBasketList = basketsController.listBasketProducts(testAuthentication);
//
//		assertEquals(productInBasketList.size(), 0);
//	}
//
//	@Test
//	public void testEmptyBasket() {
//
//		Product foundProduct = productsController.findProductByAddress("test1-gumikacsa");
//		Authentication testAuthentication = new TestingAuthenticationToken("user", "csiga", "ROLE_USER");
//		basketsController.putToBasket(foundProduct.getId(), testAuthentication);
//
//		basketsController.emptyBasket(testAuthentication);
//		List<Product> productInBasketList = basketsController.listBasketProducts(testAuthentication);
//
//		assertEquals(productInBasketList.size(), 0);
//	}
//
//	public void testFindProductByName() {
////
////		Product foundProduct = productsController.findProductByCode("test1");
////
////		assertEquals(foundProduct.getName(),"test1 gumikacsa");
////		assertEquals(foundProduct.getAddress(),"test1-gumikacsa");
////		assertEquals(foundProduct.getManufacturer(),"QuackQuack Inc.");
////		assertEquals(foundProduct.getPrice(),2000);
////		assertEquals(foundProduct.getProductStatus(), ProductStatus.ACTIVE);
//	}
//	public void testFindProductByCode() {
//
////		Product foundProduct = productsController.findProductByName("test1-gumikacsa");
////
////		assertEquals(foundProduct.getCode(),"test1");
////		assertEquals(foundProduct.getAddress(),"test1-gumikacsa");
////		assertEquals(foundProduct.getManufacturer(),"QuackQuack Inc.");
////		assertEquals(foundProduct.getPrice(),2000);
////		assertEquals(foundProduct.getProductStatus(), ProductStatus.ACTIVE);
//	}
//
//    @Test
//    public void contextOrderLoads() {
//        ordersController.listAllOrdersAsAdmin(new TestingAuthenticationToken("adminka", "alma", "ROLE_ADMIN"), "false");
//        List<Order> allOrders = ordersController.listAllOrdersAsAdmin(new TestingAuthenticationToken("adminka", "alma", "ROLE_ADMIN"), "false");
//
//        assertEquals(6, allOrders.size());
//        assertEquals("2019-03-25", allOrders.get(0).getDate());
//    }
//
//}