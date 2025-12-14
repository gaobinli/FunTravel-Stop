package com.shopping.domain.dto.res;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class ReportResDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer monthValue;

    private Integer numberValue;
}
