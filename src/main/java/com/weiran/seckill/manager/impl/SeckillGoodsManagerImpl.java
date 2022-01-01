package com.weiran.seckill.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.seckill.pojo.entity.SeckillGoods;
import com.weiran.seckill.manager.SeckillGoodsManager;
import com.weiran.seckill.mapper.SeckillGoodsMapper;
import org.springframework.stereotype.Service;

@Service
public class SeckillGoodsManagerImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements SeckillGoodsManager {
}
