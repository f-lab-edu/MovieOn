package kr.flab.movieon.supports;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import java.io.IOException;

@Profile({"test", "local"})
@Configuration
public class EmbeddedRedisConfiguration {

    private static final Logger log = LoggerFactory.getLogger(EmbeddedRedisConfiguration.class);

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void start() throws IOException {
        if (ProcessUtil.isRunningPort(redisPort)) {
            log.warn("Local embedded redis port is already used. Redis server did not start (port: {})", redisPort);
            return;
        }

        try {
            redisServer = RedisServer.builder()
                    .port(redisPort)
                    .build();
            redisServer.start();
            log.info("Embedded Redis Server enabled http://localhost:{}", redisPort);
        } catch (Exception ex) {
            log.error("Failed to start Embedded Redis Server http://localhost:{}", redisPort, ex);
        }

    }

    @PreDestroy
    public void stop() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
