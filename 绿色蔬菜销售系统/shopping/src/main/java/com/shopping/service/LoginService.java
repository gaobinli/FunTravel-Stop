package com.shopping.service;

import com.shopping.domain.dto.req.LoginReqDto;

import java.io.IOException;
import java.util.Map;

public interface LoginService {

    String login(LoginReqDto loginReqDto);

    Map<String, Object> generateCode() throws IOException;
}
