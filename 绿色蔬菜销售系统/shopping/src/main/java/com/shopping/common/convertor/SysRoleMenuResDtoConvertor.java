package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import com.shopping.domain.dto.res.SysRoleMenuResDto;
import com.shopping.domain.model.SysRoleMenuEntity;
import com.shopping.domain.po.SysRoleMenuPo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysRoleMenuResDtoConvertor implements IConvertor<SysRoleMenuResDto,SysRoleMenuEntity,SysRoleMenuPo> {

    public Pager<SysRoleMenuResDto> convertPoPager2ResDtoPager(Pager<SysRoleMenuPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<SysRoleMenuResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








