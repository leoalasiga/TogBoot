package com.als.tog.config;

import com.als.tog.Exception.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author dkw
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResultUtil<Object> handleBusinessException(BusinessException e) {
        return ResultUtil.fail(e.getMessage());
    }
}
