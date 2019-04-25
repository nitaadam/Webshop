package training360.bitsnbytes.rubberduck.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/api/reports/orders")
    public List<Report1> listReportByDateAndStatus() {
        return reportService.listReportByDateAndStatus();
    }

    @GetMapping("/api/reports/products")
    public List<Report2> listReportByDateAndProduct(){
        return reportService.listReportByDateAndProduct();
    }

}
