package com.ricks.rntcr.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.ricks.rntcr.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Car.class.getName(), jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Car.class.getName() + ".tags", jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Contact.class.getName(), jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.RentHistory.class.getName(), jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Album.class.getName(), jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Photo.class.getName(), jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Photo.class.getName() + ".tags", jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Tag.class.getName() + ".photos", jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.RentHistory.class.getName() + ".cars", jcacheConfiguration);
            cm.createCache(com.ricks.rntcr.domain.Price.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
