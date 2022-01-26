package com.mini.exception;

import com.mini.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;

public class DataNotFoundException extends ServiceRuntimeException {

    static final String MESSAGE_KEY = "error.notfound";
    static final String MESSAGE_DETAILS = "error.notfound.details";

    public DataNotFoundException(Class cls, Object... values) {
        this(cls.getSimpleName(), values);
    }

    public DataNotFoundException(String targetName, Object... values) {
        super(MESSAGE_KEY, MESSAGE_DETAILS, new String[]{targetName, (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
    }

    @Override
    public String getMessage() {
        return MessageUtils.getInstance().getMessage(getDetailKey(), getParams());
    }

    @Override
    public String toString() {
        return MessageUtils.getInstance().getMessage(getMessageKey());
    }

}
