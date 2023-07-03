package com.lqs.mall.service;

import com.lqs.mall.dto.PmsBrandParam;
import com.lqs.mall.model.PmsBrand;

import java.util.List;

/**
 * 后台品牌管理Service
 * Created by 刘千山 on 2023/7/3/003-10:17
 */
public interface PmsBrandService {
    /**
     * 获取全部品牌列表
     */
    List<PmsBrand> listAllBrand();


    /**
     * 创建品牌
     */
    int createBrand(PmsBrandParam pmsBrand);

    /**
     * 修改品牌
     */
    int updateBrand(Long id, PmsBrandParam pmsBrandParam);


    /**
     * 删除品牌
     */
    int deleteBrand(Long id);

    /**
     * 分页获取品牌列表
     */
    List<PmsBrand> listBrand(String keyword, Integer showStatus, Integer pageNum, Integer pageSize);

    /**
     * 根据id获取品牌
     */
    PmsBrand getBrand(Long id);

    /**
     * 批量删除品牌
     */
    int deleteBrand(List<Long> ids);

    /**
     * 批量更新显示状态
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 批量更新厂家状态
     */
    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}
