package com.lqs.mall.controller;

import com.lqs.mall.common.api.CommonPage;
import com.lqs.mall.common.api.CommonResult;
import com.lqs.mall.domain.MemberBrandAttention;
import com.lqs.mall.service.MemberAttentionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 会员关注品牌管理Controller
 * Created by 刘千山 on 2023/7/5/005-11:01
 */
@RestController
@Api(tags = "MemberAttentionController")
@Tag(name = "MemberAttentionController",description = "会员关注品牌管理")
@RequestMapping("/member/attention")
public class MemberAttentionController {
    @Autowired
    private MemberAttentionService memberAttentionService;

    @ApiOperation("添加品牌关注")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<Object> add(@RequestBody MemberBrandAttention memberBrandAttention) {
        int count = memberAttentionService.add(memberBrandAttention);
        if(count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation("取消品牌关注")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult<Object> delete(Long brandId) {
        int count = memberAttentionService.delete(brandId);
        if(count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

    @ApiOperation("显示当前用户品牌关注列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<MemberBrandAttention>> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<MemberBrandAttention> page = memberAttentionService.list(pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation("显示品牌关注详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public CommonResult<MemberBrandAttention> detail(@RequestParam Long brandId) {
        MemberBrandAttention memberBrandAttention = memberAttentionService.detail(brandId);
        return CommonResult.success(memberBrandAttention);
    }

    @ApiOperation("清空当前用户品牌关注列表")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public CommonResult<Object> clear() {
        memberAttentionService.clear();
        return CommonResult.success(null);
    }
}
