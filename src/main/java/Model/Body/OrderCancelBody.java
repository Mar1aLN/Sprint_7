package Model.Body;

public class OrderCancelBody {
    private String track;

    public OrderCancelBody() {

    }

    public OrderCancelBody(String track) {
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
