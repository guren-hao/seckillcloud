package com.weiran.seckill.service;

import com.weiran.seckill.common.obj.Result;
import com.weiran.seckill.pojo.vo.OrderDetailVo;

public interface OrderService {

    /**
     * 查询订单信息
     */
    Result<OrderDetailVo> info(long orderId);

}
