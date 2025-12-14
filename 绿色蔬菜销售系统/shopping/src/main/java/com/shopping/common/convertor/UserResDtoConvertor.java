package com.shopping.common.convertor;

import com.shopping.domain.dto.res.UserResDto;
import com.shopping.domain.model.UserEntity;
import com.shopping.domain.po.UserPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class UserResDtoConvertor implements IConvertor<UserResDto,UserEntity,UserPo> {

    public  Pager<UserResDto> convertPoPager2ResDtoPager(Pager<UserPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<UserResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








