package kr.flab.movieon.notification.infrastructure;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessDelegator;
import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessor;
import kr.flab.movieon.notification.domain.NotificationSender;
import kr.flab.movieon.notification.domain.NotificationSenderDelegator;
import lombok.Getter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class NotificationModuleConfiguration {

    @Bean
    public ExternalEventNotificationProcessDelegator externalEventNotificationProcessorDelegator(
        List<ExternalEventNotificationProcessor> processors) {
        return new ExternalEventNotificationProcessDelegator(processors);
    }

    @Bean
    public NotificationSenderDelegator notificationSenderDelegator(
        List<NotificationSender> senders) {
        return new NotificationSenderDelegator(senders);
    }

    @Bean
    public CacheManager cacheManager() {
        var cacheManager = new SimpleCacheManager();
        var caches = Arrays.stream(CachePolicy.values())
            .map(cache -> new CaffeineCache(cache.getCacheName(),
                    Caffeine.newBuilder()
                        .recordStats()
                        .expireAfterWrite(cache.getMaxTimeToLive(), TimeUnit.MINUTES)
                        .maximumSize(cache.getMaxSize())
                        .build()
                ))
                .collect(Collectors.toList());
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Getter
    enum CachePolicy {
        TEMPLATE("template", 10000, 24 * 60 * 60);

        private final String cacheName;
        private final long maxSize;
        private final int maxTimeToLive;

        CachePolicy(String cacheName, long maxSize, int maxTimeToLive) {
            this.cacheName = cacheName;
            this.maxSize = maxSize;
            this.maxTimeToLive = maxTimeToLive;
        }
    }
}
