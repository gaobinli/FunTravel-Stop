package com.shopping.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.google.common.base.CaseFormat;
import com.shopping.common.convertor.SkuReqDtoConvertor;
import com.shopping.common.convertor.SkuResDtoConvertor;
import com.shopping.common.exception.AppException;
import com.shopping.common.exception.ErrorCode;
import com.shopping.domain.dto.req.SkuReqDto;
import com.shopping.domain.dto.res.SkuResDto;
import com.shopping.domain.po.*;
import com.shopping.mapper.*;
import com.shopping.service.SkuService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Service("SkuAppService")
@Transactional(rollbackFor = Exception.class)
public class SkuServiceImpl implements SkuService {

    @Autowired
    private ModuleSkuMapper moduleSkuMapper;
    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private SkuReqDtoConvertor skuReqDtoConvertor;

    @Autowired
    private SkuResDtoConvertor skuResDtoConvertor;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private StoreMapper storeMapper;

    private IPService<SkuPo> skuPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SkuServiceImpl(SkuMapper SkuMapper) {
        this.skuPoService = new BasePService(SkuMapper);
    }


    @Override
    public SkuResDto save(SkuReqDto reqDto) {
        SkuPo po = skuReqDtoConvertor.dto2Po(reqDto);
        skuPoService.save(po);
        if (null != reqDto.getModuleIds()) {
            for (Integer moduleId : reqDto.getModuleIds()) {
                ModuleSkuPo moduleSkuPo = new ModuleSkuPo();
                moduleSkuPo.setModuleId(moduleId);
                moduleSkuPo.setSkuId(po.getId().intValue());
                moduleSkuMapper.insert(moduleSkuPo);
            }
        }
        SkuResDto resDto = skuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return skuPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<SkuResDto> doPager(Map<String, Object> params) {
        Pager<SkuPo> poPager = skuPoService.queryPage(params, SkuPo.class);
        Pager<SkuResDto> resDtoPager = skuResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        addModuleName(resDtoPager.getList());
        return resDtoPager;
    }

    private void addModuleName(List<SkuResDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (SkuResDto skuResDto : list) {
            Long storeId = skuResDto.getStoreId();
            if (null != storeId) {
                StorePo storePo = storeMapper.selectById(storeId);
                if (null != storePo) {
                    skuResDto.setStoreName(storePo.getName());
                }
            }
        }
        List<Integer> SkuIds = list.stream()
                .map(SkuResDto::getId)
                .collect(Collectors.toList());
        List<ModuleSkuPo> moduleSkuPos = moduleSkuMapper.selectList(Wrappers.<ModuleSkuPo>
                lambdaQuery().in(ModuleSkuPo::getSkuId, SkuIds));
        if (CollectionUtils.isEmpty(moduleSkuPos)) {
            return;
        }
        List<Integer> moduleIds = moduleSkuPos.stream()
                .map(ModuleSkuPo::getModuleId)
                .collect(Collectors.toList());
        List<ModulePo> modulePos = moduleMapper.selectList(Wrappers.<ModulePo>
                lambdaQuery().in(ModulePo::getId, moduleIds));
        Map<Integer, List<ModuleSkuPo>> moduleSkuMap = moduleSkuPos.stream()
                .collect(Collectors.groupingBy(ModuleSkuPo::getSkuId));
        Map<Long, String> moduleMap = modulePos.stream()
                .collect(Collectors.toMap(ModulePo::getId, ModulePo::getName));
        for (SkuResDto skuResDto : list) {
            Integer id = skuResDto.getId();
            List<ModuleSkuPo> modules = moduleSkuMap.get(id);
            if (CollectionUtils.isEmpty(modules)) {
                continue;
            }
            for (ModuleSkuPo module : modules) {
                String moduelName = moduleMap.get(module.getModuleId());
                if (StringUtils.isEmpty(moduelName)) {
                    continue;
                }
                skuResDto.setModuleName(moduelName);
            }

        }
    }

    @Override
    public SkuResDto selectOne(Long id) {
        SkuPo po = skuPoService.getById(id);
        SkuResDto resDto = skuResDtoConvertor.po2Dto(po);
        List<ModuleSkuPo> moduleSkuPos = moduleSkuMapper.selectList(Wrappers.<ModuleSkuPo>
                lambdaQuery().eq(ModuleSkuPo::getSkuId, id));
        List<Integer> moduleIds = moduleSkuPos.stream()
                .map(ModuleSkuPo::getModuleId)
                .collect(Collectors.toList());
        resDto.setModuleIds(moduleIds);
        if (!CollectionUtils.isEmpty(moduleIds)) {
            List<ModulePo> modulePos = moduleMapper.selectList(Wrappers.<ModulePo>
                    lambdaQuery().in(ModulePo::getId, moduleIds));
            String moduleNames = modulePos.stream()
                    .map(ModulePo::getName)
                    .collect(Collectors.joining("，"));
            resDto.setModuleName(moduleNames);
        }

        return resDto;
    }

    @Override
    public SkuResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(SkuPo::new, params);
        SkuPo po = skuPoService.getOne(queryWrapper);
        SkuResDto resDto = skuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<SkuResDto> selectList() {
        List<SkuPo> list = skuPoService.list();
        return skuResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<SkuPo> updateWrapper = QueryParamUtils.updateWrapper4Map(SkuPo::new, id, params);
        return skuPoService.update(new SkuPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, SkuReqDto reqDto) {
        SkuPo po = skuReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        moduleSkuMapper.delete(Wrappers.<ModuleSkuPo>lambdaQuery().eq(ModuleSkuPo::getSkuId, id));
        for (Integer moduleId : reqDto.getModuleIds()) {
            ModuleSkuPo moduleSkuPo = new ModuleSkuPo();
            moduleSkuPo.setModuleId(moduleId);
            moduleSkuPo.setSkuId(po.getId().intValue());
            moduleSkuMapper.insert(moduleSkuPo);
        }
        return skuPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, SkuReqDto reqDto) {
        UpdateWrapper<SkuPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = SkuReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), SkuReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return skuPoService.update(new SkuPo(), updateWrapper);
    }

}