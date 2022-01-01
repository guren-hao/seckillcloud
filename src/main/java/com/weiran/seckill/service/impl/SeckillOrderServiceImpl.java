package com.weiran.seckill.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weiran.seckill.common.enums.RedisCacheTimeEnum;
import com.weiran.seckill.redis.key.SeckillKey;
import com.weiran.seckill.redis.manager.RedisService;
import com.weiran.seckill.pojo.entity.OrderInfo;
import com.weiran.seckill.pojo.entity.SeckillOrder;
import com.weiran.seckill.pojo.entity.User;
import com.weiran.seckill.manager.OrderInfoManager;
import com.weiran.seckill.manager.SeckillOrderManager;
import com.weiran.seckill.pojo.bo.GoodsBo;
import com.weiran.seckill.service.SeckillGoodService;
import com.weiran.seckill.service.SeckillOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillOrderServiceImpl implements SeckillOrderService {

    final OrderInfoManager orderInfoManager;
    final SeckillOrderManager seckillOrderManager;
    final SeckillGoodService seckillGoodService;
    final RedisService redisService;

    // 根据用户信息和goodsBo对象插入订单表和秒杀订单表
    @Transactional
    @Override
    public OrderInfo insertByUserAndGoodsBo(User user, GoodsBo goods) {
        // 秒杀商品库存减一
        int success = seckillGoodService.reduceStock(goods.getId());
        if (success == 1) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setCreateDate(LocalDateTime.now());
            orderInfo.setAddrId(0L);
            orderInfo.setGoodsCount(1);
            orderInfo.setGoodsId(goods.getId());
            orderInfo.setGoodsName(goods.getGoodsName());
            orderInfo.setGoodsPrice(goods.getSeckillPrice());
            orderInfo.setOrderChannel(1);
            orderInfo.setStatus(0);
            orderInfo.setUserId(user.getId());
            // 添加信息进订单表
            orderInfoManager.save(orderInfo);
            log.info("user" + user.getId() + "订单信息已经加入订单表");
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setGoodsId(goods.getId());
            seckillOrder.setOrderId(orderInfo.getId());
            seckillOrder.setUserId(user.getId());
            // 添加进秒杀表
            seckillOrderManager.save(seckillOrder);
            return orderInfo;
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    // 通过OrderId查找SeckillOrder对象
    @Override
    public SeckillOrder selectByOrderId(Long orderId) {
        return seckillOrderManager.getOne(Wrappers.<SeckillOrder>lambdaQuery().eq(SeckillOrder::getOrderId, orderId));
    }

    // 通过UserId和goodsId查找SeckillOrder对象
    @Override
    public SeckillOrder selectByUserIdAndGoodsId(long userId, long goodsId) {
        return seckillOrderManager.getOne(Wrappers.<SeckillOrder>lambdaQuery()
                .eq(SeckillOrder::getUserId, userId)
                .eq(SeckillOrder::getGoodsId, goodsId));
    }

    // 秒杀商品结束标记
    private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, ""+ goodsId, true , RedisCacheTimeEnum.GOODS_ID_EXTIME.getValue());
    }

}
