package cn.weiyf.dlframe.net;

/**
 * Created by weiyf on 16-12-6.
 */

public class DLException extends RuntimeException {


    private String errorCode;

    public DLException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public String getErrorCode() {
        return errorCode;
    }
}
