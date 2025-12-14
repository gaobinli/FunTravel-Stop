package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.shopping.domain.dto.req.SysUserRoleReqDto;
import com.shopping.domain.model.SysUserRoleEntity;
import com.shopping.domain.po.SysUserRolePo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysUserRoleReqDtoConvertor implements IConvertor<SysUserRoleReqDto,SysUserRoleEntity,SysUserRolePo> {

}








