package com.shopping.common.convertor;

import com.shopping.domain.dto.res.ModuleResDto;
import com.shopping.domain.model.ModuleEntity;
import com.shopping.domain.po.ModulePo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class ModuleResDtoConvertor implements IConvertor<ModuleResDto,ModuleEntity,ModulePo> {

    public  Pager<ModuleResDto> convertPoPager2ResDtoPager(Pager<ModulePo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<ModuleResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








