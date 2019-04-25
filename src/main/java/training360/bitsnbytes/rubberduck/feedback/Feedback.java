package training360.bitsnbytes.rubberduck.feedback;

import training360.bitsnbytes.rubberduck.user.User;

import java.time.LocalDateTime;

public class Feedback {
    private Long id;
    private Long productId;
    private User user;
    private Integer stars;
    private String review;
    private LocalDateTime date;

    public Feedback() {
    }

    public Feedback(long id, long productId, User user, int stars, String review, LocalDateTime date) {
        this.id = id;
        this.productId = productId;
        this.user = user;
        this.stars = stars;
        this.review = review;
        this.date = date;
    }

    public Feedback(Long productId, User user, Integer stars, String review, LocalDateTime date) {
        this.productId = productId;
        this.user = user;
        this.stars = stars;
        this.review = review;
        this.date = date;
    }

    public Feedback(Long productId, User user, Integer stars, String review) {
        this.productId = productId;
        this.user = user;
        this.stars = stars;
        this.review = review;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
