package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.shopping.domain.dto.req.SkuReqDto;
import com.shopping.domain.model.SkuEntity;
import com.shopping.domain.po.SkuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SkuReqDtoConvertor implements IConvertor<SkuReqDto, SkuEntity, SkuPo> {

}








