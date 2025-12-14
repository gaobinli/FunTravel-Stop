package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import com.shopping.domain.dto.res.ModuleSkuResDto;
import com.shopping.domain.model.ModuleSkuEntity;
import com.shopping.domain.po.ModuleSkuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class ModuleSkuResDtoConvertor implements IConvertor<ModuleSkuResDto, ModuleSkuEntity, ModuleSkuPo> {

    public  Pager<ModuleSkuResDto> convertPoPager2ResDtoPager(Pager<ModuleSkuPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<ModuleSkuResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








