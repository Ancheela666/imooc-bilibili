package com.imooc.bilibili.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.imooc.bilibili.domain.exception.ConditionException;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {

    private final static String ISSUER = "签发者";

    public static String generateToken(Long userId) throws Exception { //生成用户令牌
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance(); //过期时间
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, 30); //设置超时时间
        return JWT.create()
                .withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER) //设置签发者
                .withExpiresAt(calendar.getTime()) //设置过期时间
                .sign(algorithm); //设置加密算法
    }

    public static String generateRefreshToken(Long userId) throws Exception {
        Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
        Calendar calendar = Calendar.getInstance(); //过期时间
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7); //设置超时时间
        return JWT.create()
                .withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER) //设置签发者
                .withExpiresAt(calendar.getTime()) //设置过期时间
                .sign(algorithm); //设置加密算法
    }

    public static Long verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String userId = jwt.getKeyId();
            return Long.valueOf(userId);
        } catch (TokenExpiredException e){
            throw new ConditionException("555", "token过期！");
        } catch (Exception e) {
            throw new ConditionException("非法用户token！");
        }
    }

}
