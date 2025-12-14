package com.shopping.domain.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;


@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class SkuAppraiseReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id INT
     */
    @ApiModelProperty(value = "skuId")
    @JsonProperty(index = 10)
    private Integer skuId;
    /**
     * 评价人id INT
     */
    @ApiModelProperty(value = "评价人id")
    @JsonProperty(index = 20)
    private Integer userId;
    /**
     * 评价内容 TEXT
     */
    @ApiModelProperty(value = "评价内容")
    @JsonProperty(index = 30)
    private String content;
    /**
     * 物流评分 BIT
     */
    @ApiModelProperty(value = "物流评分")
    @JsonProperty(index = 40)
    private Integer logisticeAppraise;
    /**
     * 商品评价 BIT
     */
    @ApiModelProperty(value = "商品评价")
    @JsonProperty(index = 50)
    private Integer skuAppraise;

    private String orderNo;
}






