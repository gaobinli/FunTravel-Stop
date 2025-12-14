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
import java.util.List;


@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class SkuReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 周边名称 VARCHAR
     */
    @ApiModelProperty(value = "周边名称")
    @JsonProperty(index = 10)
    private String name;
    /**
     * 图片地址 VARCHAR
     */
    @ApiModelProperty(value = "图片地址")
    @JsonProperty(index = 20)
    private String img;
    /**
     * 价格 DECIMAL
     */
    @ApiModelProperty(value = "价格")
    @JsonProperty(index = 30)
    private BigDecimal price;
    /**
     * 库存量 INT
     */
    @ApiModelProperty(value = "库存量")
    @JsonProperty(index = 40)
    private Integer inventory;

    private List<Integer> moduleIds;

    private String detail;

    private Long storeId;

    private String storeName;
}






