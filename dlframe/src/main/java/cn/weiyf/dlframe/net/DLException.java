package cn.weiyf.dlframe.net;

/**
 * Created by weiyf on 16-12-6.
 */

public class DLException extends Exception {


    private int errorCode;

    public DLException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


    public int getErrorCode() {
        return errorCode;
    }
}
