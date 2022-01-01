package com.weiran.seckill.service.impl;

import com.weiran.seckill.common.obj.CodeMsg;
import com.weiran.seckill.common.obj.Result;
import com.weiran.seckill.redis.manager.RedisService;
import com.weiran.seckill.pojo.entity.OrderInfo;
import com.weiran.seckill.pojo.entity.SeckillOrder;
import com.weiran.seckill.manager.OrderInfoManager;
import com.weiran.seckill.pojo.bo.GoodsBo;
import com.weiran.seckill.pojo.vo.OrderDetailVo;
import com.weiran.seckill.service.GoodsService;
import com.weiran.seckill.service.OrderService;
import com.weiran.seckill.service.SeckillOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final OrderInfoManager orderInfoManager;
    final RedisService redisService;
    final SeckillOrderService seckillOrderService;
    final GoodsService goodsService;

    // 查询订单信息
    @Override
    public Result<OrderDetailVo> info(long orderId) {
        SeckillOrder seckillOrder = seckillOrderService.selectByOrderId(orderId);
        if (seckillOrder == null) {
            return null;
        }
        OrderInfo orderInfo = orderInfoManager.getById(orderId);
        if (orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = orderInfo.getGoodsId();
        GoodsBo goodsBo = goodsService.getGoodsBoByGoodsId(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrder(orderInfo);
        orderDetailVo.setGoodsBo(goodsBo);
        return Result.success(orderDetailVo);
    }
}
