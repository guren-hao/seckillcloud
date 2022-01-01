package com.weiran.seckill.service;

import com.weiran.seckill.common.obj.Result;
import com.weiran.seckill.pojo.bo.GoodsBo;
import com.weiran.seckill.pojo.vo.GoodsBoListVo;
import com.weiran.seckill.pojo.vo.GoodsDetailVo;

import java.util.List;

public interface GoodsService {

    /**
     * 显示商品列表
     */
    Result<GoodsBoListVo> getGoodsList();

    /**
     * 显示秒杀商品细节
     */
    Result<GoodsDetailVo> getDetail(long goodsId);

    /**
     * 查询全部的商品
     */
    List<GoodsBo> selectAllGoods();

    /**
     * 根据商品id查询返回goodsBo
     */
    GoodsBo getGoodsBoByGoodsId(long goodsId);

}
