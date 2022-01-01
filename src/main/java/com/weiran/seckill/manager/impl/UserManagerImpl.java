package com.weiran.seckill.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weiran.seckill.pojo.entity.User;
import com.weiran.seckill.manager.UserManager;
import com.weiran.seckill.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl extends ServiceImpl<UserMapper, User> implements UserManager {

}
