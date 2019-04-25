package training360.bitsnbytes.rubberduck.feedback;

public class FeedbackData {

    private int delivered;
    private int reviewed;

    public FeedbackData(int delivered, int reviewed) {
        this.delivered = delivered;
        this.reviewed = reviewed;
    }

    public int getDelivered() {
        return delivered;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }

    public int getReviewed() {
        return reviewed;
    }

    public void setReviewed(int reviewed) {
        this.reviewed = reviewed;
    }
}
