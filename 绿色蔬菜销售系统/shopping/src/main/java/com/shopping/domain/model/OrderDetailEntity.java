package com.shopping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class OrderDetailEntity {


        /**
         * 主键 INT
         */
        private Integer id;
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

        private Long storeId;

}






