package com.shopping.common.convertor;

import com.shopping.domain.dto.req.AnnouncementReqDto;
import com.shopping.domain.model.AnnouncementEntity;
import com.shopping.domain.po.AnnouncementPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class AnnouncementReqDtoConvertor implements IConvertor<AnnouncementReqDto,AnnouncementEntity,AnnouncementPo> {

}








