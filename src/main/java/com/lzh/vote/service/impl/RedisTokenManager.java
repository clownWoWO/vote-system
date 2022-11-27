package com.lzh.vote.service.impl;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.lzh.vote.constant.VoteConstant;
import com.lzh.vote.entity.TokenModel;
import com.lzh.vote.service.TokenManager;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 *
 * @date 2015/7/31.
 */
@Component
@AllArgsConstructor
public class RedisTokenManager implements TokenManager {

    private StringRedisTemplate redis;

    public TokenModel createToken(String userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        //存储到redis并设置过期时间
        redis.boundValueOps(VoteConstant.USERID_KEY + userId).set(token, VoteConstant.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    public TokenModel getToken(String authentication) {
        if (ObjectUtils.isEmpty(authentication)) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        String userId = param[0];
        String token = param[1];
        return new TokenModel(userId, token);
    }

    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = redis.boundValueOps(VoteConstant.USERID_KEY + model.getUserId()).get();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(VoteConstant.USERID_KEY + model.getUserId()).expire(VoteConstant.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    public void deleteToken(String userId) {
        redis.delete(VoteConstant.USERID_KEY + userId);
    }
}
