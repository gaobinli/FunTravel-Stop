package com.shopping.mapper;

import com.shopping.domain.po.OrderDetailPo;
import com.baomidou.mybatisplus.ext.BaseExtMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseExtMapper<OrderDetailPo> {
	
}
