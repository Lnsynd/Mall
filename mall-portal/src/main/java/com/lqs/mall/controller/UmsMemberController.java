package com.lqs.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册登录管理
 * Created by 刘千山 on 2023/7/3/003-11:16
 */
@RestController
@Api(tags = "UmsMemberController")
@Tag(name = "UmsMemberController", description = "会员登录注册管理")
@RequestMapping("/sso")
public class UmsMemberController {
}
