package com.shopping.common.convertor;

import com.shopping.domain.dto.res.OrderResDto;
import com.shopping.domain.model.OrderEntity;
import com.shopping.domain.po.OrderPo;
import com.baomidou.mybatisplus.ext.IConvertor;
import com.baomidou.mybatisplus.ext.Pager;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class OrderResDtoConvertor implements IConvertor<OrderResDto,OrderEntity,OrderPo> {

    public  Pager<OrderResDto> convertPoPager2ResDtoPager(Pager<OrderPo> poPager) {
        if (poPager == null) {
            return null;
        }

        Pager<OrderResDto> resDtoPager = new Pager();
        resDtoPager.setTotalCount(poPager.getTotalCount());
        resDtoPager.setPageSize(poPager.getPageSize());
        resDtoPager.setTotalPage(poPager.getTotalPage());
        resDtoPager.setCurrentPage(poPager.getCurrentPage());
        resDtoPager.setList(poList2DtoList(poPager.getList()));

        return resDtoPager;
    }
}








