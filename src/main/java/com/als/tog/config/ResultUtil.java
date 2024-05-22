package com.als.tog.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dkw
 */
@Data
public class ResultUtil<T> {

    public static final Integer SUCCESS_CODE = 200;
    public static final Integer FAIL_CODE = 4000;
    public static final String SUCCESS_MESSAGE = "";
    public static final String FAIL_MESSAGE = "系统异常";
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    @Getter
    @Setter
    private String message;

    /**
     * 返回数据
     */
    @Setter
    private T data;
    /**
     * 返回状态
     */
    @Getter
    @Setter
    private Boolean status;

    private ResultUtil() {

    }

    public static <T> ResultUtil<T> success() {
        ResultUtil<T> resultUtil = new ResultUtil<>();
        resultUtil.setCode(SUCCESS_CODE);
        resultUtil.setMessage(SUCCESS_MESSAGE);
        resultUtil.setStatus(true);
        return resultUtil;
    }

    public static <T> ResultUtil<T> success(T data) {
        ResultUtil<T> resultUtil = success();
        resultUtil.setData(data);
        return resultUtil;
    }

    public static <T> ResultUtil<T> fail() {
        ResultUtil<T> resultUtil = new ResultUtil<>();
        resultUtil.setStatus(false);
        resultUtil.setCode(FAIL_CODE);
        resultUtil.setMessage(FAIL_MESSAGE);
        return resultUtil;
    }

    public static <T> ResultUtil<T> fail(String message) {
        ResultUtil<T> resultUtil = new ResultUtil<>();
        resultUtil.setStatus(false);
        resultUtil.setCode(FAIL_CODE);
        resultUtil.setMessage(message);
        return resultUtil;
    }
}
