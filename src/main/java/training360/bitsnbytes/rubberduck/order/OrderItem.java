package training360.bitsnbytes.rubberduck.order;

import training360.bitsnbytes.rubberduck.product.Product;

public class OrderItem {

    private Long id;
    private Product product;
    private int price;
    private int quantity;

    public OrderItem(Long id) {
        this.id = id;
    }

//    public OrderItem(Long id, Product product, int price) {
//        this.id = id;
//        this.product = product;
//        this.price = price;
//    }


    public OrderItem(Long id, Product product, int price, int quantity) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
