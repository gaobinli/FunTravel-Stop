package com.shopping.common.convertor;

import com.shopping.domain.dto.req.UserReqDto;
import com.shopping.domain.model.UserEntity;
import com.shopping.domain.po.UserPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class UserReqDtoConvertor implements IConvertor<UserReqDto,UserEntity,UserPo> {

}








