package training360.bitsnbytes.rubberduck.report;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;

public class Report2 {

    private YearMonth yearAndMonthOfOrder;
    private String productName;
    private int productCount;
    private long productPrice;
    private long totalPrice;

    public Report2(int yearOfOrder, int monthOfOrder, String productName, int productCount, long productPrice, long totalPrice) {
        this.yearAndMonthOfOrder = YearMonth.of(yearOfOrder, monthOfOrder);
        this.productName = productName;
        this.productCount = productCount;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
    }

    public YearMonth getYearAndMonthOfOrder() {
        return yearAndMonthOfOrder;
    }

    public void setYearAndMonthOfOrder(YearMonth yearAndMonthOfOrder) {
        this.yearAndMonthOfOrder = yearAndMonthOfOrder;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
