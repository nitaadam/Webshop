package training360.bitsnbytes.rubberduck.report;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private ReportDao reportDao;

    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public List<Report1> listReportByDateAndStatus() {
        return reportDao.listReportByDateAndStatus();
    }

    public List<Report2> listReportByDateAndProduct() {
        return reportDao.listReportByDateAndProduct();
    }
}
