package com.blackfiresoft.sheepmall.result;

import lombok.Data;

/**
 * Represents a standardized result entity for API responses.
 */
@Data
public class ResultEntity {
    private Integer code;
    private String message;
    private Object data;

    /**
     * Creates a success result entity with default success message and code.
     *
     * @return a ResultEntity indicating success
     */
    public static ResultEntity success() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(ResultEnum.SUCCESS.getCode());
        resultEntity.setMessage(ResultEnum.SUCCESS.getMessage());
        return resultEntity;
    }

    /**
     * Creates a success result entity with data.
     *
     * @param data the data to be included in the result
     * @return a ResultEntity indicating success with the provided data
     */
    public static ResultEntity success(Object data) {
        ResultEntity resultEntity = success();
        resultEntity.setData(data);
        return resultEntity;
    }

    /**
     * Creates a failure result entity with a specified code and message.
     *
     * @param resultEnum the status code of the failure
     * @return a ResultEntity indicating failure with the specified parameters
     */
    public static ResultEntity fail(ResultEnum resultEnum) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(resultEnum.getCode());
        resultEntity.setMessage(resultEnum.getMessage());
        return resultEntity;
    }

}
