package com.study.shortJ.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
@Component
public class RedisInitializer implements CommandLineRunner {

    @Value("${spring.data.redis.flush}")
    private Boolean flush;

    private final JedisPool jedisPool;

    public RedisInitializer(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void run (String... args) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (flush) {
                jedis.flushDB();
                log.info("Redis database flushed successfully");
            };
        }
    }
}
