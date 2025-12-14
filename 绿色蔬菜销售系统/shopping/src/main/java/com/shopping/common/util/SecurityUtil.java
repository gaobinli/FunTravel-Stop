package com.shopping.common.util;

import com.shopping.domain.model.LoginUserEntity;
import com.shopping.common.exception.AppException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {

    /**
     * 获取用户姓名
     **/
    public static String getUserName() {
        try {
            LoginUserEntity loginUserEntity = (LoginUserEntity) getAuthentication().getPrincipal();
            return loginUserEntity.getSysUser().getName();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 获取用户
     **/
    public static LoginUserEntity getLoginUser() {
        try {
            return (LoginUserEntity) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new AppException("获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
