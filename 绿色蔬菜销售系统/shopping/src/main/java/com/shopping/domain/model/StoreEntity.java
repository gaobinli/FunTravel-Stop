package com.shopping.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class StoreEntity {


        /**
         * 主键 INT
         */
        private Integer id;
        /**
         * 商家 VARCHAR
         */
        private String merchantsCode;
        /**
         * 产品名称 VARCHAR
         */
        private String name;
        /**
         * 店铺封面 VARCHAR
         */
        private String url;
        /**
         * 店铺简介 VARCHAR
         */
        private String simpleDesc;




}






