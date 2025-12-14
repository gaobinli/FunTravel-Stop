package com.shopping.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.google.common.base.CaseFormat;
import com.shopping.common.convertor.ModuleReqDtoConvertor;
import com.shopping.common.convertor.ModuleResDtoConvertor;
import com.shopping.common.convertor.SkuResDtoConvertor;
import com.shopping.common.exception.AppException;
import com.shopping.common.exception.ErrorCode;
import com.shopping.common.util.MapUtil;
import com.shopping.domain.dto.req.ModuleReqDto;
import com.shopping.domain.dto.res.ModuleResDto;
import com.shopping.domain.po.ModulePo;
import com.shopping.domain.po.ModuleSkuPo;
import com.shopping.domain.po.SkuPo;
import com.shopping.mapper.ModuleMapper;
import com.shopping.mapper.ModuleSkuMapper;
import com.shopping.mapper.SkuMapper;
import com.shopping.service.ModuleService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("moduleAppService")
@Transactional(rollbackFor = Exception.class)
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleSkuMapper moduleSkuMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private ModuleReqDtoConvertor moduleReqDtoConvertor;
    @Autowired
    private SkuResDtoConvertor skuResDtoConvertor;
    @Autowired
    private ModuleResDtoConvertor moduleResDtoConvertor;

    private IPService<ModulePo> modulePoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public ModuleServiceImpl(ModuleMapper moduleMapper) {
        this.modulePoService = new BasePService(moduleMapper);
    }


    @Override
    public ModuleResDto save(ModuleReqDto reqDto) {
        ModulePo po = moduleReqDtoConvertor.dto2Po(reqDto);
        modulePoService.save(po);
        ModuleResDto resDto = moduleResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return modulePoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<ModuleResDto> doPager(Map<String, Object> params) {
        Pager<ModulePo> poPager = modulePoService.queryPage(params, ModulePo.class);
        Pager<ModuleResDto> resDtoPager = moduleResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public ModuleResDto selectOne(Long id) {
        ModulePo po = modulePoService.getById(id);
        ModuleResDto resDto = moduleResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<ModuleResDto> selectList() {
        List<ModulePo> list = modulePoService.getList(new MapUtil(), ModulePo.class);
        return moduleResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public List<ModuleResDto> selectModuleSku() {
        List<ModulePo> list = modulePoService.list(Wrappers.<ModulePo>lambdaQuery().orderByDesc(ModulePo::getSort));
        List<ModuleResDto> moduleResDtos = moduleResDtoConvertor.poList2DtoList(list);
        for (ModuleResDto moduleResDto : moduleResDtos) {
            Integer id = moduleResDto.getId();
            List<ModuleSkuPo> moduleSkuPos = moduleSkuMapper.selectList(Wrappers.<ModuleSkuPo>
                    lambdaQuery().eq(ModuleSkuPo::getModuleId, id));
            if (CollectionUtils.isEmpty(moduleSkuPos)) {
                continue;
            }
            List<Integer> skuIds = moduleSkuPos.stream()
                    .map(ModuleSkuPo::getSkuId)
                    .collect(Collectors.toList());
            List<SkuPo> skuPos = skuMapper.selectBatchIds(skuIds);
            moduleResDto.setSkus(skuResDtoConvertor.poList2DtoList(skuPos));
        }
        return moduleResDtos;
    }

    @Override
    public ModuleResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(ModulePo::new, params);
        ModulePo po = modulePoService.getOne(queryWrapper);
        ModuleResDto resDto = moduleResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<ModulePo> updateWrapper = QueryParamUtils.updateWrapper4Map(ModulePo::new, id, params);
        return modulePoService.update(new ModulePo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, ModuleReqDto reqDto) {
        ModulePo po = moduleReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return modulePoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, ModuleReqDto reqDto) {
        UpdateWrapper<ModulePo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = ModuleReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), ModuleReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return modulePoService.update(new ModulePo(), updateWrapper);
    }

}