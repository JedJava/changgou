package com.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.entity.IdWorker;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.SkuMapper;
import com.changgou.goods.dao.SpuMapper;
import com.changgou.goods.pojo.Goods;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import com.changgou.goods.service.SpuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/****
 * @Author:传智播客
 * @Description:Spu业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired(required = false)
    private SpuMapper spuMapper;

    @Autowired(required = false)
    private SkuMapper skuMapper;

    @Autowired(required = false)
    private CategoryMapper categoryMapper;

    @Autowired(required = false)
    private BrandMapper brandMapper;

    @Autowired
    private IdWorker idWorker;


    /**
     * Spu条件+分页查询
     *
     * @param spu  查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(spu);
        //执行搜索
        return new PageInfo<Spu>(spuMapper.selectByExample(example));
    }

    /**
     * Spu分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spu> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Spu>(spuMapper.selectAll());
    }

    /**
     * Spu条件查询
     *
     * @param spu
     * @return
     */
    @Override
    public List<Spu> findList(Spu spu) {
        //构建查询条件
        Example example = createExample(spu);
        //根据构建的条件查询数据
        return spuMapper.selectByExample(example);
    }


    /**
     * Spu构建查询对象
     *
     * @param spu
     * @return
     */
    public Example createExample(Spu spu) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (spu != null) {
            // 主键
            if (!StringUtils.isEmpty(spu.getId())) {
                criteria.andEqualTo("id", spu.getId());
            }
            // 货号
            if (!StringUtils.isEmpty(spu.getSn())) {
                criteria.andEqualTo("sn", spu.getSn());
            }
            // SPU名
            if (!StringUtils.isEmpty(spu.getName())) {
                criteria.andLike("name", "%" + spu.getName() + "%");
            }
            // 副标题
            if (!StringUtils.isEmpty(spu.getCaption())) {
                criteria.andEqualTo("caption", spu.getCaption());
            }
            // 品牌ID
            if (!StringUtils.isEmpty(spu.getBrandId())) {
                criteria.andEqualTo("brandId", spu.getBrandId());
            }
            // 一级分类
            if (!StringUtils.isEmpty(spu.getCategory1Id())) {
                criteria.andEqualTo("category1Id", spu.getCategory1Id());
            }
            // 二级分类
            if (!StringUtils.isEmpty(spu.getCategory2Id())) {
                criteria.andEqualTo("category2Id", spu.getCategory2Id());
            }
            // 三级分类
            if (!StringUtils.isEmpty(spu.getCategory3Id())) {
                criteria.andEqualTo("category3Id", spu.getCategory3Id());
            }
            // 模板ID
            if (!StringUtils.isEmpty(spu.getTemplateId())) {
                criteria.andEqualTo("templateId", spu.getTemplateId());
            }
            // 运费模板id
            if (!StringUtils.isEmpty(spu.getFreightId())) {
                criteria.andEqualTo("freightId", spu.getFreightId());
            }
            // 图片
            if (!StringUtils.isEmpty(spu.getImage())) {
                criteria.andEqualTo("image", spu.getImage());
            }
            // 图片列表
            if (!StringUtils.isEmpty(spu.getImages())) {
                criteria.andEqualTo("images", spu.getImages());
            }
            // 售后服务
            if (!StringUtils.isEmpty(spu.getSaleService())) {
                criteria.andEqualTo("saleService", spu.getSaleService());
            }
            // 介绍
            if (!StringUtils.isEmpty(spu.getIntroduction())) {
                criteria.andEqualTo("introduction", spu.getIntroduction());
            }
            // 规格列表
            if (!StringUtils.isEmpty(spu.getSpecItems())) {
                criteria.andEqualTo("specItems", spu.getSpecItems());
            }
            // 参数列表
            if (!StringUtils.isEmpty(spu.getParaItems())) {
                criteria.andEqualTo("paraItems", spu.getParaItems());
            }
            // 销量
            if (!StringUtils.isEmpty(spu.getSaleNum())) {
                criteria.andEqualTo("saleNum", spu.getSaleNum());
            }
            // 评论数
            if (!StringUtils.isEmpty(spu.getCommentNum())) {
                criteria.andEqualTo("commentNum", spu.getCommentNum());
            }
            // 是否上架
            if (!StringUtils.isEmpty(spu.getIsMarketable())) {
                criteria.andEqualTo("isMarketable", spu.getIsMarketable());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(spu.getIsEnableSpec())) {
                criteria.andEqualTo("isEnableSpec", spu.getIsEnableSpec());
            }
            // 是否删除
            if (!StringUtils.isEmpty(spu.getIsDelete())) {
                criteria.andEqualTo("isDelete", spu.getIsDelete());
            }
            // 审核状态
            if (!StringUtils.isEmpty(spu.getStatus())) {
                criteria.andEqualTo("status", spu.getStatus());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id){
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //检查是否被逻辑删除  ,必须先逻辑删除后才能物理删除
        if(!spu.getIsDelete().equals("1")){
            throw new RuntimeException("此商品不能删除！");
        }
        spuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Spu
     *
     * @param spu
     */
    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 增加Spu
     *
     * @param spu
     */
    @Override
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }

    /**
     * 根据ID查询Spu
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Spu全部数据
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }


    @Override
    public void saveOrUpdate(Goods goods) {
        // 保存商品信息
        Spu spu = goods.getSpu();
        if (spu.getId() == null) {   // 保存操作
            long spuId = idWorker.nextId();
            spu.setId(spuId);   // 主键
            spu.setIsMarketable("0");   // 未上架
            spu.setIsDelete("0");       // 未删除
            spu.setStatus("0");         // 未审核
            spuMapper.insertSelective(spu);
        } else {  // 更新操作
            spu.setStatus("0");         // 未审核
            spuMapper.updateByPrimaryKeySelective(spu);

            // 删除原有的库存列表数据
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }


        // 保存商品对应的库存信息
        List<Sku> skuList = goods.getSkuList();
        if (skuList != null && skuList.size() > 0) {
            for (Sku sku : skuList) {
                long id = idWorker.nextId();
                sku.setId(id);      // 主键
                // 商品库存的名称 = spu名称 + spu副标题  + 规格
                String name = spu.getName() + " " + spu.getCaption();
                // {"手机屏幕尺寸":"5寸","网络":"移动4G","颜色":"红","机身内存":"16G","存储":"64G","像素":"800万像素"}
                String spec = sku.getSpec();
                Map<String, String> specMap = JSON.parseObject(spec, Map.class);
                if (specMap != null) {
                    Set<Map.Entry<String, String>> entries = specMap.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        String value = entry.getValue();
                        name += " " + value;
                    }
                }
                sku.setName(name);
                sku.setCreateTime(new Date());  // 录入时间
                sku.setUpdateTime(new Date());  // 更新时间
                sku.setSpuId(spu.getId());            // 商品id
                sku.setCategoryId(spu.getCategory3Id());    // 分类id
                String categoryName = categoryMapper.selectByPrimaryKey(spu.getCategory3Id()).getName();
                sku.setCategoryName(categoryName);  // 分类名称
                sku.setBrandName(brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());   // 品牌名称
                sku.setStatus("1"); // 库存状态
                skuMapper.insertSelective(sku);
            }
        }
    }

    /***
     * 根据SpuID查询goods信息
     * @param spuId
     * @return
     */
    @Override
    public Goods findGoodsById(Long spuId) {
        //查询Spu
        Spu spu = spuMapper.selectByPrimaryKey(spuId);

        //查询List<Sku>
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        //封装Goods
        Goods goods = new Goods();
        goods.setSkuList(skus);
        goods.setSpu(spu);
        return goods;
    }

    /**
     * @author 栗子
     * @Description 商品审核
     * @Date 17:29 2019/10/22
     * @param id
     * @param status
     * @return void
     **/
    @Override
    public void audit(Long id, String status) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if ("1".equals(spu.getIsDelete())){
            throw new RuntimeException("删除后的商品不能审核");
        }

        spu.setStatus(status);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * @author 栗子
     * @Description 上架或下架
     * @Date 0:40 2019/8/13
     * @param id
     * @param isMarketable
     * @return void
     **/
    @Override
    public void isShow(Long id, String isMarketable) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if ("1".equals(isMarketable)){  // 上架操作
            if ("1".equals(spu.getIsDelete()) || !"1".equals(spu.getIsMarketable())){
                // 删除的 or 未审核的
                throw new RuntimeException("该商品不能上架");
            }
        }else{  // 下架操作
            if("1".equals(spu.getIsDelete())){
                throw new RuntimeException("已删除的商品无法下架");
            }
        }
        spu.setIsMarketable(isMarketable);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /***
     * 批量上架
     * @param ids:需要上架的商品ID集合
     * @return
     */
    @Override
    public int putMany(Long[] ids) {
        Spu spu=new Spu();
        spu.setIsMarketable("1");//上架
        //批量修改
        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));//id
        //下架
        criteria.andEqualTo("isMarketable","0");
        //审核通过的
        criteria.andEqualTo("status","1");
        //非删除的
        criteria.andEqualTo("isDelete","0");
        return spuMapper.updateByExampleSelective(spu, example);
    }

    /***
     * 逻辑删除
     * @param spuId
     */
    @Override
    @Transactional
    public void logicDelete(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //检查是否下架的商品
        if(!spu.getIsMarketable().equals("0")){
            throw new RuntimeException("必须先下架再删除！");
        }
        //删除
        spu.setIsDelete("1");
        //未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 恢复数据
     * @param spuId
     */
    @Override
    public void restore(Long spuId) {
        Spu spu = spuMapper.selectByPrimaryKey(spuId);
        //检查是否删除的商品
        if(!spu.getIsDelete().equals("1")){
            throw new RuntimeException("此商品未删除！");
        }
        //未删除
        spu.setIsDelete("0");
        //未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }
}
