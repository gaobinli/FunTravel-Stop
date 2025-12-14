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
public class UserReqDto implements Serializable {
    private static final long serialVersionUID = 1L;

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

    private Boolean enable;
}






