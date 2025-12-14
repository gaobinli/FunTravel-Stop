package com.shopping.domain.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class OrderReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户编码 INT
     */
    @ApiModelProperty(value = "用户手机号")
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

    private List<OrderDetailReqDto> orderDetails;

    private Integer status;

    private Long storeId;
}






