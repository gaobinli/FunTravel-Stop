package com.shopping.domain.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.math.BigDecimal;


@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class OrderDetailReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单编号 VARCHAR
     */
    @ApiModelProperty(value = "订单编号")
    @JsonProperty(index = 10)
    private String orderNo;
    /**
     * 周边id INT
     */
    @ApiModelProperty(value = "skuId")
    @JsonProperty(index = 20)
    private Integer skuId;
    /**
     * 数量 INT
     */
    @ApiModelProperty(value = "数量")
    @JsonProperty(index = 30)
    private Integer number;

    private BigDecimal price;

    private Long storeId;
}






