package com.shopping.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
public class UserPo extends BasePo<UserPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public UserPo(){

    }
    public UserPo(Long id){
        this.id = id;
    }

    /**
     * 密码 VARCHAR
     */
    private String password;

    /**
     * 昵称 VARCHAR
     */
    private String nickName;

    /**
     * 手机号 VARCHAR
     */
    private String phone;

    /**
     * 年龄 TINYINT
     */
    private Integer age;

    /**
     * 地址 VARCHAR
     */
    private String address;

    /**
     * 性别，1 男 2 女 TINYINT
     */
    private Integer sex;

    private Boolean enable;
}






