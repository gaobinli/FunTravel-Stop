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
public class ModuleSkuReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 模块id INT
     */
    @ApiModelProperty(value = "模块id")
    @JsonProperty(index = 10)
    private Integer moduleId;
    /**
     * 周边id INT
     */
    @ApiModelProperty(value = "skuid")
    @JsonProperty(index = 20)
    private Integer skuId;

}






