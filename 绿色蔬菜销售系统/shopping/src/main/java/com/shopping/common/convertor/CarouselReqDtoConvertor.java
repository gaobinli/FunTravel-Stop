package com.shopping.common.convertor;

import com.shopping.domain.dto.req.CarouselReqDto;
import com.shopping.domain.model.CarouselEntity;
import com.shopping.domain.po.CarouselPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class CarouselReqDtoConvertor implements IConvertor<CarouselReqDto,CarouselEntity,CarouselPo> {

}








