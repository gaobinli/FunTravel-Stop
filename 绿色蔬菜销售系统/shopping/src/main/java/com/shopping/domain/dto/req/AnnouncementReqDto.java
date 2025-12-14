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
public class AnnouncementReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标题 VARCHAR
     */
    @ApiModelProperty(value = "标题")
    @JsonProperty(index = 10)


    private String title;
    /**
     * 内容 TEXT
     */
    @ApiModelProperty(value = "内容")
    @JsonProperty(index = 20)


    private String content;

}






