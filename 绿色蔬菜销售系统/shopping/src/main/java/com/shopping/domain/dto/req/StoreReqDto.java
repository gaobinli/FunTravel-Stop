package com.shopping.domain.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class StoreReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商家 VARCHAR
     */
    @ApiModelProperty(value = "商家")
    @JsonProperty(index = 10)
    private String merchantsCode;
    /**
     * 产品名称 VARCHAR
     */
    @ApiModelProperty(value = "产品名称")
    @JsonProperty(index = 20)
    private String name;
    /**
     * 店铺封面 VARCHAR
     */
    @ApiModelProperty(value = "店铺封面")
    @JsonProperty(index = 30)
    private String url;
    /**
     * 店铺简介 VARCHAR
     */
    @ApiModelProperty(value = "店铺简介")
    @JsonProperty(index = 40)
    private String simpleDesc;
}






