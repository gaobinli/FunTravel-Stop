package com.shopping.mapper;

import com.shopping.domain.po.OrderPo;
import com.baomidou.mybatisplus.ext.BaseExtMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseExtMapper<OrderPo> {
	
}
