package training360.bitsnbytes.rubberduck.product;

import training360.bitsnbytes.rubberduck.category.Category;

public class Product {

    private Long id;
    private String code;
    private String name;
    private String address;
    private String manufacturer;
    private int price;
    private ProductStatus productStatus;
    private Category category;
    private byte[] image;

    public Product(Long id, String code, String name, String address, String manufacturer, int price, ProductStatus productStatus) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.address = address;
        this.manufacturer = manufacturer;
        this.price = price;
        this.productStatus = productStatus;
    }

    public Product(Long id, String code, String name, String address, String manufacturer, int price, ProductStatus productStatus, Category category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.address = address;
        this.manufacturer = manufacturer;
        this.price = price;
        this.productStatus = productStatus;
        this.category = category;
    }

    public Product(Long id, String code, String name, String address, String manufacturer, int price, ProductStatus productStatus, Category category, byte[] image) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.address = address;
        this.manufacturer = manufacturer;
        this.price = price;
        this.productStatus = productStatus;
        this.category = category;
        this.image = image;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
