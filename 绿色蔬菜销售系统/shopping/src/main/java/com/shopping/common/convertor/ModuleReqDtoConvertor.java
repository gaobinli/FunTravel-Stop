package com.shopping.common.convertor;

import com.shopping.domain.dto.req.ModuleReqDto;
import com.shopping.domain.model.ModuleEntity;
import com.shopping.domain.po.ModulePo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class ModuleReqDtoConvertor implements IConvertor<ModuleReqDto,ModuleEntity,ModulePo> {

}








