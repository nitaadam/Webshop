package training360.bitsnbytes.rubberduck.category;

public class Category {

    private long id;
    private String name;
    private int viewOrder;

    public Category(long id, String name, int viewOrder) {
        this.id = id;
        this.name = name;
        this.viewOrder = viewOrder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getViewOrder() {
        return viewOrder;
    }

    public void setViewOrder(int viewOrder) {
        this.viewOrder = viewOrder;
    }


}
