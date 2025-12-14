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
public class AnnouncementEntity {


        /**
         * 主键 INT
         */
        private Integer id;
        /**
         * 标题 VARCHAR
         */
        private String title;
        /**
         * 内容 TEXT
         */
        private String content;



}






