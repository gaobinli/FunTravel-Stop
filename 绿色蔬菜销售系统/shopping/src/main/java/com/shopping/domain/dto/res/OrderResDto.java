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
public class OrderResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 *  INT
 */
@ApiModelProperty(value = "")
@JsonProperty(index = 0)

private Integer id;
/**
 * 用户编码 INT
 */
@ApiModelProperty(value = "用户编码")
@JsonProperty(index = 10)

private String phone;
/**
 * 订单编号 VARCHAR
 */
@ApiModelProperty(value = "订单编号")
@JsonProperty(index = 20)

private String orderNo;
/**
 * 发货时间 DATETIME
 */
@ApiModelProperty(value = "发货时间")
@JsonProperty(index = 30)
private LocalDateTime sendTime;
/**
 * 收货时间 VARCHAR
 */
@ApiModelProperty(value = "收货时间")
@JsonProperty(index = 40)

private LocalDateTime receiveTime;
/**
 * 创建人 VARCHAR
 */
@ApiModelProperty(value = "创建人")
@JsonProperty(index = 60)

private String createUser;
/**
 * 创建时间 DATETIME
 */
@ApiModelProperty(value = "创建时间")
@JsonProperty(index = 70)
private LocalDateTime createTime;
/**
 * 修改人 VARCHAR
 */
@ApiModelProperty(value = "修改人")
@JsonProperty(index = 80)

private String modifyUser;
/**
 * 修改时间 DATETIME
 */
@ApiModelProperty(value = "修改时间")
@JsonProperty(index = 90)
private LocalDateTime modifyTime;

private BigDecimal orderPrice;

private Integer status;

    private Long storeId;

    private String storeName;
}






