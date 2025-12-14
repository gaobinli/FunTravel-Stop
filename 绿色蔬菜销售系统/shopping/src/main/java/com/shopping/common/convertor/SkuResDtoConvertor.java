package com.shopping.common.convertor;

import com.shopping.domain.dto.res.SkuResDto;
import com.shopping.domain.model.SkuEntity;
import com.shopping.domain.po.SkuPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SkuResDtoConvertor implements IConvertor<SkuResDto,SkuEntity,SkuPo> {

    public  Pager<SkuResDto> convertPoPager2ResDtoPager(Pager<SkuPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<SkuResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








