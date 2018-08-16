package app.com.esenatenigeria.model;


/**
 * Created by applify on 6/6/2017.
 */

public abstract class BaseModel {


    /**
     * statusCode : 500
     * error : Internal Server Error
     * message : An internal server error occurred
     */

    private int statusCode;
    private String error;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

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
