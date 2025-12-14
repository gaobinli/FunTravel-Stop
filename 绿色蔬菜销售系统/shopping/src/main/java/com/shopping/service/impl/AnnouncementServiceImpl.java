package com.shopping.service.impl;


import com.shopping.common.convertor.AnnouncementReqDtoConvertor;
import com.shopping.common.convertor.AnnouncementResDtoConvertor;
import com.shopping.domain.dto.req.AnnouncementReqDto;
import com.shopping.domain.dto.res.AnnouncementResDto;
import com.shopping.domain.po.AnnouncementPo;
import com.shopping.mapper.AnnouncementMapper;
import com.shopping.service.AnnouncementService;
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
@Service("announcementAppService")
@Transactional(rollbackFor = Exception.class)
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementReqDtoConvertor announcementReqDtoConvertor;

    @Autowired
    private AnnouncementResDtoConvertor announcementResDtoConvertor;

    private IPService<AnnouncementPo> announcementPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public AnnouncementServiceImpl(AnnouncementMapper announcementMapper) {
        this.announcementPoService = new BasePService(announcementMapper);
    }


    @Override
    public AnnouncementResDto save(AnnouncementReqDto reqDto) {
        AnnouncementPo po = announcementReqDtoConvertor.dto2Po(reqDto);
        announcementPoService.save(po);
        AnnouncementResDto resDto = announcementResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public List<AnnouncementResDto> getList() {
        List<AnnouncementPo> list = announcementPoService.list();
        return announcementResDtoConvertor.poList2DtoList(list);
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return announcementPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<AnnouncementResDto> doPager(Map<String, Object> params) {
        Pager<AnnouncementPo> poPager = announcementPoService.queryPage(params, AnnouncementPo.class);
        Pager<AnnouncementResDto> resDtoPager = announcementResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        return resDtoPager;
    }

    @Override
    public AnnouncementResDto selectOne(Long id) {
        AnnouncementPo po = announcementPoService.getById(id);
        AnnouncementResDto resDto = announcementResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public AnnouncementResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(AnnouncementPo::new, params);
        AnnouncementPo po = announcementPoService.getOne(queryWrapper);
        AnnouncementResDto resDto = announcementResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<AnnouncementPo> updateWrapper = QueryParamUtils.updateWrapper4Map(AnnouncementPo::new, id, params);
        return announcementPoService.update(new AnnouncementPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, AnnouncementReqDto reqDto) {
        AnnouncementPo po = announcementReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return announcementPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, AnnouncementReqDto reqDto) {
        UpdateWrapper<AnnouncementPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = AnnouncementReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), AnnouncementReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return announcementPoService.update(new AnnouncementPo(), updateWrapper);
    }

}