package com.changgou.goods.service.impl;

import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Brand> findList(Brand brand) {
        // 封装查询条件
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        return brands;
    }

    /** 封装查询条件 */
    private Example createExample(Brand brand) {
        Example example = new Example(Brand.class);
        if (brand != null) {
            Example.Criteria criteria = example.createCriteria();
            if (!StringUtils.isEmpty(brand.getName())){
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            if (!StringUtils.isEmpty(brand.getLetter())){
                criteria.andEqualTo("letter",brand.getLetter());
            }
        }
        return example;
    }

    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        // 设置分页条件
        PageHelper.startPage(page, size);
        List<Brand> brands = brandMapper.selectAll();
        return new PageInfo<>(brands);
    }

    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        // 设置分页条件
        PageHelper.startPage(page, size);
        // 设置查询条件
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        return new PageInfo<>(brands);
    }

    /***
     * 根据分类ID查询品牌集合
     * @param categoryid:分类ID
     * @return
     */
    @Override
    public List<Brand> findByCategory(Integer categoryid) {
        //1.查询当前分类所对应的所有品牌信息
        return brandMapper.findByCategory(categoryid);
    }
}
