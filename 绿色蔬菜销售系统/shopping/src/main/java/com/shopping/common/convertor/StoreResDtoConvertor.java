package com.shopping.common.convertor;

import com.shopping.domain.dto.res.StoreResDto;
import com.shopping.domain.model.StoreEntity;
import com.shopping.domain.po.StorePo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class StoreResDtoConvertor implements IConvertor<StoreResDto,StoreEntity,StorePo> {

    public  Pager<StoreResDto> convertPoPager2ResDtoPager(Pager<StorePo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<StoreResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








