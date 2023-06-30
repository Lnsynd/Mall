package com.lqs.mall.common.api;

import org.springframework.data.domain.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * 通用分页封装类
 * Created by 刘千山 on 2023/6/29/029-19:45
 */
public class CommonPage<T> {

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 数据总条数
     */
    private Long total;

    /**
     * 分页数据
     */
    private List<T> list;


    /**
     * 将PageHelper分页后的数据转为 分页信息
     *
     * @param list 分页数据
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * 将Spring Data分页后的list 转换为分页信息
     */
    public static <T> CommonPage<T> restPage(Page<T> pageInfo) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
