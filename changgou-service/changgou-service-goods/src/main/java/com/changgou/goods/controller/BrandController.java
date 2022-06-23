package com.changgou.goods.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌表
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result<List<Brand>> findAll(){
        List<Brand> list = brandService.findAll();
        return new Result<>(true, StatusCode.OK, "查询成功", list);
    }

    @GetMapping("/{id}")
    public Result<Brand> findById(@PathVariable(value = "id") Integer id){
        Brand brand = brandService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", brand);
    }

    @PostMapping
    public Result add(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody Brand brand, @PathVariable(value = "id") Integer id){
        // 设置主键
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable Integer id){
        brandService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @PostMapping("/search")
    public Result<List<Brand>> findList(@RequestBody(required = false) Brand brand){
        List<Brand> list = brandService.findList(brand);
        return new Result<>(true, StatusCode.OK, "查询成功", list);
    }

    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Brand>> search(@PathVariable(value = "page") Integer page,
                                          @PathVariable(value = "size") Integer size){
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return new Result<>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false) Brand brand, @PathVariable(value = "page") Integer page,@PathVariable(value = "size") Integer size){
        //执行搜索
        PageInfo<Brand> pageInfo = brandService.findPage(brand, page, size);
        return new Result<>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 根据分类实现品牌列表查询
     * /brand/category/{id}  分类ID
     */
    @GetMapping(value = "/category/{id}")
    public Result<List<Brand>> findBrandByCategory(@PathVariable(value = "id")Integer categoryId){
        //调用Service查询品牌数据
        List<Brand> categoryList = brandService.findByCategory(categoryId);
        return new Result<List<Brand>>(true,StatusCode.OK,"查询成功！",categoryList);
    }
}
