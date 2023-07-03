package com.lqs.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询单个商品修改后返回的结果
 * Created by 刘千山 on 2023/7/3/003-16:34
 */

public class PmsProductResult extends PmsProductParam{
    @Getter
    @Setter
    @ApiModelProperty("商品所选分类的父id")
    private Long cateParentId;
}
