package com.shopping.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "module")
public class ModulePo extends BasePo<ModulePo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public ModulePo(){

    }
    public ModulePo(Long id){
        this.id = id;
    }

    /**
     * 模块名 VARCHAR
     */
    private String name;

    /**
     * 排序 BIT
     */
    private Integer sort;

}






