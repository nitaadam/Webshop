package training360.bitsnbytes.rubberduck.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/api/dashboard")
    public Dashboard createDashboard(){
        int totalUsers = dashboardService.getTotalUsers();
        int activeProducts = dashboardService.getActiveProducts();
        int allProducts = dashboardService.getAllProducts();
        int activeOrders = dashboardService.getActiveOrders();
        int allOrders = dashboardService.getAllOrders();

        return new Dashboard(totalUsers,allProducts,activeProducts,allOrders,activeOrders);
    }
}
