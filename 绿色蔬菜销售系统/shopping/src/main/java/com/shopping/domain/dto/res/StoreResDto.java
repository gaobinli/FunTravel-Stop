package com.shopping.domain.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class StoreResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 * 主键 INT
 */
@ApiModelProperty(value = "主键")
@JsonProperty(index = 0)

private Integer id;
/**
 * 商家 VARCHAR
 */
@ApiModelProperty(value = "商家")
@JsonProperty(index = 10)

private String merchantsCode;

    private String merchantsName;
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




}






