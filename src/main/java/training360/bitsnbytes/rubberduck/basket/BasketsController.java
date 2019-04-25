package training360.bitsnbytes.rubberduck.basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.Validator;

import java.util.List;

@RestController
public class BasketsController {

    @Autowired
    private BasketsService basketsService;

    private Validator validator = new Validator();

    public BasketsController(BasketsService basketsService) {
        this.basketsService = basketsService;
    }


    @PostMapping("/api/products/{id}/tobasket")
    public ResponseStatus putToBasket(@PathVariable long id, Authentication authentication, @RequestBody BasketItem basketItem) {
        if (basketItem.getQuantity()<1){
            return new ResponseStatus(false, "Egynél kevesebb terméket nem lehet rendelni!");
        }
        return basketsService.putToBasket(id, authentication.getName(), basketItem);
    }

    @PostMapping("api/basket/quantity")
    public ResponseStatus updateBasketItem(Authentication authentication, @RequestBody BasketItem basketItem) {

        if (!validator.isQuantityPositive(basketItem.getQuantity())) {
            return new ResponseStatus(false, "A mennyiség csak pozitív szám lehet.");
        }
        return basketsService.updateBasketItem(authentication.getName(), basketItem);
    }


    @GetMapping("api/basket")
    public List<BasketItem> listBasketProducts(Authentication authentication) {
        return basketsService.listBasketProducts(authentication.getName());
    }

    @DeleteMapping("api/basket/{productId}")
    public ResponseStatus deleteFromBasket(@PathVariable long productId, Authentication authentication) {
        if (basketsService.deleteFromBasket(productId, authentication.getName())) {
            return new ResponseStatus(true, "");
        } else {
            return new ResponseStatus(false, "A termék nem törlődött.");
        }
    }

    @DeleteMapping("/api/basket")
    public ResponseStatus emptyBasket(Authentication authentication) {
        if (basketsService.emptyBasket(authentication.getName())) {
            return new ResponseStatus(true, "");
        } else {
            return new ResponseStatus(false, "A kosár nem lett kiürítve.");
        }
    }

}
