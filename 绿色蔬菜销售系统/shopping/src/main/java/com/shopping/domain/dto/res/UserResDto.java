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
public class UserResDto implements Serializable {

    private static final long serialVersionUID = 1L;

/**
 * 主键 INT
 */
@ApiModelProperty(value = "主键")
@JsonProperty(index = 0)

private Integer id;
/**
 * 密码 VARCHAR
 */
@ApiModelProperty(value = "密码")
@JsonProperty(index = 10)

private String password;
/**
 * 昵称 VARCHAR
 */
@ApiModelProperty(value = "昵称")
@JsonProperty(index = 20)

private String nickName;
/**
 * 手机号 VARCHAR
 */
@ApiModelProperty(value = "手机号")
@JsonProperty(index = 30)

private String phone;
/**
 * 年龄 TINYINT
 */
@ApiModelProperty(value = "年龄")
@JsonProperty(index = 40)

private Integer age;
/**
 * 地址 VARCHAR
 */
@ApiModelProperty(value = "地址")
@JsonProperty(index = 50)

private String address;
/**
 * 性别，1 男 2 女 TINYINT
 */
@ApiModelProperty(value = "性别，1 男 2 女")
@JsonProperty(index = 60)

private Integer sex;
/**
 * 创建人 VARCHAR
 */
@ApiModelProperty(value = "创建人")
@JsonProperty(index = 80)

private String createUser;
/**
 * 创建时间 DATETIME
 */
@ApiModelProperty(value = "创建时间")
@JsonProperty(index = 90)
private LocalDateTime createTime;
/**
 * 修改人 VARCHAR
 */
@ApiModelProperty(value = "修改人")
@JsonProperty(index = 100)

private String modifyUser;
/**
 * 修改时间 DATETIME
 */
@ApiModelProperty(value = "修改时间")
@JsonProperty(index = 110)
private LocalDateTime modifyTime;

    private Boolean enable;
}






