package com.shopping.common.convertor;

import com.shopping.domain.dto.res.AnnouncementResDto;
import com.shopping.domain.model.AnnouncementEntity;
import com.shopping.domain.po.AnnouncementPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class AnnouncementResDtoConvertor implements IConvertor<AnnouncementResDto,AnnouncementEntity,AnnouncementPo> {

    public  Pager<AnnouncementResDto> convertPoPager2ResDtoPager(Pager<AnnouncementPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<AnnouncementResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








