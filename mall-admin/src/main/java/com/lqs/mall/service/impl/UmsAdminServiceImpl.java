package com.lqs.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.lqs.mall.bo.AdminUserDetails;
import com.lqs.mall.common.exception.Asserts;
import com.lqs.mall.common.util.RequestUtil;
import com.lqs.mall.dao.UmsAdminRoleRelationDao;
import com.lqs.mall.dto.UmsAdminLoginParam;
import com.lqs.mall.dto.UmsAdminParam;
import com.lqs.mall.mall.utils.JwtTokenUtil;
import com.lqs.mall.mapper.UmsAdminLoginLogMapper;
import com.lqs.mall.mapper.UmsAdminMapper;
import com.lqs.mall.model.*;
import com.lqs.mall.service.UmsAdminService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 后台用户Service实现类
 * Created by 刘千山 on 2023/6/30/030-15:13
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);


    @Autowired
    private UmsAdminLoginLogMapper loginLogMapper;

    @Autowired
    private UmsAdminMapper adminMapper;

    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 根据用户名获取用户
     */
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        // TODO 先从缓存中获取数据
        // 从数据库获取
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(umsAdminExample);

        if (umsAdmins != null && umsAdmins.size() > 0) {
            return umsAdmins.get(0);
            // TODO 存入缓存当中
        }
        return null;
    }

    /**
     * 用户注册
     */
    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    /**
     * 用户登录，返回token
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            // 如果密码不匹配
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("密码不正确");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail("账户已禁用");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
            //TODO 更新用户登录时间 日志
            updateLoginTimeByUsername(username);
            //TODO 插入用户登录记录
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.info("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 插入登录日志
     */
    private void insertLoginLog(String username) {
        UmsAdmin umsAdmin = getAdminByUsername(username);
        if (umsAdmin == null) {
            return;
        }
        UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
        loginLog.setAdminId(umsAdmin.getId());
        loginLog.setCreateTime(new Date());
        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 设置 登录的ip
        loginLog.setIp(RequestUtil.getRequestIp(request));
        loginLogMapper.insert(loginLog);
    }

    /**
     * 更新用户登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setLoginTime(new Date());
        // 给用户名为username的用户更新登录时间
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        adminMapper.updateByExampleSelective(umsAdmin, example);
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return null;
    }

    @Override
    public List<UmsResource> getResouceList(Long adminId) {
        // TODO 先从缓存中获取
        // 数据库中获取
        List<UmsResource> resourceList = adminRoleRelationDao.getResourceList(adminId);
        if (CollUtil.isNotEmpty(resourceList)) {
            // TODO 存入缓存中
        }
        return resourceList;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 获取用户信息
        UmsAdmin umsAdmin = getAdminByUsername(username);
        if (umsAdmin != null) {
            List<UmsResource> resouceList = getResouceList(umsAdmin.getId());
            return new AdminUserDetails(umsAdmin, resouceList);
        }
        throw new UsernameNotFoundException("用户名或者密码错误");
    }


}
