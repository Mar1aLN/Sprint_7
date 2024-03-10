package Model.Body;

public class CourierLoginBody {
    private String login;

    private String password;

    public CourierLoginBody(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierLoginBody() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
