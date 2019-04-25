package training360.bitsnbytes.rubberduck.dashboard;

public class Dashboard {
    private int totalUsers;
    private int totalProducts;
    private int totalActiveProducts;
    private int totalOrders;
    private int totalActiveOrders;

    public int getTotalUsers() {
        return totalUsers;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public int getTotalActiveProducts() {
        return totalActiveProducts;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public int getTotalActiveOrders() {
        return totalActiveOrders;
    }

    public Dashboard(int totalUsers, int totalProducts, int totalActiveProducts, int totalOrders, int totalActiveOrders) {
        this.totalUsers = totalUsers;
        this.totalProducts = totalProducts;
        this.totalActiveProducts = totalActiveProducts;
        this.totalOrders = totalOrders;
        this.totalActiveOrders = totalActiveOrders;
    }
}
