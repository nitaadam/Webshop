package training360.bitsnbytes.rubberduck.Dashboard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.bitsnbytes.rubberduck.dashboard.DashboardDao;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class DashboardDaoTest {

    @Autowired
    private DashboardDao dashboardDao;

    @Test
    public void testGetTotalUsers() {

        int totalUsers = dashboardDao.getTotalUsers();

        assertEquals(4, totalUsers);
    }

    @Test
    public void testGetActiveProducts() {

        int activeProducts = dashboardDao.getActiveProducts();

        assertEquals(4, activeProducts);
    }

    @Test
    public void testGetAllProducts() {

        int totalProducts = dashboardDao.getAllProducts();

        assertEquals(5, totalProducts);
    }

    @Test
    public void testGetActiveOrders() {

        int activeOrders = dashboardDao.getActiveOrders();

        assertEquals(3, activeOrders);
    }

    @Test
    public void testGetAllOrders() {

        int allOrders = dashboardDao.getAllOrders();

        assertEquals(4, allOrders);
    }
}
