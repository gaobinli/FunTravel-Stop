package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import com.shopping.domain.dto.res.SysMenuResDto;
import com.shopping.domain.model.SysMenuEntity;
import com.shopping.domain.po.SysMenuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysMenuResDtoConvertor implements IConvertor<SysMenuResDto,SysMenuEntity,SysMenuPo> {

    public Pager<SysMenuResDto> convertPoPager2ResDtoPager(Pager<SysMenuPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<SysMenuResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








