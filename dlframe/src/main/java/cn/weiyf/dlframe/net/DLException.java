package cn.weiyf.dlframe.net;

/**
 * Created by weiyf on 16-12-6.
 */

public class DLException extends RuntimeException {


    private int errorCode;

    public DLException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public int getErrorCode() {
        return errorCode;
    }
}
