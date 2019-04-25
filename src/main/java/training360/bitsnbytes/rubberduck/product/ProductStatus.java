package training360.bitsnbytes.rubberduck.product;

public enum ProductStatus {

    ACTIVE ("Aktív"),
    DELETED ("Törölt");

    private String statusHun;

    ProductStatus(String statusHun) {
        this.statusHun = statusHun;
    }

    public String getStatusHun() {
        return statusHun;
    }

    public void setStatusHun(String statusHun) {
        this.statusHun = statusHun;
    }

}
