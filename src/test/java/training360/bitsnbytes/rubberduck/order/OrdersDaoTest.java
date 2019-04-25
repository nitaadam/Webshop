package training360.bitsnbytes.rubberduck.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class OrdersDaoTest {

    @Autowired
    private OrdersDao ordersDao;

//    @Test
//    public void testCreateNewOrder(){
//        Order order = ordersDao.createNewOrder(1);
//        assertEquals(OrderStatus.ACTIVE, order.getOrderStatus());
//    }

    @Test
    public void testListAllOrdersAsAdmin(){
        List<Order> orderList = ordersDao.listAllOrdersAsAdmin();
        assertEquals(3, orderList.size());
        assertEquals(2, orderList.get(0).getId());
        assertEquals(6058, orderList.get(0).getSum());
    }

}
