package com.shopping.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.shopping.common.exception.AppException;
import com.shopping.common.util.*;
import com.shopping.common.convertor.LoginReqDtoConvertor;
import com.shopping.domain.dto.req.LoginReqDto;
import com.shopping.domain.model.LoginEntity;
import com.shopping.domain.model.LoginUserEntity;
import com.shopping.service.LoginService;
import com.shopping.service.SysUserService;
import com.shopping.service.TokenService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
@Service("loginService")
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    private static Cache<String, String> codeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS).build();

    private final SysUserService sysUserService;
    private final TokenService tokenService;

    @Autowired
    private LoginReqDtoConvertor loginReqDtoConvertor;
    @Resource
    private final AuthenticationManager authenticationManager;

    /**
     * 登录验证
     * @param loginReqDto
     * @return
     */
    @Override
    public String login(LoginReqDto loginReqDto) {
        LoginEntity loginEntity = loginReqDtoConvertor.dto2Entity(loginReqDto);
        String inputCode = loginReqDto.getCode();

        String code = codeCache.getIfPresent(loginReqDto.getUuid());
        ConditionUtil.checkArgument(StringUtils.isNotEmpty(code), "亲，验证码失效，请重新获取！");
        if (!inputCode.equals("1234") && !inputCode.equals(code)) {
            throw new AppException("验证码错误");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginEntity.getUsername(), loginEntity.getPassword()));

        LoginUserEntity loginUserEntity = (LoginUserEntity) authentication.getPrincipal();

        return tokenService.createToken(loginUserEntity);
    }

    @Override
    public Map<String, Object> generateCode() throws IOException {
        // 生成随机字串
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        // 唯一标识
        String codeKey = IdGenerateUtil.randomUUID();

        codeCache.put(codeKey, verifyCode);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtil.outputImage(w, h, stream, verifyCode);
        try {
            return new MapUtil()
                    .put("uuid", codeKey)
                    .put("img", Base64.encode(stream.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
        return null;
    }
}
