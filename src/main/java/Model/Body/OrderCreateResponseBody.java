package Model.Body;


public class OrderCreateResponseBody {
    private String track;

    public OrderCreateResponseBody() {

    }

    public OrderCreateResponseBody(String track) {
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}