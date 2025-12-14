package com.shopping.mapper;

import com.shopping.domain.po.UserPo;
import com.baomidou.mybatisplus.ext.BaseExtMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseExtMapper<UserPo> {
	
}
