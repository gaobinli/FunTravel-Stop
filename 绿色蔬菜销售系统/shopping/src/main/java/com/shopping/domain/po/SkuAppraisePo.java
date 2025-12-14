package com.shopping.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sku_appraise")
public class SkuAppraisePo extends BasePo<SkuAppraisePo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public SkuAppraisePo(){

    }
    public SkuAppraisePo(Long id){
        this.id = id;
    }

    /**
     * skuId INT
     */
    private Integer skuId;

    /**
     * 评价人id INT
     */
    private Integer userId;

    /**
     * 评价内容 TEXT
     */
    private String content;

    /**
     * 物流评分 BIT
     */
    private Integer logisticeAppraise;

    /**
     * 商品评价 BIT
     */
    private Integer skuAppraise;

    private String orderNo;
}






