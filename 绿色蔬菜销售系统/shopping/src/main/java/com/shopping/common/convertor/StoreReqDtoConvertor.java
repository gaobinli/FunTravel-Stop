package com.shopping.common.convertor;

import com.shopping.domain.dto.req.StoreReqDto;
import com.shopping.domain.model.StoreEntity;
import com.shopping.domain.po.StorePo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class StoreReqDtoConvertor implements IConvertor<StoreReqDto,StoreEntity,StorePo> {

}








