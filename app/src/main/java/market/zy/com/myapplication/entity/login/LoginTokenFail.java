package market.zy.com.myapplication.entity.login;

/**
 * Created by zpauly on 16-5-17.
 */
public class LoginTokenFail {
    /**
     * error : unauthorized
     * message : Invalid credentidls
     */

    private String error;
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
