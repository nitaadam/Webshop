package training360.bitsnbytes.rubberduck.report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import training360.bitsnbytes.rubberduck.order.OrderStatus;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class ReportControllerTest {

    @Autowired
    private ReportController reportController;


    @Test
    public void testListReportByDateAndStatus() {

        List<Report1> reportByDateAndStatus = reportController.listReportByDateAndStatus();

        assertEquals("2019-03", reportByDateAndStatus.get(0).getYearAndMonthOfOrder().toString());
        assertEquals(OrderStatus.ACTIVE, reportByDateAndStatus.get(0).getOrderStatus());
        assertEquals(2, reportByDateAndStatus.get(0).getOrderCount());
        assertEquals(5132, reportByDateAndStatus.get(0).getTotalPriceOfOrder());
    }

    @Test
    public void testListReportByDateAndProduct() {

        List<Report2> reportByDateAndProduct = reportController.listReportByDateAndProduct();

        assertEquals("2019-03", reportByDateAndProduct.get(0).getYearAndMonthOfOrder().toString());
        assertEquals("animalbat", reportByDateAndProduct.get(0).getProductName());
        assertEquals(1, reportByDateAndProduct.get(0).getProductCount());
        assertEquals(2700, reportByDateAndProduct.get(0).getProductPrice());
        assertEquals(2020, reportByDateAndProduct.get(0).getTotalPrice());
    }
}
