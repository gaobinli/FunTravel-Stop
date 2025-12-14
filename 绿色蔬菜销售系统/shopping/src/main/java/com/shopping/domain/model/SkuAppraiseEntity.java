package com.shopping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class SkuAppraiseEntity {


        /**
         *  INT
         */
        private Integer id;
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



}






