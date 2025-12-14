package com.shopping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class ModuleEntity {


        /**
         *  INT
         */
        private Integer id;
        /**
         * 模块名 VARCHAR
         */
        private String name;
        /**
         * 排序 BIT
         */
        private Integer sort;



}






