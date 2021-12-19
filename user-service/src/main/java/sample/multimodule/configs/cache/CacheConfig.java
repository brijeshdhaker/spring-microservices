package sample.multimodule.configs.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger log = LoggerFactory.getLogger(CacheConfig.class);

    /*

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new SimpleCacheManager();
        //Cache userCache = new ConcurrentMapCache("userCache");
        Cache roleCache = new ConcurrentMapCache("roleCache");
        cacheManager.setCaches(Arrays.asList(roleCache));
        return cacheManager;
    }
    */

    @Bean
    public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {

        return new CacheManagerCustomizer<ConcurrentMapCacheManager>() {
            @Override
            public void customize(ConcurrentMapCacheManager cacheManager) {
                log.info("Programmatically Customization of ConcurrentMapCacheManager ...");
                cacheManager.setCacheNames(Arrays.asList("userCache", "roleCache"));
            }
        };
    }

}
