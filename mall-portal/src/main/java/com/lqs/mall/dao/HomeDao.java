package com.lqs.mall.dao;

import com.lqs.mall.model.PmsBrand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 首页内容管理自定义Dao
 * Created by 刘千山 on 2023/7/4/004-09:05
 */
public interface HomeDao {
    /**
     * 获取推荐品牌
     */
    List<PmsBrand> getRecommendBrandList(@Param("offset")int offset,@Param("limit") Integer pageSize);
}
