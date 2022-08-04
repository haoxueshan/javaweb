package com.demo.mapper;

import com.demo.pojo.Brand;
import com.demo.pojo.User;

import java.util.List;

public interface BrandMapper {
    List<Brand> selectAll();

    Brand selectById(int id);
}
