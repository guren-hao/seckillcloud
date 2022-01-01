package com.weiran.seckill.service;

import com.weiran.seckill.common.obj.Result;
import com.weiran.seckill.common.param.LoginParam;
import com.weiran.seckill.common.param.RegisterParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface UserService {

    /**
     * 登陆
     */
    Result doLogin(HttpServletResponse response, HttpSession session, LoginParam loginParam);

    /**
     * 注销
     */
    Result doLogout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 注册
     */
    Result doRegister(RegisterParam registerParam);
}