package com.shopping.common.convertor;

import com.shopping.domain.dto.res.SkuAppraiseResDto;
import com.shopping.domain.model.SkuAppraiseEntity;
import com.shopping.domain.po.SkuAppraisePo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class SkuAppraiseResDtoConvertor implements IConvertor<SkuAppraiseResDto,SkuAppraiseEntity,SkuAppraisePo> {

    public  Pager<SkuAppraiseResDto> convertPoPager2ResDtoPager(Pager<SkuAppraisePo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<SkuAppraiseResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








