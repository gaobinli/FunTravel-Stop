package com.shopping.service.impl;

import com.shopping.domain.dto.res.ReportResDTO;
import com.shopping.mapper.ReportMapper;
import com.shopping.service.ReportService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;

    @Override
    public List<Integer> orderNumber() {
        LocalDate firstDayOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDateTime firstDateOfYear = LocalDateTime.of(firstDayOfYear, LocalTime.MIN);
        List<ReportResDTO> reportResDTOS = reportMapper.selectOrderNumber(firstDateOfYear);
        Map<Integer, Integer> map = reportResDTOS.stream()
                .collect(Collectors.toMap(ReportResDTO::getMonthValue, ReportResDTO::getNumberValue));
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Integer value = map.get(i);
            result.add(value == null ? 0 : value);
        }
        return result;
    }

    @Override
    public List<Integer> orderSale() {
        LocalDate firstDayOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDateTime firstDateOfYear = LocalDateTime.of(firstDayOfYear, LocalTime.MIN);
        List<ReportResDTO> reportResDTOS = reportMapper.selectOrderSale(firstDateOfYear);
        Map<Integer, Integer> map = reportResDTOS.stream()
                .collect(Collectors.toMap(ReportResDTO::getMonthValue, ReportResDTO::getNumberValue));
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Integer value = map.get(i);
            result.add(value == null ? 0 : value);
        }
        return result;
    }
}
