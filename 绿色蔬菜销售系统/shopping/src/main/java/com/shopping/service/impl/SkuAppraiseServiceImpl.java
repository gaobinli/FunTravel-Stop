package com.shopping.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.google.common.base.CaseFormat;
import com.shopping.common.convertor.SkuAppraiseReqDtoConvertor;
import com.shopping.common.convertor.SkuAppraiseResDtoConvertor;
import com.shopping.common.exception.AppException;
import com.shopping.common.exception.ErrorCode;
import com.shopping.domain.dto.req.SkuAppraiseReqDto;
import com.shopping.domain.dto.res.SkuAppraiseResDto;
import com.shopping.domain.po.SkuAppraisePo;
import com.shopping.domain.po.SkuPo;
import com.shopping.domain.po.UserPo;
import com.shopping.mapper.SkuAppraiseMapper;
import com.shopping.mapper.SkuMapper;
import com.shopping.mapper.UserMapper;
import com.shopping.service.SkuAppraiseService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@Service("SkuAppraiseAppService")
@Transactional(rollbackFor = Exception.class)
public class SkuAppraiseServiceImpl implements SkuAppraiseService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private SkuMapper SkuMapper;

    @Autowired
    private SkuAppraiseReqDtoConvertor skuAppraiseReqDtoConvertor;

    @Autowired
    private SkuAppraiseResDtoConvertor skuAppraiseResDtoConvertor;

    private IPService<SkuAppraisePo> SkuAppraisePoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public SkuAppraiseServiceImpl(SkuAppraiseMapper SkuAppraiseMapper) {
        this.SkuAppraisePoService = new BasePService(SkuAppraiseMapper);
    }


    @Override
    public SkuAppraiseResDto save(SkuAppraiseReqDto reqDto) {
        SkuAppraisePo po = skuAppraiseReqDtoConvertor.dto2Po(reqDto);
        SkuAppraisePoService.save(po);
        SkuAppraiseResDto resDto = skuAppraiseResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return SkuAppraisePoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<SkuAppraiseResDto> doPager(Map<String, Object> params) {
        Pager<SkuAppraisePo> poPager = SkuAppraisePoService.queryPage(params, SkuAppraisePo.class);
        Pager<SkuAppraiseResDto> resDtoPager = skuAppraiseResDtoConvertor.convertPoPager2ResDtoPager(poPager);

        resDtoPager.getList().stream().forEach(dto -> {
            UserPo userPo = userMapper.selectById(dto.getUserId());
            if (userPo != null) {
                dto.setUserName(userPo.getNickName());
            }
            SkuPo skuPo = SkuMapper.selectById(dto.getSkuId());
            if (skuPo != null) {
                dto.setSkuName(skuPo.getName());
                dto.setSkuImg(skuPo.getImg());
            }
        });

        return resDtoPager;
    }

    @Override
    public SkuAppraiseResDto selectOne(Long id) {
        SkuAppraisePo po = SkuAppraisePoService.getById(id);
        SkuAppraiseResDto resDto = skuAppraiseResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public SkuAppraiseResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(SkuAppraisePo::new, params);
        SkuAppraisePo po = SkuAppraisePoService.getOne(queryWrapper);
        SkuAppraiseResDto resDto = skuAppraiseResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<SkuAppraisePo> updateWrapper = QueryParamUtils.updateWrapper4Map(SkuAppraisePo::new, id, params);
        return SkuAppraisePoService.update(new SkuAppraisePo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, SkuAppraiseReqDto reqDto) {
        SkuAppraisePo po = skuAppraiseReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return SkuAppraisePoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, SkuAppraiseReqDto reqDto) {
        UpdateWrapper<SkuAppraisePo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = SkuAppraiseReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), SkuAppraiseReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return SkuAppraisePoService.update(new SkuAppraisePo(), updateWrapper);
    }

}