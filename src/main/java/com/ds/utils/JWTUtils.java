package com.ds.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtils {

    //秘钥 盐
    private static final String SECRET = "20220914@123!#";

    /**
     * 生成token header.payload.sing
     * @param map
     * @param expired
     * @return
     */
    public static String getToken(Map<String,String> map, Integer expired){
        Calendar instance = Calendar.getInstance();
        //设置过期时间单位:秒
        instance.add(Calendar.SECOND,expired);
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        //制定令牌过期时间
        String token = builder.withExpiresAt(instance.getTime())
                //sign
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 验证token 合法性 || 获取token信息方法
     * @param token
     */
    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getClaim(String token, String name){
        String claim = null;
        try {
            claim =  JWT.decode(token).getClaim(name).asString();
        }catch (Exception e) {
            return null;
        }
        return claim;
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("userId","1");
        map.put("loginName","admin");
        String token = getToken(map,60);
        log.info(token);
        boolean verify = verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpbk5hbWUiOiJhZG1pbiIsImV4cCI6MTY2MzEyMzQxMCwidXNlcklkIjoiMSJ9.vRhEhGGNPA3EJlBXpsGfW-GjEZp4txwURqhfAsRkRDU");
        if(verify){
            String userId = getClaim(token,"userId");
            String loginName = getClaim(token,"loginName");
            log.info("userId={}, loginName={}", userId, loginName);
        }
    }
}

