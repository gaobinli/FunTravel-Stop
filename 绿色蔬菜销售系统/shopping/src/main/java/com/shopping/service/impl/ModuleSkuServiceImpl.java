package com.shopping.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.google.common.base.CaseFormat;
import com.shopping.common.convertor.ModuleSkuReqDtoConvertor;
import com.shopping.common.convertor.ModuleSkuResDtoConvertor;
import com.shopping.common.exception.AppException;
import com.shopping.common.exception.ErrorCode;
import com.shopping.domain.dto.req.ModuleSkuReqDto;
import com.shopping.domain.dto.res.ModuleSkuResDto;
import com.shopping.domain.po.ModuleSkuPo;
import com.shopping.mapper.ModuleSkuMapper;
import com.shopping.service.ModuleSkuService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service("ModuleSkuAppService")
@Transactional(rollbackFor = Exception.class)
public class ModuleSkuServiceImpl implements ModuleSkuService {

    @Autowired
    private ModuleSkuReqDtoConvertor moduleSkuReqDtoConvertor;

    @Autowired
    private ModuleSkuResDtoConvertor moduleSkuResDtoConvertor;

    private IPService<ModuleSkuPo> moduleSkuPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public ModuleSkuServiceImpl(ModuleSkuMapper ModuleSkuMapper) {
        this.moduleSkuPoService = new BasePService(ModuleSkuMapper);
    }


    @Override
    public ModuleSkuResDto save(ModuleSkuReqDto reqDto) {
        ModuleSkuPo po = moduleSkuReqDtoConvertor.dto2Po(reqDto);
        moduleSkuPoService.save(po);
        ModuleSkuResDto resDto = moduleSkuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return moduleSkuPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<ModuleSkuResDto> doPager(Map<String, Object> params) {
        Pager<ModuleSkuPo> poPager = moduleSkuPoService.queryPage(params, ModuleSkuPo.class);
        Pager<ModuleSkuResDto> resDtoPager = moduleSkuResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public ModuleSkuResDto selectOne(Long id) {
        ModuleSkuPo po = moduleSkuPoService.getById(id);
        ModuleSkuResDto resDto = moduleSkuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public ModuleSkuResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(ModuleSkuPo::new, params);
        ModuleSkuPo po = moduleSkuPoService.getOne(queryWrapper);
        ModuleSkuResDto resDto = moduleSkuResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<ModuleSkuPo> updateWrapper = QueryParamUtils.updateWrapper4Map(ModuleSkuPo::new, id, params);
        return moduleSkuPoService.update(new ModuleSkuPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, ModuleSkuReqDto reqDto) {
        ModuleSkuPo po = moduleSkuReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return moduleSkuPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, ModuleSkuReqDto reqDto) {
        UpdateWrapper<ModuleSkuPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = ModuleSkuReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), ModuleSkuReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return moduleSkuPoService.update(new ModuleSkuPo(), updateWrapper);
    }

}