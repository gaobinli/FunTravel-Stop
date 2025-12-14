package com.shopping.domain.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class OrderDetailResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 * 主键 INT
 */
@ApiModelProperty(value = "主键")
@JsonProperty(index = 0)

private Integer id;
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
/**
 * 创建人 VARCHAR
 */
@ApiModelProperty(value = "创建人")
@JsonProperty(index = 50)

private String createUser;
/**
 * 创建时间 DATETIME
 */
@ApiModelProperty(value = "创建时间")
@JsonProperty(index = 60)
private LocalDateTime createTime;
/**
 * 修改人 VARCHAR
 */
@ApiModelProperty(value = "修改人")
@JsonProperty(index = 70)

private String modifyUser;
/**
 * 修改时间 DATETIME
 */
@ApiModelProperty(value = "修改时间")
@JsonProperty(index = 80)
private LocalDateTime modifyTime;

private BigDecimal price;

private String skuName;

private String skuImg;

private Boolean appraiseFlag;

    private Long storeId;
    private String storeName;
}






