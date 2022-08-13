package com.itheima.service.impl;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandServlce;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandServletImpl implements BrandServlce {

    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public List<Brand> selectAll() {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        List<Brand> brands = mapper.selectAll();

        sqlSession.close();
        return brands;
    }

    @Override
    public void add(Brand brand) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.add(brand);
        sqlSession.commit();

        sqlSession.close();
    }

    @Override
    public void deleteById(int[] ids) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        mapper.deleteById(ids);

        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public PageBean<Brand> selectByPage(int currentPage, int pageSize) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        int begin=(currentPage-1)*pageSize;
        int size=pageSize;
        List<Brand> brands = mapper.selectByPage(begin, size);

        int totalCount = mapper.selectTotalCount();

        PageBean<Brand> pageBean=new PageBean<>();
        pageBean.setRows(brands);
        pageBean.setTotalCount(totalCount);

        sqlSession.close();

        return pageBean;



    }

    @Override
    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        int begin=(currentPage-1)*pageSize;
        int size=pageSize;
        String brandName = brand.getBrandName();
        if (brandName !=null && brandName.length()>0){
            brand.setBrandName("%"+brandName+"%");
        }

        String companyName = brand.getCompanyName();
        if (companyName !=null && companyName.length()>0){
            brand.setCompanyName("%"+companyName+"%");
        }

        List<Brand> brands = mapper.selectByPageAndCondition(begin, size,brand);

        int totalCount = mapper.selectTotalCountbyCondition();

        PageBean<Brand> pageBean=new PageBean<>();
        pageBean.setRows(brands);
        pageBean.setTotalCount(totalCount);

        sqlSession.close();

        return pageBean;
    }

}
