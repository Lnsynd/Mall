package com.lqs.mall.dao;

import com.lqs.mall.model.PmsMemberPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员价格管理 自定义Dao
 * Created by 刘千山 on 2023/7/3/003-16:01
 */
public interface PmsMemberPriceDao  {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsMemberPrice> memberPriceList);
}
