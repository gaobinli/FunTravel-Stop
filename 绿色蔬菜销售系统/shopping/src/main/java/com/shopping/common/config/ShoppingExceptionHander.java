package com.shopping.common.config;

import com.shopping.common.exception.AppExceptionHandler;
import com.shopping.domain.ResultDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

@RestControllerAdvice
public class ShoppingExceptionHander extends AppExceptionHandler {
    public ShoppingExceptionHander() {
        super("Shopping");
    }

    @ExceptionHandler({ServletException.class})
    public ResultDTO servletException(ServletException e) {
        return ResultDTO.error("sys." + "Shopping" + ".404", e.getMessage());
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResultDTO internalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return ResultDTO.error("sys." + "Shopping" + ".500", e.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResultDTO badCredentialsException(BadCredentialsException e) {
        return ResultDTO.error("sys." + "Shopping" + ".500", "亲！密码错误");
    }
}