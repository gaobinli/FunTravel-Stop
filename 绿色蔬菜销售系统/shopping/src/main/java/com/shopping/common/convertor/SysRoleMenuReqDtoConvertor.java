package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.shopping.domain.dto.req.SysRoleMenuReqDto;
import com.shopping.domain.model.SysRoleMenuEntity;
import com.shopping.domain.po.SysRoleMenuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysRoleMenuReqDtoConvertor implements IConvertor<SysRoleMenuReqDto,SysRoleMenuEntity,SysRoleMenuPo> {

}








