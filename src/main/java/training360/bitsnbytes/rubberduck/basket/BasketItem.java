package training360.bitsnbytes.rubberduck.basket;

import training360.bitsnbytes.rubberduck.product.Product;

public class BasketItem {

    long id;
    private Product product;
    private int quantity;

    public BasketItem(long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public BasketItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public BasketItem() {
    }

    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
