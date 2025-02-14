package com.blackfiresoft.sheepmall.result;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0, "success"),
    ERROR(1, "error"),
    PARAM_ERROR(2, "param is not allowed to be null"),
    DB_ERROR(3, "db error"),
    USERNAME_ERROR(4, "username error"),
    PASSWORD_ERROR(5, "password error"),
    EMAIL_ERROR(6, "email error"),
    USERNAME_EXIST(7, "username already exists"),
    EMAIL_EXIST(8, "email already exists"),
    LOGIN_FAILED(9, "username or password failed"),
    USER_NOT_EXIST(10, "user not exist"),
    DATA_NOT_FOUND(11, "data not found"),
    VALIDATION_CODE_ERROR(12, "validation code error"),
    INVALID_RANGE(13, "invalid price range"),
    FIND_ADMIN_ERROR(14, "query admin error"),
    SEND_EMAIL_ERROR(15, "send email error"),
    RESET_PASSWORD_ERROR(16, "reset password error"),
    UPDATE_PASSWORD_ERROR(17, "the new password cannot be the same as the old password"),
    ADMIN_NOT_FOUND(18, "admin not found"),
    ACTIVITY_EXPIRED(19, "activity expired"),
    USER_HAS_BUY(20, "user has buy this product"),
    STOCK_NOT_ENOUGH(21, "product has sale out"),
    LOCK_FAILED(22, "lock failed"),
    REFUND_EXPIRED(23, "beyond the refund time limit"),
    REMOVE_ACTIVITY_FAILED(24, "cannot delete ongoing activities"),
    REFUND_FAILED(25, "uncompleted orders do not support refunds"),
    UNAUTHORIZED(26, "you are unauthorized"),
    AUTH_INTERCEPTOR_ERROR(27, "an error occurred while processing your request"),
    QUANTITY_NOT_ENOUGH(28, "quantity not enough"),
    ACTIVITY_TIME_ERROR(29, "activity time error"),
    ACTIVITY_NOT_FOUND(30, "activity not found"),
    ACTIVITY_NOT_START(31, "activity not start"),
    UNKNOWN_ERROR(101, "system has occurred unknown error");

    private final int code;
    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
