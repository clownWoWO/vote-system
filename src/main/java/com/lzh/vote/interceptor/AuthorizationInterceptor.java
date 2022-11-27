package com.lzh.vote.interceptor;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.lzh.vote.annotation.Authorization;
import com.lzh.vote.constant.VoteConstant;
import com.lzh.vote.entity.DB.UserDB;
import com.lzh.vote.entity.TokenModel;
import com.lzh.vote.mapper.UserMapper;
import com.lzh.vote.service.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，判断此次请求是否有权限
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;
    @Autowired
    private UserMapper userMapper;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Authorization annotation = method.getAnnotation(Authorization.class);

        //从header中得到token
        String authorization = request.getHeader(VoteConstant.TOKEN);
        //验证token
        TokenModel model = manager.getToken(authorization);

        boolean checkToken = manager.checkToken(model);
        if (ObjectUtils.isEmpty(annotation)){
            return true;
        }
        if (checkToken && !annotation.isAdmin()) {
            //如果token验证成功并且不需要是管理员，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(VoteConstant.CURRENT_USER_ID, model.getUserId());
            return true;
        }
        //判断是否有管理员权限
        if (checkToken && annotation.isAdmin()) {
            UserDB userDB = userMapper.selectById(Integer.valueOf(model.getUserId()));
            //没有管理员权限返回401
            if (!userDB.getIsAdmin().equals(VoteConstant.IS_ADMIN)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }else{
                request.setAttribute(VoteConstant.CURRENT_USER_ID, model.getUserId());
                return true;
            }
        }
        //如果验证token失败，并且方法注明了Authorization，返回401错误
        if (annotation != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
