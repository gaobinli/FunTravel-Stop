package com.shopping.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "order_detail")
public class OrderDetailPo extends BasePo<OrderDetailPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public OrderDetailPo(){

    }
    public OrderDetailPo(Long id){
        this.id = id;
    }

    /**
     * 订单编号 VARCHAR
     */
    private String orderNo;

    /**
     * skuId INT
     */
    private Integer skuId;

    /**
     * 数量 INT
     */
    private Integer number;

    private BigDecimal price;

    private Long storeId;
}






