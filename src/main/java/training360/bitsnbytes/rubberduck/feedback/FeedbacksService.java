package training360.bitsnbytes.rubberduck.feedback;

import org.springframework.stereotype.Service;
import training360.bitsnbytes.rubberduck.product.ProductsDao;
import training360.bitsnbytes.rubberduck.user.UsersDao;

import java.util.List;

@Service
public class FeedbacksService {
    private FeedbacksDao feedbacksDao;
    private UsersDao usersDao;

    public FeedbacksService(FeedbacksDao feedbacksDao, UsersDao usersDao, ProductsDao productsDao) {
        this.feedbacksDao = feedbacksDao;
        this.usersDao = usersDao;
    }

    public List<Feedback> listFeedbacksByUserId(String userName){
        long userId = usersDao.findUserByUserName(userName).get().getId();
        return feedbacksDao.listFeedbacksByUserId(userId);
    }

    public void createFeedback(Feedback feedback){
        feedbacksDao.createFeedback(feedback);
    }

    public void modifyFeedback(Feedback feedback,long userId, long productId){
        feedbacksDao.modifyFeedback(feedback,userId,productId);
    }

    public void deleteFeedbackById(long id){
        feedbacksDao.deleteFeedbackById(id);
    }

    public FeedbackData getNumberOfDeliveredOrdersByUserForProduct(long userId, long productId){
        return feedbacksDao.getNumberOfDeliveredOrdersByUserForProduct(userId, productId);
    }

    public List<Feedback> listFeedbacksByProductId(long productId){
        return feedbacksDao.listFeedbacksByProductId(productId);
    }
}
