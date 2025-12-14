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

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class ModuleSkuResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 *  INT
 */
@ApiModelProperty(value = "")
@JsonProperty(index = 0)

private Integer id;
/**
 * 模块id INT
 */
@ApiModelProperty(value = "模块id")
@JsonProperty(index = 10)

private Integer moduleId;
/**
 * 周边id INT
 */
@ApiModelProperty(value = "skuId")
@JsonProperty(index = 20)

private Integer skuId;
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



}






