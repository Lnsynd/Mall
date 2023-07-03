package com.lqs.mall.dao;

import com.lqs.mall.model.CmsSubjectProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品和专题关系自定义Dao
 * Created by 刘千山 on 2023/7/3/003-16:31
 */
public interface CmsSubjectProductRelationDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<CmsSubjectProductRelation> subjectProductRelationList);
}
