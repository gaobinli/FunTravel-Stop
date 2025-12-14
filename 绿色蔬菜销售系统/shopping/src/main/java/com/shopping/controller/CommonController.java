package com.shopping.controller;

import com.shopping.domain.ResultDTO;
import com.shopping.domain.dto.req.LoginReqDto;
import com.shopping.service.CommonService;
import com.shopping.service.LoginService;
import io.swagger.annotations.*;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Data
@RestController
@Api(tags = "common")
@SuppressWarnings("unchecked")
public class CommonController {

    private final LoginService loginService;

    private final CommonService commonService;

    @ApiOperation(value = "生成验证码", tags = {"common"}, nickname = "getCode")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @GetMapping("/captchaImage")
    public ResultDTO<Map<String, String>> getCode() throws IOException {
        return ResultDTO.ok(loginService.generateCode());
    }

    @ApiOperation(value = "登录", tags = {"common"}, nickname = "login")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping("/admin/login")
    public ResultDTO<String> login(@ApiParam(value = "要保存的对象", required = true) @RequestBody LoginReqDto loginReqDto) {
        loginReqDto.setUsername("admin-" + loginReqDto.getUsername());
        String token = loginService.login(loginReqDto);

        return ResultDTO.ok(token);
    }

    @ApiOperation(value = "用户登录", tags = {"common"}, nickname = "userLogin")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "000000:成功，否则失败")})
    @PostMapping("/user/login")
    public ResultDTO<String> userLogin(@ApiParam(value = "要保存的对象", required = true) @RequestBody LoginReqDto loginReqDto) {
        loginReqDto.setUsername("user-" + loginReqDto.getUsername());
        String token = loginService.login(loginReqDto);

        return ResultDTO.ok(token);
    }

    /**
     * 活动上传图片
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResultDTO<String> uploadImage(@RequestPart MultipartFile file) throws IOException {
        String url = commonService.uploadImage(file);

        return ResultDTO.ok(url);
    }

    /**
     * 活动上传图片
     * @param file
     * @return
     */
    @PostMapping("/upload2")
    public String uploadImage2(@RequestPart MultipartFile file) throws IOException {
        String url = commonService.uploadImage(file);

        return url;
    }
}
