package com.shopping.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.ext.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "module_sku")
public class ModuleSkuPo extends BasePo<ModuleSkuPo> implements Serializable{

    private static final long serialVersionUID = 1L;

    public ModuleSkuPo(){

    }
    public ModuleSkuPo(Long id){
        this.id = id;
    }

    /**
     * 模块id INT
     */
    private Integer moduleId;

    /**
     * skuId INT
     */
    private Integer skuId;

}






