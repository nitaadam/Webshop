package training360.bitsnbytes.rubberduck.basket;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.product.Product;
import training360.bitsnbytes.rubberduck.user.UsersDao;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BasketsService {

    private BasketsDao basketsDao;
    private UsersDao usersDao;

    public BasketsService(BasketsDao basketsDao, UsersDao usersDao) {
        this.basketsDao = basketsDao;
        this.usersDao = usersDao;
    }

    public ResponseStatus putToBasket(long productId, String userName, BasketItem basketItem) {
        long userId = usersDao.findUserByUserName(userName).get().getId();
        Optional<Basket> basket = basketsDao.findBasketByUserId(userId);
        Basket selectedBasket;
        if (basket.isEmpty()) {
            selectedBasket = basketsDao.createBasket(userId);
        } else {
            selectedBasket = basket.get();
        }
        Optional<BasketItem> alreadyBasketedItem = basketsDao.findBasketItemByUserAndProduct(userId, basketItem.getProduct());
        if (alreadyBasketedItem.isEmpty()) {
            basketsDao.saveBasketItem(basketItem, selectedBasket.getId());
        } else {
            alreadyBasketedItem.get().setQuantity(alreadyBasketedItem.get().getQuantity()+basketItem.getQuantity());
            basketsDao.updateBasketItem(alreadyBasketedItem.get());
        }
        return new ResponseStatus(true, basketItem.getQuantity() + " darab termék került a kosárba!");
    }

    public ResponseStatus updateBasketItem(String userName, BasketItem basketItem) {
        basketsDao.updateBasketItem(basketItem);
        return new ResponseStatus(true, "A kosárban lévő termék mennyisége módosításra került!");
    }

    public List<BasketItem> listBasketProducts(String userName) {

        List<Product> products = basketsDao.listBasketProducts(usersDao.findUserByUserName(userName).get().getId());
        List<BasketItem> basketItems = new ArrayList<>();
        long userId = usersDao.findUserByUserName(userName).get().getId();
        for (Product product: products) {
            basketItems.add(basketsDao.findBasketItemByUserAndProduct(userId, product).get());
        }
        return basketItems;
    }

    public boolean deleteFromBasket(long productId, String userName) {
        long userId = usersDao.findUserByUserName(userName).get().getId();
        long basketId = basketsDao.findBasketByUserId(userId).get().getId();
        return basketsDao.deleteFromBasket(basketId, productId);
    }

    public boolean emptyBasket(String userName) {
        long userId = usersDao.findUserByUserName(userName).get().getId();
        long basketId =basketsDao.findBasketByUserId(userId).get().getId();
        return  basketsDao.emptyBasket(basketId);
    }
}




