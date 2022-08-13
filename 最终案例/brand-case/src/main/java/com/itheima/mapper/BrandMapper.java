package com.itheima.mapper;

import com.itheima.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper {

    @Select("select * from tb_brand")
    @ResultMap("brandResultMap")
    List<Brand> selectAll();


    //添加
    @Insert("insert into tb_brand values (null,#{brandName},#{companyName},#{ordered},#{description},#{status})")
    void add(Brand brand);

    //批量删除
    void deleteById(@Param("ids") int[] ids);

    @ResultMap("brandResultMap")
    @Select("select * from tb_brand limit #{begin},#{size}")
    List<Brand> selectByPage(@Param("begin") int begin,@Param("size") int size);

    @Select("select COUNT(*) from tb_brand")
    int selectTotalCount();



    List<Brand> selectByPageAndCondition(@Param("begin") int begin,@Param("size") int size,@Param("brand") Brand brand);


    int selectTotalCountbyCondition();



}
