package com.als.tog.utils;

import com.als.tog.Exception.BusinessException;

/**
 * @author dkw
 */
public class ExceptionUtils {
    public static void throwBusinessException(String message) {
        throw new BusinessException(400, message);
    }

    public static void throwNotFoundException(String message) {
        throw new BusinessException(404, message);
    }
}
