package com.shopping.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class OrderEntity {


        /**
         *  INT
         */
        private Integer id;
        /**
         * 用户编码 INT
         */
        private Integer userId;
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
        private String receiveTime;

        private Long storeId;
}






