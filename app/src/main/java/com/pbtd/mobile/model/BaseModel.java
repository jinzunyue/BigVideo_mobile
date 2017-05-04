package com.pbtd.mobile.model;

/**
 * Created by xuqinchao on 17/4/24.
 */

public class BaseModel<T> {
    public boolean success;
    public boolean validate;
    public String message;
    public T result;
}
