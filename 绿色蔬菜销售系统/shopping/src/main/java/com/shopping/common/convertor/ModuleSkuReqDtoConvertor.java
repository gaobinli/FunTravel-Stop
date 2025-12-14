package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.shopping.domain.dto.req.ModuleSkuReqDto;
import com.shopping.domain.model.ModuleSkuEntity;
import com.shopping.domain.po.ModuleSkuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class ModuleSkuReqDtoConvertor implements IConvertor<ModuleSkuReqDto, ModuleSkuEntity, ModuleSkuPo> {

}








