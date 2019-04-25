package training360.bitsnbytes.rubberduck.dashboard;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private DashboardDao dashboardDao;

    public DashboardService(DashboardDao dashboardDao) {
        this.dashboardDao = dashboardDao;
    }

   public int getTotalUsers(){
      return   dashboardDao.getTotalUsers();
   }

    public int getActiveProducts(){
       return dashboardDao.getActiveProducts();
    }

    public int getAllProducts(){
      return dashboardDao.getAllProducts();
    }

    public int getActiveOrders(){
      return dashboardDao.getActiveOrders();
    }

    public int getAllOrders(){
     return dashboardDao.getAllOrders();
    }
}
