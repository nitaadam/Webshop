package training360.bitsnbytes.rubberduck.order;

import org.apache.tomcat.jni.Local;
import training360.bitsnbytes.rubberduck.product.Product;
import training360.bitsnbytes.rubberduck.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long id;
    private LocalDateTime date;
    private OrderStatus orderStatus;
    private User user;
    private int sum;
    private String deliveryAddress;
    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order(){}

    public Order(long id) {
        this.id = id;
        this.date = LocalDateTime.now();
    }

    public Order(Long id, LocalDateTime date, OrderStatus orderStatus) {
        this(id);
        this.date = date;
        this.orderStatus = orderStatus;
    }

    public Order(Long id, LocalDateTime date, OrderStatus orderStatus, User user) {
        this(id, date, orderStatus);
        this.user = user;
    }

    public Order(Long id, LocalDateTime date, OrderStatus orderStatus, String deliveryAddress) {
        this(id, date, orderStatus);
        this.deliveryAddress = deliveryAddress;
    }

    public Order(Long id, LocalDateTime date, OrderStatus orderStatus, String deliveryAddress, int sum) {
        this(id, date, orderStatus, deliveryAddress);
        this.sum = sum;
    }

    public Order(Long id, LocalDateTime date, OrderStatus orderStatus, String deliveryAddress, User user, int sum) {
        this(id, date, orderStatus, deliveryAddress, sum);
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList.addAll(orderItemList);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
