package kr.flab.movieon.notification.infrastructure;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kr.flab.movieon.notification.domain.EmailNotification;
import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessDelegator;
import kr.flab.movieon.notification.domain.ExternalEventNotificationProcessor;
import kr.flab.movieon.notification.domain.NotificationSender;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
@EnableCaching
public class NotificationModuleConfiguration {

    @Bean
    @Profile(value = {"local", "prod"})
    public NotificationSender<EmailNotification> emailNotificationSender(
        JavaMailSender javaMailSender) {
        return new EmailNotificationSender(javaMailSender);
    }

    @Bean
    public TemplateEngine templateEngine() {
        var templateEngine = new SpringTemplateEngine();
        var templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

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
    public CacheManager caffeineCacheManager() {
        var cacheManager = new SimpleCacheManager();
        var caches = Arrays.stream(CachePolicy.values())
            .map(cache -> new CaffeineCache(cache.getCacheName(),
                Caffeine.newBuilder()
                    .recordStats()
                    .expireAfterWrite(cache.getMaxTimeToLive(), TimeUnit.MINUTES)
                    .maximumSize(cache.getMaxSize())
                    .build()
            ))
            .toList();
        cacheManager.setCaches(caches);
        return cacheManager;
    }

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

        public String getCacheName() {
            return cacheName;
        }

        public long getMaxSize() {
            return maxSize;
        }

        public int getMaxTimeToLive() {
            return maxTimeToLive;
        }
    }
}
