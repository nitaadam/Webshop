package training360.bitsnbytes.rubberduck.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.bitsnbytes.rubberduck.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class OrdersControllerTest {

    @Autowired
    private OrdersController ordersController;
    private Order firstOrder = new Order(5L, LocalDateTime.now(), OrderStatus.ACTIVE, "Személyes átvétel");
    private Authentication user = new TestingAuthenticationToken("user", "user", "ROLE_USER");
    private Authentication admin = new TestingAuthenticationToken("admin", "admin", "ROLE_ADMIN");

    @Test
    public void testCreateOrder() {

        ResponseStatus createOrder = ordersController.createOrder(firstOrder, user);

        assertEquals(true, createOrder.isOk());
    }

    @Test
    public void testListOrdersByUserId() {

        List<Order> orderList = ordersController.listOrdersByUserId(user);

        assertEquals(2, orderList.size());
    }

    @Test
    public void testListAllOrdersAsAdmin() {

        List<Order> allOrderList = ordersController.listAllOrdersAsAdmin(admin,"isActive");

        assertEquals(3, allOrderList.size());
    }

    @Test
    public void deleteOrderItem() {

        Order order = ordersController.listOrdersByUserId(user).get(0);

        assertEquals(3, order.getOrderItemList().size());

        ordersController.deleteOrderItem(2L, "sebesz-gumikacsa");
        order = ordersController.listOrdersByUserId(user).get(0);

        assertEquals(2, order.getOrderItemList().size());
    }

    @Test
    public void deleteOrderById() {

        List<Order> orderList = ordersController.listOrdersByUserId(user);
        assertEquals(2, orderList.size());

        ordersController.deleteOrderById(1L);

        orderList = ordersController.listOrdersByUserId(user);
        assertEquals(2, orderList.size());
        assertEquals(OrderStatus.DELETED, orderList.get(1).getOrderStatus());
    }

    @Test
    public void testChangeOrderStatusToDeliveredByOrderId() {


    }
}
