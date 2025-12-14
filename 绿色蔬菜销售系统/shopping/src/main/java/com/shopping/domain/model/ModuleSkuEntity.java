package com.shopping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class ModuleSkuEntity {


        /**
         *  INT
         */
        private Integer id;
        /**
         * 模块id INT
         */
        private Integer moduleId;
        /**
         * skuId INT
         */
        private Integer skuId;



}






