package com.shopping.common.convertor;

import com.shopping.domain.dto.req.OrderDetailReqDto;
import com.shopping.domain.model.OrderDetailEntity;
import com.shopping.domain.po.OrderDetailPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class OrderDetailReqDtoConvertor implements IConvertor<OrderDetailReqDto,OrderDetailEntity,OrderDetailPo> {

}








