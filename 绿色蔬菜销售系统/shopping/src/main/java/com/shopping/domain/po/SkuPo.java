package com.shopping.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sku")
public class SkuPo extends BasePo<SkuPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public SkuPo(){

    }
    public SkuPo(Long id){
        this.id = id;
    }

    /**
     * 周边名称 VARCHAR
     */
    private String name;

    /**
     * 图片地址 VARCHAR
     */
    private String img;

    /**
     * 价格 DECIMAL
     */
    private BigDecimal price;

    /**
     * 库存量 INT
     */
    private Integer inventory;

    private String detail;

    private Long storeId;
}






