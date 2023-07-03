package com.lqs.mall.dao;

import com.lqs.mall.model.PmsProductVertifyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台审核记录操作  自定义dao
 * Created by 刘千山 on 2023/7/3/003-18:06
 */
public interface PmsProductVertifyRecordDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductVertifyRecord> list);
}
