package com.shopping.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class UserEntity {


        /**
         * 主键 INT
         */
        private Integer id;
        /**
         * 密码 VARCHAR
         */
        private String password;
        /**
         * 昵称 VARCHAR
         */
        private String nickName;
        /**
         * 手机号 VARCHAR
         */
        private String phone;
        /**
         * 年龄 TINYINT
         */
        private Integer age;
        /**
         * 地址 VARCHAR
         */
        private String address;
        /**
         * 性别，1 男 2 女 TINYINT
         */
        private Integer sex;
}






