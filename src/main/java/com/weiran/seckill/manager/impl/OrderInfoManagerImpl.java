package com.weiran.seckill.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.seckill.mapper.OrderInfoMapper;
import com.weiran.seckill.pojo.entity.OrderInfo;
import com.weiran.seckill.manager.OrderInfoManager;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoManagerImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoManager {

}