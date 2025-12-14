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
public class CarouselReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 轮播图名字 VARCHAR
     */
    @ApiModelProperty(value = "轮播图名字")
    @JsonProperty(index = 10)
    private String name;
    /**
     * 图片地址 VARCHAR
     */
    @ApiModelProperty(value = "图片地址")
    @JsonProperty(index = 20)
    private String img;
    /**
     * 排序 BIT
     */
    @ApiModelProperty(value = "排序")
    @JsonProperty(index = 30)
    private Integer sort;
}






