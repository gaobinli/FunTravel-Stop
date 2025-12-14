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
import java.util.List;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class SkuResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 * 主键 INT
 */
@ApiModelProperty(value = "主键")
@JsonProperty(index = 0)

private Integer id;
/**
 * 周边名称 VARCHAR
 */
@ApiModelProperty(value = "sku名称")
@JsonProperty(index = 10)

private String name;
/**
 * 图片地址 VARCHAR
 */
@ApiModelProperty(value = "图片地址")
@JsonProperty(index = 20)

private String img;
/**
 * 价格 DECIMAL
 */
@ApiModelProperty(value = "价格")
@JsonProperty(index = 30)

private BigDecimal price;
/**
 * 库存量 INT
 */
@ApiModelProperty(value = "库存量")
@JsonProperty(index = 40)

private Integer inventory;
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

private String moduleName;

private List<Integer> moduleIds;

private String detail;

    private Long storeId;

    private String storeName;
}






