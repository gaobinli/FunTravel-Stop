package com.shopping.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.ext.BasePService;
import com.baomidou.mybatisplus.ext.IPService;
import com.baomidou.mybatisplus.ext.Pager;
import com.baomidou.mybatisplus.ext.QueryParamUtils;
import com.google.common.base.CaseFormat;
import com.shopping.common.convertor.OrderReqDtoConvertor;
import com.shopping.common.convertor.OrderResDtoConvertor;
import com.shopping.common.exception.AppException;
import com.shopping.common.exception.ErrorCode;
import com.shopping.common.util.IdGenerateUtil;
import com.shopping.domain.dto.req.OrderDetailReqDto;
import com.shopping.domain.dto.req.OrderReqDto;
import com.shopping.domain.dto.res.OrderResDto;
import com.shopping.domain.po.OrderDetailPo;
import com.shopping.domain.po.OrderPo;
import com.shopping.domain.po.SkuPo;
import com.shopping.domain.po.StorePo;
import com.shopping.mapper.OrderDetailMapper;
import com.shopping.mapper.OrderMapper;
import com.shopping.mapper.SkuMapper;
import com.shopping.mapper.StoreMapper;
import com.shopping.service.OrderService;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service("orderAppService")
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderReqDtoConvertor orderReqDtoConvertor;

    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private OrderResDtoConvertor orderResDtoConvertor;

    private IPService<OrderPo> orderPoService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderPoService = new BasePService(orderMapper);
    }


    @Override
    @Transactional
    public OrderResDto save(OrderReqDto reqDto) {
        for (OrderDetailReqDto orderDetail : reqDto.getOrderDetails()) {
            SkuPo skuPo = skuMapper.selectById(orderDetail.getSkuId());
            if (skuPo.getInventory() < orderDetail.getNumber()) {
                throw new AppException("啊哦，[" + skuPo.getName() + "]库存不够了");
            }
        }

        Map<Long, List<OrderDetailReqDto>> skuByStoreIdMap = reqDto.getOrderDetails().stream().collect(Collectors.groupingBy(OrderDetailReqDto::getStoreId));
        skuByStoreIdMap.forEach((k, v) -> {
            String orderNo = IdGenerateUtil.prefix("C");
            reqDto.setOrderNo(orderNo);
            reqDto.setStatus(0);
            reqDto.setStoreId(v.get(0).getStoreId());
            OrderPo po = orderReqDtoConvertor.dto2Po(reqDto);
            orderPoService.save(po);

            for (OrderDetailReqDto orderDetail : v) {
                SkuPo skuPo = skuMapper.selectById(orderDetail.getSkuId());
                skuPo.setInventory(skuPo.getInventory() - orderDetail.getNumber());
                skuMapper.updateById(skuPo);
                OrderDetailPo orderDetailPo = new OrderDetailPo();
                orderDetailPo.setOrderNo(orderNo);
                orderDetailPo.setSkuId(orderDetail.getSkuId());
                orderDetailPo.setNumber(orderDetail.getNumber());
                orderDetailPo.setPrice(orderDetail.getPrice());
                orderDetailPo.setStoreId(orderDetail.getStoreId());
                orderDetailMapper.insert(orderDetailPo);
            }
        });

        return null;
    }

    @Override
    public boolean deleteByIds(String ids) {
        try {
        List<Long> idLongList =
        Arrays.asList(ids.split(",")).stream().map(s -> NumberUtils.createLong(s.trim())).collect(Collectors.toList());
        return orderPoService.removeByIds(idLongList);
        } catch (Exception e) {
        throw new AppException("参数错误：" + ids, ErrorCode.sys("417"), e);
        }
     }

    @Override
    public Pager<OrderResDto> doPager(Map<String, Object> params) {
        Pager<OrderPo> poPager = orderPoService.queryPage(params, OrderPo.class);
        Pager<OrderResDto> resDtoPager = orderResDtoConvertor.convertPoPager2ResDtoPager(poPager);
        for (OrderResDto orderResDto : resDtoPager.getList()) {
            List<OrderDetailPo> orderDetailPos = orderDetailMapper.selectList(Wrappers.<OrderDetailPo>
                    lambdaQuery().eq(OrderDetailPo::getOrderNo, orderResDto.getOrderNo()));
            BigDecimal orderPrice = orderDetailPos.stream()
                    .map(detail -> detail.getPrice().multiply(new BigDecimal(detail.getNumber())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            orderResDto.setOrderPrice(orderPrice);
            if (null != orderResDto.getStoreId()) {
                StorePo storePo = storeMapper.selectById(orderResDto.getStoreId());
                if (null != storePo) {
                    orderResDto.setStoreName(storePo.getName());
                }
            }
        }
        return resDtoPager;
    }

    @Override
    public OrderResDto selectOne(Long id) {
        OrderPo po = orderPoService.getById(id);
        OrderResDto resDto = orderResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public OrderResDto selectOne(Map<String, Object> params) {
        QueryWrapper queryWrapper = QueryParamUtils.queryWrapper4eq(OrderPo::new, params);
        OrderPo po = orderPoService.getOne(queryWrapper);
        OrderResDto resDto = orderResDtoConvertor.po2Dto(po);
        return resDto;
    }

    @Override
    public boolean updateProps(Long id, Map<String, Object> params) {
        UpdateWrapper<OrderPo> updateWrapper = QueryParamUtils.updateWrapper4Map(OrderPo::new, id, params);
        return orderPoService.update(new OrderPo(), updateWrapper);
        }

    @Override
    public boolean updateProps(Long id, OrderReqDto reqDto) {
        OrderPo po = orderReqDtoConvertor.dto2Po(reqDto);
        po.setId(id);
        return orderPoService.updateById(po);
    }


    @Override
    public boolean updateAllProps(Long id, OrderReqDto reqDto) {
        UpdateWrapper<OrderPo> updateWrapper = Wrappers.update();
        updateWrapper.eq("id", id);
        Field[] fields = OrderReqDto.class.getDeclaredFields();
        Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).forEach(field -> {
        try {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), OrderReqDto.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        String fileNameCamel = getMethod.getName().substring(3);
        String fileNameUnderline = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fileNameCamel);
        updateWrapper.set(fileNameUnderline, getMethod.invoke(reqDto));
        } catch (Exception ex) {
        log.warn("属性不存在get方法："+field.getName(),ex);
        }
        });
        return orderPoService.update(new OrderPo(), updateWrapper);
    }

    @Override
    public void send(Long id) {
        OrderPo orderPo = new OrderPo();
        orderPo.setId(id);
        orderPo.setSendTime(LocalDateTime.now());
        orderPo.setStatus(1);
        orderPoService.updateById(orderPo);
    }

    @Override
    public void receipt(Long id) {
        OrderPo orderPo = new OrderPo();
        orderPo.setId(id);
        orderPo.setReceiveTime(LocalDateTime.now());
        orderPo.setStatus(2);
        orderPoService.updateById(orderPo);
    }

    @Override
    public void returnOrder(String ids, Integer status) {
        for (String s : ids.split(",")) {
            OrderPo orderPo = new OrderPo();
            orderPo.setId(Long.valueOf(s));
            orderPo.setStatus(status);
            orderPoService.updateById(orderPo);
        }
    }

}