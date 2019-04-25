package training360.bitsnbytes.rubberduck.order;

import org.springframework.stereotype.Service;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.basket.Basket;
import training360.bitsnbytes.rubberduck.basket.BasketItem;
import training360.bitsnbytes.rubberduck.basket.BasketsDao;
import training360.bitsnbytes.rubberduck.product.Product;
import training360.bitsnbytes.rubberduck.product.ProductsDao;
import training360.bitsnbytes.rubberduck.user.User;
import training360.bitsnbytes.rubberduck.user.UsersDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private OrdersDao ordersDao;
    private BasketsDao basketsDao;
    private UsersDao usersDao;
    private ProductsDao productsDao;

    public OrdersService(OrdersDao ordersDao, BasketsDao basketsDao, UsersDao usersDao, ProductsDao productsDao) {
        this.ordersDao = ordersDao;
        this.basketsDao = basketsDao;
        this.usersDao = usersDao;
        this.productsDao = productsDao;
    }

    public ResponseStatus createOrder(String userName, String address) {
        long userId = usersDao.findUserByUserName(userName).get().getId();
        Basket basket = basketsDao.findBasketByUserId(userId).get();
        List<BasketItem> basketItemLista = basketsDao.listBasketItems(basket);
        basket.setBasketItemList(basketItemLista);
//        List<Product> productList = basketsDao.listBasketProducts(userId);
        Order order;
        if (basketItemLista.size() != 0) {
            order = ordersDao.createNewOrder(userId, address);
            for (BasketItem basketItem: basketItemLista) {
                ordersDao.saveOrderItem(order.getId(), basketItem);
                basketsDao.emptyBasket(basket.getId());
            }
            return new ResponseStatus(true, "Sikeresen megrendelted a termékeket.");
        } else {
            return new ResponseStatus(false, "Először helyezz terméket a kosárba!");
        }
    }

    public List<Order> listOrdersByUserId(String userName) {
        List<Order> orderList = ordersDao.listOrdersByUserId(usersDao.findUserByUserName(userName).get().getId());
        for(Order order: orderList){
            order.setOrderItemList(ordersDao.listOrderItemsByOrder(order));
        }
        return orderList;
    }

    public List<Order> listAllOrdersAsAdmin(boolean isActive) {
        List<Order> allOrders = new ArrayList<>();
        if(isActive) {
            List<Order> activeOrderList = ordersDao.listActiveOrdersAsAdmin();
            for(Order activeOrder : activeOrderList){
                activeOrder.setOrderItemList(ordersDao.listOrderItemsByOrder(activeOrder));
            }
            allOrders = activeOrderList;
        } else {
            List<Order> allOrderList = ordersDao.listAllOrdersAsAdmin();
            for(Order allOrder : allOrderList){
                allOrder.setOrderItemList(ordersDao.listOrderItemsByOrder(allOrder));
            }
            allOrders = allOrderList;
        }
        return allOrders;
    }

    public void deleteOrderItem(long id, String address) {
        long productId = productsDao.findProductByAddress(address).getId();
            ordersDao.deleteOrderItem(id, productId);
    }

    public void deleteOrderById(long id) {
            ordersDao.deleteOrderById(id);
    }

    public void changeOrderStatusToDeliveredByOrderId(long id) {
        ordersDao.changeOrderStatusToDeliveredByOrderId(id);
    }


}
