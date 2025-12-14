package com.shopping.mapper;

import com.baomidou.mybatisplus.ext.BaseExtMapper;
import com.shopping.domain.dto.res.ReportResDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReportMapper extends BaseExtMapper<ReportResDTO> {

    List<ReportResDTO> selectOrderNumber(LocalDateTime startTime);

    List<ReportResDTO> selectOrderSale(LocalDateTime startTime);
}
