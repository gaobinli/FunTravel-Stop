package com.shopping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class CarouselEntity {


        /**
         * 主键 INT
         */
        private Integer id;
        /**
         * 轮播图名字 VARCHAR
         */
        private String name;
        /**
         * 图片地址 VARCHAR
         */
        private String img;
        /**
         * 排序 BIT
         */
        private Integer sort;



}






