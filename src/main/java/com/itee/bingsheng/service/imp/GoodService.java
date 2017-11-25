package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.entity.Goods;
import com.itee.bingsheng.mybatis.dao.GoodDao;

import com.itee.bingsheng.service.IGoodGroupService;
import com.itee.bingsheng.service.IGoodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GoodService implements IGoodService {

    @Resource
    GoodDao dao;

    @Resource
    private IGoodGroupService goodGroupService;


    @Override
    public int saveGood (Goods goods)throws Exception{
        return dao.saveGood(goods);
    }


    /**
     * 商品列表查询
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> queryGoodList(Map<String, Object> map)throws Exception{
        return dao.queryGoodList(map);
    }

    /**
     * 更新商品信息
     * @param goods
     * @return
     * @throws Exception
     */
    @Override
    public int updateGood(Goods goods)throws Exception{
        try {
            Map<String,Object> params = new HashMap();
            params.put("goods_id",goods.getGoodsId());
            Goods oldGood = getGoodsByGoodsMap(queryGoodInfo(params));
            if(oldGood.getCatId()!=goods.getCatId() || oldGood.getExtendCatId()!=goods.getExtendCatId()){
                // 清空商品属性
                params.put("state",0);
                dao.updateGoodAttr(params);
            }
            dao.updateGood(goods);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 更新商品明细信息
     * @return
     * @throws Exception
     */
    @Override
    public int updateGoodAttr(Map<String,Object> map)throws Exception{
        return dao.updateGoodAttr(map);
    }

    /**
     * 商品列表查询总数量
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public int queryGoodListCount(Map<String, Object> map)throws Exception{
        return dao.queryGoodListCount(map);
    }

    /**
     * 商品查询
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> queryGoodInfo(Map<String, Object> map)throws Exception{
        List<Map<String,Object>> goodsList = dao.queryGoodList(map);
        if(goodsList!=null && goodsList.size()>0){
            return goodsList.get(0);
        }
        return null;
    }


    /**
     * 商品map转实体
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Goods getGoodsByGoodsMap(Map<String, Object> map)throws Exception{
        Goods goods = new Goods();
        if(map.get("goods_id")!=null && StringUtils.isNotEmpty(map.get("goods_id").toString())){
            goods.setGoodsId(Integer.parseInt(map.get("goods_id").toString()));
        }
        if(map.get("cat_id")!=null && StringUtils.isNotEmpty(map.get("cat_id").toString())){
            goods.setCatId(Integer.parseInt(map.get("cat_id").toString()));
            goods.setCatName(goodGroupService.getGoodGroupById(goods.getCatId()).getName());
        }
        if(map.get("extend_cat_id")!=null && StringUtils.isNotEmpty(map.get("extend_cat_id").toString())){
            goods.setExtendCatId(Integer.parseInt(map.get("extend_cat_id").toString()));
            goods.setExtendCatName(goodGroupService.getGoodGroupById(goods.getExtendCatId()).getName());
        }
        if(map.get("goods_sn")!=null && StringUtils.isNotEmpty(map.get("goods_sn").toString())){
            goods.setGoodsSn(map.get("goods_sn").toString());
        }
        if(map.get("goods_name")!=null && StringUtils.isNotEmpty(map.get("goods_name").toString())){
            goods.setGoodsName(map.get("goods_name").toString());
        }
        if(map.get("click_count")!=null && StringUtils.isNotEmpty(map.get("click_count").toString())){
            goods.setClickCount(Integer.parseInt(map.get("click_count").toString()));
        }
        if(map.get("store_count")!=null && StringUtils.isNotEmpty(map.get("store_count").toString())){
            goods.setStoreCount(Integer.parseInt(map.get("store_count").toString()));
        }
        if(map.get("comment_count")!=null && StringUtils.isNotEmpty(map.get("comment_count").toString())){
            goods.setCommentCount(Integer.parseInt(map.get("comment_count").toString()));
        }
        if(map.get("weight")!=null && StringUtils.isNotEmpty(map.get("weight").toString())){
            goods.setWeight(Integer.parseInt(map.get("weight").toString()));
        }
        if(map.get("price")!=null && StringUtils.isNotEmpty(map.get("price").toString())){
            goods.setPrice(Double.parseDouble(map.get("price").toString()));
        }
        if(map.get("dealerprice")!=null && StringUtils.isNotEmpty(map.get("dealerprice").toString())){
            goods.setDealerprice(Double.parseDouble(map.get("dealerprice").toString()));
        }
        if(map.get("keywords")!=null && StringUtils.isNotEmpty(map.get("keywords").toString())){
            goods.setKeywords(map.get("keywords").toString());
        }
        if(map.get("goods_remark")!=null && StringUtils.isNotEmpty(map.get("goods_remark").toString())){
            goods.setGoodsRemark(map.get("goods_remark").toString());
        }
        if(map.get("goods_content")!=null && StringUtils.isNotEmpty(map.get("goods_content").toString())){
            goods.setGoodsContent(map.get("goods_content").toString());
        }
        if(map.get("original_img")!=null && StringUtils.isNotEmpty(map.get("original_img").toString())){
            goods.setOriginalImg(map.get("original_img").toString());
        }
        if(map.get("is_real")!=null && StringUtils.isNotEmpty(map.get("is_real").toString())){
            goods.setIsReal(Integer.parseInt(map.get("is_real").toString()));
        }
        if(map.get("is_on_sale")!=null && StringUtils.isNotEmpty(map.get("is_on_sale").toString())){
            goods.setIsOnSale(Integer.parseInt(map.get("is_on_sale").toString()));
        }
        if(map.get("is_free_shipping")!=null && StringUtils.isNotEmpty(map.get("is_free_shipping").toString())){
            goods.setIsFreeShipping(Integer.parseInt(map.get("is_free_shipping").toString()));
        }
        if(map.get("sort")!=null && StringUtils.isNotEmpty(map.get("sort").toString())){
            goods.setSort(Integer.parseInt(map.get("sort").toString()));
        }
        if(map.get("is_recommend")!=null && StringUtils.isNotEmpty(map.get("is_recommend").toString())){
            goods.setIsRecommend(Integer.parseInt(map.get("is_recommend").toString()));
        }
        if(map.get("is_new")!=null && StringUtils.isNotEmpty(map.get("is_new").toString())){
            goods.setIsNew(Integer.parseInt(map.get("is_new").toString()));
        }
        if(map.get("is_hot")!=null && StringUtils.isNotEmpty(map.get("is_hot").toString())){
            goods.setIsHot(Integer.parseInt(map.get("is_hot").toString()));
        }
        if(map.get("give_integral")!=null && StringUtils.isNotEmpty(map.get("give_integral").toString())){
            goods.setGiveIntegral(Integer.parseInt(map.get("give_integral").toString()));
        }
        if(map.get("suppliers_id")!=null && StringUtils.isNotEmpty(map.get("suppliers_id").toString())){
            goods.setSuppliersId(Integer.parseInt(map.get("suppliers_id").toString()));
        }
        if(map.get("create_by")!=null && StringUtils.isNotEmpty(map.get("create_by").toString())){
            goods.setCreateBy(Integer.parseInt(map.get("create_by").toString()));
        }
        if(map.get("update_by")!=null && StringUtils.isNotEmpty(map.get("update_by").toString())){
            goods.setUpdateBy(Integer.parseInt(map.get("update_by").toString()));
        }
        if(map.get("sales_sum")!=null && StringUtils.isNotEmpty(map.get("sales_sum").toString())){
            goods.setSalesSum(Integer.parseInt(map.get("sales_sum").toString()));
        }
        if(map.get("prom_type")!=null && StringUtils.isNotEmpty(map.get("prom_type").toString())){
            goods.setPromType(Integer.parseInt(map.get("prom_type").toString()));
        }
        if(map.get("commission")!=null && StringUtils.isNotEmpty(map.get("commission").toString())){
            goods.setCommission(Double.parseDouble(map.get("commission").toString()));
        }

        return goods;
    }

    /**
     * 获取商品的属性
     * @param goodsId
     * @return
     * @throws Exception
     */
    @Override
    public  List<Map<String,Object>> getGoodAttr(int goodsId)throws Exception{
        return dao.getGoodAttr(goodsId);
    }

    /**
     * 保存商品属性
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public int saveGoodAttr(Map<String,Object> map)throws Exception{
        return dao.saveGoodAttr(map);
    }

    /**
     * 变更属性时更改购物车的商品状态
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public int updateGoodCartState(Map<String,Object> map)throws Exception{
        return dao.updateGoodCartState(map);
    }
}