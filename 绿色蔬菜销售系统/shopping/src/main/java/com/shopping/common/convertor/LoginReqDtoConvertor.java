package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.shopping.domain.dto.req.LoginReqDto;
import com.shopping.domain.model.LoginEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class LoginReqDtoConvertor implements IConvertor<LoginReqDto, LoginEntity, String> {

}








