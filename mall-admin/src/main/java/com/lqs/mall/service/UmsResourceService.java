package com.lqs.mall.service;

import com.lqs.mall.model.UmsResource;

import java.util.List;

/**
 * 后台资源管理
 * Created by 刘千山 on 2023/7/2/002-15:58
 */
public interface UmsResourceService {
    /**
     * 后台 添加资源
     */
    int create(UmsResource umsResource);


    /**
     * 修改资源信息
     */
    int update(Long id, UmsResource umsResource);

    /**
     * 查询全部资源
     */
    List<UmsResource> listAll();


    /**
     * 根据id获取单个资源
     */
    UmsResource getItem(Long id);

    /**
     * 根据id删除某个资源
     */
    int delete(Long id);

    /**
     * 分页查询资源列表
     *
     * @param categoryId  所属分类的id
     * @param nameKeyword 名称
     * @param urlKeyword  请求地址
     * @param pageSize    页面大小
     * @param pageNum     当前页面 index
     */
    List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
