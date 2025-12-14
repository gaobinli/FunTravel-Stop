package com.shopping.service.impl;


import com.shopping.common.convertor.StoreReqDtoConvertor;
import com.shopping.common.convertor.StoreResDtoConvertor;
import com.shopping.domain.dto.req.StoreReqDto;
import com.shopping.domain.dto.res.StoreResDto;
import com.shopping.domain.po.StorePo;
import com.shopping.domain.po.SysUserPo;
import com.shopping.mapper.StoreMapper;
import com.shopping.mapper.SysUserMapper;
import com.shopping.service.StoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.CaseFormat;
import com.shopping.common.exception.AppException;
import com.shopping.common.exception.ErrorCode;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
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
@Service("storeAppService")
@Transactional(rollbackFor = Exception.class)
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreReqDtoConvertor storeReqDtoConvertor;

    @Autowired
    private StoreResDtoConvertor storeResDtoConvertor;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    private IPService<StorePo> storePoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public StoreServiceImpl(StoreMapper storeMapper) {
        this.storePoService = new BasePService(storeMapper);
    }


    @Override
    public StoreResDto save(StoreReqDto reqDto) {
        StorePo po = storeReqDtoConvertor.dto2Po(reqDto);
        storePoService.save(po);
        StoreResDto resDto = storeResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<StoreResDto> getList(Map<String, Object> params) {
        Pager<StorePo> poPager = storePoService.queryPage(params, StorePo.class);
        Pager<StoreResDto> resDtoPager = storeResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager.getList();
    }

    @Override
    public String getStoreIds(String merchantsCode) {
        List<StorePo> storePos = storeMapper.selectList(Wrappers.<StorePo>lambdaQuery().eq(StorePo::getMerchantsCode, merchantsCode));
        return storePos.stream().map(po -> String.valueOf(po.getId())).collect(Collectors.joining(","));
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return storePoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<StoreResDto> doPager(Map<String, Object> params) {
        Pager<StorePo> poPager = storePoService.queryPage(params, StorePo.class);
        Pager<StoreResDto> resDtoPager = storeResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        for (StoreResDto storeResDto : resDtoPager.getList()) {
            if (StringUtils.isNotBlank(storeResDto.getMerchantsCode())) {
                List<SysUserPo> sysUserPos = sysUserMapper.selectList(Wrappers.<SysUserPo>lambdaQuery().eq(SysUserPo::getCode, storeResDto.getMerchantsCode()));
                if (!CollectionUtils.isEmpty(sysUserPos)) {
                    storeResDto.setMerchantsName(sysUserPos.get(0).getName());
                }
            }
        }
        return resDtoPager;
    }

    @Override
    public StoreResDto selectOne(Long id) {
        StorePo po = storePoService.getById(id);
        StoreResDto resDto = storeResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public StoreResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(StorePo::new, params);
        StorePo po = storePoService.getOne(queryWrapper);
        StoreResDto resDto = storeResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<StorePo> updateWrapper = QueryParamUtils.updateWrapper4Map(StorePo::new, id, params);
        return storePoService.update(new StorePo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, StoreReqDto reqDto) {
        StorePo po = storeReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return storePoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, StoreReqDto reqDto) {
        UpdateWrapper<StorePo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = StoreReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), StoreReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return storePoService.update(new StorePo(), updateWrapper);
    }

}