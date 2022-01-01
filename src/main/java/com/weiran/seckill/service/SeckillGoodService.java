package com.weiran.seckill.service;

public interface SeckillGoodService {

    /**
     * 库存减一
     */
    int reduceStock(long goodsId);
}
