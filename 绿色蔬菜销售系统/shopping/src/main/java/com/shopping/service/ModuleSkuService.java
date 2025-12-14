package com.shopping.service;

import com.baomidou.mybatisplus.ext.Pager;
import com.shopping.domain.dto.req.ModuleSkuReqDto;
import com.shopping.domain.dto.res.ModuleSkuResDto;

import java.util.Map;

public interface ModuleSkuService {

    /**
     * 保存一个对象
     * @param reqDto
     * @return
     */
    ModuleSkuResDto save(ModuleSkuReqDto reqDto);

    /**
     * 删除（支持批量）
     * @param ids
     * @return
     */
    boolean deleteByIds(String ids);

    /**
     * 分页查询
     * @param params
     * @return
     */
    Pager<ModuleSkuResDto> doPager(Map<String, Object> params);

    /**
     * 根据id查询一个对象
     * @param id
     * @return
     */
    ModuleSkuResDto selectOne(Long id);

    /**
     * 根据其它参数查询一个对象
     * @param params 查询参数
     */
    ModuleSkuResDto selectOne(Map<String, Object> params);

    /**
     * 更新一个资源对象(更新参数的全部属性)
     * @param id
     * @param params
     * @return
     */
    boolean updateProps(Long id, Map<String, Object> params);

    /**
     * 更新一个资源对象(部分更新)
     * @param id
     * @param reqDto
     * @return
     */
    boolean updateProps(Long id, ModuleSkuReqDto reqDto);

    /**
     * 更新一个资源对象（整个对象替换）
     * @param id
     * @param reqDto
     * @return
     */
    boolean updateAllProps(Long id, ModuleSkuReqDto reqDto);

}