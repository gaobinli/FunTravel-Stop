package com.shopping.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sku_order")
public class OrderPo extends BasePo<OrderPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public OrderPo(){

    }
    public OrderPo(Long id){
        this.id = id;
    }

    /**
     * 用户编码 INT
     */
    private String phone;

    /**
     * 订单编号 VARCHAR
     */
    private String orderNo;

    /**
     * 发货时间 DATETIME
     */
    private LocalDateTime sendTime;

    /**
     * 收货时间 VARCHAR
     */
    private LocalDateTime receiveTime;

    private Integer status;

    private Long storeId;
}






