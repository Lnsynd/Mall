package com.lqs.mall.service;

import com.lqs.mall.dto.PmsProductParam;
import com.lqs.mall.dto.PmsProductQueryParam;
import com.lqs.mall.dto.PmsProductResult;
import com.lqs.mall.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台-商品管理Service
 * Created by 刘千山 on 2023/7/3/003-15:53
 */
public interface PmsProductService {
    /**
     * 新建商品
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int create(PmsProductParam productParam);

    /**
     * 根据id查询商品更新信息
     */
    PmsProductResult getUpdateInfo(Long id);

    /**
     * 更新商品信息
     */
    @Transactional
    int update(Long id, PmsProductParam productParam);

    /**
     * 分页查询商品信息
     */
    List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);

    /**
     * 根据关键词模糊查询
     */
    List<PmsProduct> list(String keyword);

    /**
     * 批量修改审核状态
     * @param ids 商品id集合
     * @param verifyStatus 审核状态
     * @param detail 详情
     */
    @Transactional
    int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

    /**
     * 批量修改上架状态
     * @param ids 商品id集合
     * @param publishStatus 上架状态
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * 批量修改推荐状态
     * @param ids  商品id集合
     * @param recommendStatus 推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 批量设置为新品
     * @param ids 商品id集合
     * @param newStatus 是否为新品状态
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);

    /**
     * 批量修改删除状态
     * @param ids 商品id集合
     * @param deleteStatus 删除状态
     */
    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);
}
