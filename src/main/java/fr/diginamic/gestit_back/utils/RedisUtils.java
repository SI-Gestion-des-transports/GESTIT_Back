package fr.diginamic.gestit_back.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Data
public class RedisUtils {
    private RedisTemplate<String,String> redisTemplate;
    public void createRedisCache(String username,String token){
        redisTemplate.opsForSet().add(username,token);
    }
    public boolean verifyRedisCache(String username,String token){
        return redisTemplate.opsForSet().isMember(username,token);
    }
    public void deleteRedisCache(String username,String token){
        if (verifyRedisCache(username,token))
        redisTemplate.opsForSet().remove(username,token);
    }
}
