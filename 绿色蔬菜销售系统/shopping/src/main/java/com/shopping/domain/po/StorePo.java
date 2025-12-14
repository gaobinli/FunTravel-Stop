package com.shopping.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.*;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "store")
public class StorePo extends BasePo<StorePo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public StorePo(){

    }
    public StorePo(Long id){
        this.id = id;
    }

    /**
     * 商家 VARCHAR
     */
    private String merchantsCode;

    /**
     * 产品名称 VARCHAR
     */
    private String name;

    /**
     * 店铺封面 VARCHAR
     */
    private String url;

    /**
     * 店铺简介 VARCHAR
     */
    private String simpleDesc;
}






