package com.lqs.mall.controller;

import com.lqs.mall.common.api.CommonResult;
import com.lqs.mall.dto.UmsAdminLoginParam;
import com.lqs.mall.dto.UmsAdminParam;
import com.lqs.mall.model.UmsAdmin;
import com.lqs.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.simpleframework.xml.core.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘千山 on 2023/6/30/030-16:07
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "UmsAdminController", description = "后台用户管理")
public class UmsAdminController {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService adminService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<Object> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin admin = adminService.register(umsAdminParam);
        if (admin == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(admin);
    }

    @ApiOperation(value = "用户登录，返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<Object> login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam){
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if(token == null){
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("tokenHead",tokenHead);
        return CommonResult.success(map);
    }

}
