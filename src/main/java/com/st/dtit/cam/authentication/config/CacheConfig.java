package com.st.dtit.cam.authentication.config;

import com.st.dtit.cam.authentication.model.Application;
import com.st.dtit.cam.authentication.model.AuthToken;
import com.st.dtit.cam.authentication.model.composite.AuthTokenId;
import com.st.dtit.cam.authentication.utility.cache.CacheEventLogger;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    CacheManager getCacheManager() {

            CachingProvider provider = Caching.getCachingProvider();
            CacheManager cacheManager = provider.getCacheManager();

            CacheConfigurationBuilder<Map, List> configurationListBuilder =
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                    Map.class, List.class,
                                    ResourcePoolsBuilder.heap(2000)
                                            .offheap(10, MemoryUnit.MB))
                                            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(30)));

            CacheConfigurationBuilder<String, Application> configurationObjectBuilder =
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                    String.class, Application.class,
                                    ResourcePoolsBuilder.heap(2000)
                                            .offheap(10, MemoryUnit.MB))
                                            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(30)));

            CacheConfigurationBuilder<AuthTokenId, AuthToken> configurationAuthTokenIdBuilder =
                    CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                    AuthTokenId.class, AuthToken.class,
                                    ResourcePoolsBuilder.heap(2000)
                                            .offheap(10, MemoryUnit.MB))
                                            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(30)));

            CacheEventListenerConfigurationBuilder asynchronousListener = CacheEventListenerConfigurationBuilder
                    .newEventListenerConfiguration(new CacheEventLogger()
                            , EventType.CREATED, EventType.EXPIRED).unordered().asynchronous();

            cacheManager.createCache("application",
                    Eh107Configuration.fromEhcacheCacheConfiguration(configurationObjectBuilder.withService(asynchronousListener)));
            cacheManager.createCache("user",
                    Eh107Configuration.fromEhcacheCacheConfiguration(configurationListBuilder.withService(asynchronousListener)));
            cacheManager.createCache("user_roles",
                    Eh107Configuration.fromEhcacheCacheConfiguration(configurationListBuilder.withService(asynchronousListener)));
            cacheManager.createCache("auth_token",
                    Eh107Configuration.fromEhcacheCacheConfiguration(configurationAuthTokenIdBuilder.withService(asynchronousListener)));

            return cacheManager;
    }
}
