package com.lzh.vote.resolvers;


import com.lzh.vote.annotation.CurrentUser;
import com.lzh.vote.constant.VoteConstant;
import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.DB.UserDB;
import com.lzh.vote.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是CurrentUser注解则支持
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出鉴权时存入的登录用户Id
        Integer currentUserId = Integer.valueOf(webRequest.getAttribute(VoteConstant.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST).toString());
        if (currentUserId != null) {
            //从数据库中查询并返回
            UserDB userDB = userMapper.selectById(currentUserId);
            CurrentUserReq currentUserReq = new CurrentUserReq();
            BeanUtils.copyProperties(userDB,currentUserReq);
            return currentUserReq;
        }
        throw new MissingServletRequestPartException(VoteConstant.CURRENT_USER_ID);
    }
}
