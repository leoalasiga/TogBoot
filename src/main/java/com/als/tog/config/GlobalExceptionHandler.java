package com.als.tog.config;

import com.als.tog.Exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dkw
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResultUtil<Object> handleBusinessException(BusinessException e) {
        return ResultUtil.fail(e.getMessage());
    }
}
