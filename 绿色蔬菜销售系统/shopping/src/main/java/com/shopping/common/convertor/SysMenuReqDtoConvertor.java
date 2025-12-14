package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.shopping.domain.dto.req.SysMenuReqDto;
import com.shopping.domain.model.SysMenuEntity;
import com.shopping.domain.po.SysMenuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysMenuReqDtoConvertor implements IConvertor<SysMenuReqDto,SysMenuEntity,SysMenuPo> {

}








