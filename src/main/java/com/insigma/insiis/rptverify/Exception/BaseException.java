package com.insigma.insiis.rptverify.Exception;

/**
 * <p>自定义异常类</p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/3/3 11:52
 * @since 1.0.0
 */
public class BaseException extends Exception{
    private String code;
    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Exception cause) {
        super(message, cause);
    }

    public BaseException(Exception cause) {
        super(cause);
    }

    public BaseException(String code,String message) {
        super(message);
        this.code=code;
    }

    public BaseException(String code,String message, Exception cause) {
        super(message, cause);
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
