package com.shopping.domain.dto.res;

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
public class ModuleResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 *  INT
 */
@ApiModelProperty(value = "")
@JsonProperty(index = 0)

private Integer id;
/**
 * 模块名 VARCHAR
 */
@ApiModelProperty(value = "模块名")
@JsonProperty(index = 10)

private String name;
/**
 * 排序 BIT
 */
@ApiModelProperty(value = "排序")
@JsonProperty(index = 20)

private Integer sort;
/**
 * 创建人 VARCHAR
 */
@ApiModelProperty(value = "创建人")
@JsonProperty(index = 40)

private String createUser;
/**
 * 创建时间 DATETIME
 */
@ApiModelProperty(value = "创建时间")
@JsonProperty(index = 50)
private LocalDateTime createTime;
/**
 * 修改人 VARCHAR
 */
@ApiModelProperty(value = "修改人")
@JsonProperty(index = 60)

private String modifyUser;
/**
 * 修改时间 DATETIME
 */
@ApiModelProperty(value = "修改时间")
@JsonProperty(index = 70)
private LocalDateTime modifyTime;

List<SkuResDto> skus;
}






