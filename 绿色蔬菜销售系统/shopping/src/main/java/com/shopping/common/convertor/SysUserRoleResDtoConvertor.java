package com.shopping.common.convertor;

import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import com.shopping.domain.dto.res.SysUserRoleResDto;
import com.shopping.domain.model.SysUserRoleEntity;
import com.shopping.domain.po.SysUserRolePo;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SysUserRoleResDtoConvertor implements IConvertor<SysUserRoleResDto,SysUserRoleEntity,SysUserRolePo> {

    public Pager<SysUserRoleResDto> convertPoPager2ResDtoPager(Pager<SysUserRolePo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<SysUserRoleResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








