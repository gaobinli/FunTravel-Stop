package com.shopping.common.convertor;

import com.shopping.domain.dto.req.OrderReqDto;
import com.shopping.domain.model.OrderEntity;
import com.shopping.domain.po.OrderPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class OrderReqDtoConvertor implements IConvertor<OrderReqDto,OrderEntity,OrderPo> {

}








