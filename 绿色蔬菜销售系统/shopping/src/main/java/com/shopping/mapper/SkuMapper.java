package com.shopping.mapper;

import com.baomidou.mybatisplus.ext.BaseExtMapper;
import com.shopping.domain.po.SkuPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SkuMapper extends BaseExtMapper<SkuPo> {
	
}
