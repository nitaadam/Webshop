package training360.bitsnbytes.rubberduck.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.Validator;

import java.text.Normalizer;
import java.util.List;

@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    private Validator validator = new Validator();

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/api/myorders")
    public ResponseStatus createOrder(@RequestBody Order order, Authentication authentication){
        if(order.getDeliveryAddress() == null){
            return new ResponseStatus(false,"Kérlek, jelöld be az átvétel módját!");
        }
        ResponseStatus rs = validator.addressValidation(order.getDeliveryAddress());
        String convertedLowerCaseAddressFromDb = Normalizer.normalize(order.getDeliveryAddress().toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("[^a-z0-9]", "");
        List<Order> orderList = ordersService.listOrdersByUserId(authentication.getName());
        for(Order order1: orderList){
            if(order1.getDeliveryAddress().equals(order.getDeliveryAddress())){
                continue;
            }
            if(Normalizer.normalize(order1.getDeliveryAddress().toLowerCase(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("[^a-z0-9]", "").equals(convertedLowerCaseAddressFromDb)){
                return new ResponseStatus(false, "Ez a cím már létezik az adatbázisban, kérlek válaszd ki a listáról!");
            }
        }
        if(rs.isOk()) {
            return ordersService.createOrder(authentication.getName(), order.getDeliveryAddress());
        }
        return rs;
    }

    @GetMapping("/api/myorders")
    public List<Order> listOrdersByUserId(Authentication authentication){
        return ordersService.listOrdersByUserId(authentication.getName());
    }

    @GetMapping("/api/orders")
    public List<Order> listAllOrdersAsAdmin(Authentication authentication, @RequestParam("isActive") String isActive){
            return ordersService.listAllOrdersAsAdmin("true".equals(isActive));
    }


    @DeleteMapping("/orders/{id}/{address}")
    public void deleteOrderItem(@PathVariable long id, @PathVariable String address){
            ordersService.deleteOrderItem(id, address);
    }

    @DeleteMapping("/orders/{id}")
    public void deleteOrderById(@PathVariable long id){
            ordersService.deleteOrderById(id);
    }

    @PostMapping("/orders/{id}/status")
    public void changeOrderStatusToDeliveredByOrderId(@PathVariable long id){
            ordersService.changeOrderStatusToDeliveredByOrderId(id);
    }

}
