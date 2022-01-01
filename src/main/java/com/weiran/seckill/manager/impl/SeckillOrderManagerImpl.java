package com.weiran.seckill.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.seckill.pojo.entity.SeckillOrder;
import com.weiran.seckill.manager.SeckillOrderManager;
import com.weiran.seckill.mapper.SeckillOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class SeckillOrderManagerImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderManager {

}