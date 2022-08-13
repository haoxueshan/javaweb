package com.itheima.service;

import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandServlce {
    List<Brand> selectAll();

    void add(Brand brand);

    void deleteById(int[] ids);

    PageBean<Brand> selectByPage(int begin,int size);

    PageBean<Brand> selectByPageAndCondition(int begin,int size,Brand brand);
}
