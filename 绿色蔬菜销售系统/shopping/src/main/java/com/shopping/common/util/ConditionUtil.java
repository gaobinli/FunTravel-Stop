package com.shopping.common.util;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.base.Preconditions;
import com.shopping.common.constant.ResCode;
import com.shopping.common.exception.AppException;

import java.util.Collection;

public class ConditionUtil {

    public static void checkNotNull(Object object, String msg) {
        try {
            Preconditions.checkNotNull(object, msg);
        } catch (NullPointerException e) {
            throw new AppException(ResCode.PARAM_VALID.getValue() + ": " + e.getMessage(), ResCode.PARAM_VALID.code());
        }
    }

    public static void checkArgument(Boolean bo, String msg) {
        try {
            Preconditions.checkArgument(bo, msg);
        } catch (IllegalArgumentException e) {
            throw new AppException(ResCode.PARAM_VALID.getValue() + ": " + e.getMessage(), ResCode.PARAM_VALID.code());
        }
    }

    public static void validCollectionIsEmpty(Collection<?> collection, String msg) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw new AppException(ResCode.PARAM_VALID.getValue() + ": " + msg, ResCode.PARAM_VALID.code());
        }
    }

    public static void validCollectionNotEmpty(Collection<?> collection, String msg) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new AppException(ResCode.PARAM_VALID.getValue()+": " + msg, ResCode.PARAM_VALID.code());
        }
    }
}
