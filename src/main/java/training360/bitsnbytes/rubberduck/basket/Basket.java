package training360.bitsnbytes.rubberduck.basket;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private long id;
    private List<BasketItem> basketItemList = new ArrayList<>();

    public Basket(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public List<BasketItem> getBasketItem() {
        return basketItemList;
    }

    public void setBasketItemList(List<BasketItem> basketItemList) {
        this.basketItemList = basketItemList;
    }
}
