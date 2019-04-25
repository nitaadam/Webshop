package training360.bitsnbytes.rubberduck.Dashboard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.bitsnbytes.rubberduck.dashboard.Dashboard;
import training360.bitsnbytes.rubberduck.dashboard.DashboardController;
import training360.bitsnbytes.rubberduck.dashboard.DashboardService;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public  class DashboardControllerTest{

    @Autowired
    private DashboardController dashboardController;

    @Test
    public void testCreateDashboard() {

        Dashboard testDashboard = dashboardController.createDashboard();

        assertEquals(4, testDashboard.getTotalUsers());
        assertEquals(4, testDashboard.getTotalActiveProducts());
        assertEquals(5, testDashboard.getTotalProducts());
        assertEquals(3, testDashboard.getTotalActiveOrders());
        assertEquals(4, testDashboard.getTotalOrders());

    }
}
