package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.shopping.domain.dto.req.SysUserReqDto;
import com.shopping.domain.model.SysUserEntity;
import com.shopping.domain.po.SysUserPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysUserReqDtoConvertor implements IConvertor<SysUserReqDto,SysUserEntity,SysUserPo> {

}








