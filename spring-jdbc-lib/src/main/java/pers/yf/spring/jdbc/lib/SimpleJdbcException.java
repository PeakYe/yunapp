package pers.yf.spring.jdbc.lib;

public class SimpleJdbcException extends RuntimeException {
    public SimpleJdbcException(String msg) {
        super(msg);
    }

    public SimpleJdbcException(Throwable cause) {
        super(cause);
    }
}
