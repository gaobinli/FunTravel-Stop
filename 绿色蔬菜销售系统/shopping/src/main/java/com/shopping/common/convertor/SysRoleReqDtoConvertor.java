package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.shopping.domain.dto.req.SysRoleReqDto;
import com.shopping.domain.model.SysRoleEntity;
import com.shopping.domain.po.SysRolePo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysRoleReqDtoConvertor implements IConvertor<SysRoleReqDto,SysRoleEntity,SysRolePo> {

}








