package com.shopping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class SkuEntity {


        /**
         * 主键 INT
         */
        private Integer id;
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
        private Byte price;
        /**
         * 库存量 INT
         */
        private Integer inventory;

        private Long storeId;

}






