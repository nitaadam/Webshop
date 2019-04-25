package training360.bitsnbytes.rubberduck.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import training360.bitsnbytes.rubberduck.ResponseStatus;
import training360.bitsnbytes.rubberduck.product.Product;
import training360.bitsnbytes.rubberduck.product.ProductsService;
import training360.bitsnbytes.rubberduck.user.User;
import training360.bitsnbytes.rubberduck.user.UsersService;

import java.util.ArrayList;
import java.util.List;


@RestController
public class FeedbacksController {
    @Autowired
    private FeedbacksService feedbacksService;
    private UsersService usersService;
    private ProductsService productsService;

    public FeedbacksController(FeedbacksService feedbacksService, UsersService usersService, ProductsService productsService) {
        this.feedbacksService = feedbacksService;
        this.usersService = usersService;
        this.productsService = productsService;
    }

    @GetMapping("/api/feedbacks/")
    public List<Feedback> listFeedbacksByUserId(Authentication authentication) {
        if (authentication == null) {
            return new ArrayList<>();
        }
        return feedbacksService.listFeedbacksByUserId(authentication.getName());
    }

    @PostMapping("/api/products/feedbacks/{address}")
    public ResponseStatus createFeedback(@RequestBody Feedback feedback, Authentication authentication, @PathVariable String address) {
        long productId = productsService.findProductByAddress(address).getId();
        User user = usersService.findUserByUserName(authentication.getName()).get();
        var noHTMLString = feedback.getReview().replaceAll("\\<.*?\\>", "");
        Feedback feedback1 = new Feedback(productId, user, feedback.getStars(), noHTMLString);
        if (feedback.getReview().trim().equals("")){
            return new ResponseStatus(false, "Kérlek, írj szöveges értékelést is!");
        }
        feedbacksService.createFeedback(feedback1);
        return new ResponseStatus(true, "Köszönjük, hogy értékelted a termékünket!");
    }

    @PostMapping("/api/products/modifyFeedback/{address}")
    public ResponseStatus modifyFeedback(@RequestBody Feedback feedback, Authentication authentication, @PathVariable String address) {
        long userId = usersService.findUserByUserName(authentication.getName()).get().getId();
        long productId = productsService.findProductByAddress(address).getId();
        if (feedback.getReview() == null || feedback.getReview().trim().equals("")){
            return new ResponseStatus(false, "Kérjük, írj szöveges értékelést is!");
        }
        else {
            feedbacksService.modifyFeedback(feedback, userId, productId);
            return new ResponseStatus(true, "Sikeres módosítás");
        }
    }

    @DeleteMapping("/api/feedbacks/{id}")
    public ResponseStatus deleteFeedBackById(@PathVariable long id){
        feedbacksService.deleteFeedbackById(id);
        return new ResponseStatus(true, "Sikeres törlés");
    }

    @GetMapping("/api/feedbacks/getfeedbacks/{address}")
    public ResponseStatus getNumberOfDeliveredOrdersByUserForProduct(@PathVariable String address, Authentication authentication) {
        long userId = usersService.findUserByUserName(authentication.getName()).get().getId();
        long productId = productsService.findProductByAddress(address).getId();
        FeedbackData deliveredAndReviewed = feedbacksService.getNumberOfDeliveredOrdersByUserForProduct(userId, productId);

        int delivered = deliveredAndReviewed.getDelivered();
        int reviewed = deliveredAndReviewed.getReviewed();

        if (delivered < 1){
            return new ResponseStatus(false, "Rendelkezned kell kiszállított termékkel az értékeléshez!");
        }
        else if (reviewed < 1){
            return new ResponseStatus(true, "Köszönjük, hogy értékelted a terméket!");
        }
        else {
            return new ResponseStatus(false, "Már értékelted a terméket.");
        }
    }

    @GetMapping("/api/products/feedbacks/{address}")
    public List<Feedback> listFeedbacksByProductId(@PathVariable String address){
        try {
            Product product = productsService.findProductByAddress(address);
            long id = product.getId();
            return feedbacksService.listFeedbacksByProductId(id);
        }
        catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}
