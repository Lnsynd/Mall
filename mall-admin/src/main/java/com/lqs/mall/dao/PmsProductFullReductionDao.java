package com.lqs.mall.dao;

import com.lqs.mall.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品管理-价格满减 自定义dao
 * Created by 刘千山 on 2023/7/3/003-16:16
 */
public interface PmsProductFullReductionDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductFullReduction> productFullReductionList);
}
