package com.shopping.controller;

import com.shopping.domain.ResultDTO;
import com.shopping.service.ReportService;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@Api(tags = "report")
@RequestMapping("/report")
@SuppressWarnings("unchecked")
public class ReportController {

    private final ReportService reportService;

    @GetMapping(value = "/orderNumber", produces = {"application/json"})
    public ResultDTO<List<Integer>> orderNumber() {
        List<Integer> orderNumbers = reportService.orderNumber();
        return ResultDTO.ok(orderNumbers);
    }

    @GetMapping(value = "/orderSale", produces = {"application/json"})
    public ResultDTO<List<Integer>> orderSale() {
        List<Integer> orderNumbers = reportService.orderSale();
        return ResultDTO.ok(orderNumbers);
    }
}
