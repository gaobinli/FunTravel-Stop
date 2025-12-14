package com.shopping.common.convertor;

import com.shopping.domain.dto.req.SkuAppraiseReqDto;
import com.shopping.domain.model.SkuAppraiseEntity;
import com.shopping.domain.po.SkuAppraisePo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SkuAppraiseReqDtoConvertor implements IConvertor<SkuAppraiseReqDto,SkuAppraiseEntity,SkuAppraisePo> {

}








