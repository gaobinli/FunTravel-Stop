package com.shopping.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shopping.common.util.ConditionUtil;
import com.shopping.common.convertor.SysUserResDtoConvertor;
import com.shopping.domain.model.LoginUserEntity;
import com.shopping.domain.model.SysUserEntity;
import com.shopping.domain.po.SysUserPo;
import com.shopping.domain.po.UserPo;
import com.shopping.mapper.SysUserMapper;
import com.shopping.mapper.UserMapper;
import com.shopping.service.SysMenuService;
import com.shopping.service.SysPermissionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Data
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysMenuService sysMenuService;
    private final SysUserMapper sysUserMapper;
    private final UserMapper userMapper;
    private final SysPermissionService sysPermissionService;

    @Autowired
    private SysUserResDtoConvertor sysUserResDtoConvertor;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUserEntity sysUserEntity = new SysUserEntity();
        if (username.contains("admin")) {
            username = username.split("-")[1];
            SysUserPo sysUserPo = sysUserMapper.selectOne(Wrappers.<SysUserPo>lambdaQuery().eq(SysUserPo::getCode, username));
            sysUserEntity = sysUserResDtoConvertor.po2Entity(sysUserPo);
        } else {
            username = username.split("-")[1];
            UserPo userPo = userMapper.selectOne(Wrappers.<UserPo>lambdaQuery().eq(UserPo::getPhone, username));
            ConditionUtil.checkNotNull(userPo, "亲！登录用户：" + username + " 不存在");
            sysUserEntity.setName(userPo.getNickName());
            sysUserEntity.setPassword(userPo.getPassword());
            sysUserEntity.setCode(userPo.getPhone());
            sysUserEntity.setEnable(userPo.getEnable());
        }

        ConditionUtil.checkNotNull(sysUserEntity, "亲！登录用户：" + username + " 不存在");
        ConditionUtil.checkArgument(sysUserEntity.getEnable(), "亲！账号已被禁用");

        return createLoginUser(sysUserEntity);
    }

    public UserDetails createLoginUser(SysUserEntity sysUserEntity) {
        Set<String> perms = new HashSet<>();
        perms.add("*:*:*");
//        return new LoginUserEntity(sysUserEntity, sysPermissionService.getMenuPermission(sysUserEntity.getCode()));
        return new LoginUserEntity(sysUserEntity, perms);
    }
}
