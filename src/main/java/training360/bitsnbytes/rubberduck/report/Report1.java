package training360.bitsnbytes.rubberduck.report;

import training360.bitsnbytes.rubberduck.order.OrderStatus;

import java.time.YearMonth;


public class Report1 {

    private YearMonth yearAndMonthOfOrder;
    private OrderStatus orderStatus;
    private int orderCount;
    private long totalPriceOfOrder;


    public Report1(int yearOfOrder, int monthOfOrder, OrderStatus orderStatus, int orderCount,long totalPriceOfOrder) {
        this.yearAndMonthOfOrder = YearMonth.of(yearOfOrder, monthOfOrder);
        this.orderStatus = orderStatus;
        this.orderCount = orderCount;
        this.totalPriceOfOrder = totalPriceOfOrder;
    }

    public YearMonth getYearAndMonthOfOrder() {
        return yearAndMonthOfOrder;
    }

    public void setYearAndMonthOfOrder(YearMonth yearAndMonthOfOrder) {
        this.yearAndMonthOfOrder = yearAndMonthOfOrder;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public long getTotalPriceOfOrder() {
        return totalPriceOfOrder;
    }

    public void setTotalPriceOfOrder(long totalPriceOfOrder) {
        this.totalPriceOfOrder = totalPriceOfOrder;
    }
}
