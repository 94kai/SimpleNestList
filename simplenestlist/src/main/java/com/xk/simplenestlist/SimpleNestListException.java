package com.xk.simplenestlist;

/**
 * @author xuekai1
 * @date 2019/4/24
 */
public class SimpleNestListException extends RuntimeException{
    public SimpleNestListException() {
    }

    public SimpleNestListException(String message) {
        super(message);
    }

    public SimpleNestListException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleNestListException(Throwable cause) {
        super(cause);
    }
}
