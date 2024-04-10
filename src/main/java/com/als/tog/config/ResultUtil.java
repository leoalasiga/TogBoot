package com.als.tog.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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


    private ResultUtil() {

    }

    public static <T> ResultUtil<T> success() {
        ResultUtil<T> resultUtil = new ResultUtil<>();
        resultUtil.setCode(SUCCESS_CODE);
        resultUtil.setMessage(SUCCESS_MESSAGE);
        return resultUtil;
    }

    public static <T> ResultUtil<T> success(T data) {
        ResultUtil<T> resultUtil = success();
        resultUtil.setData(data);
        return resultUtil;
    }

    public static <T> ResultUtil<T> fail() {
        ResultUtil<T> resultUtil = new ResultUtil<>();
        resultUtil.setCode(FAIL_CODE);
        resultUtil.setMessage(FAIL_MESSAGE);
        return resultUtil;
    }
}
