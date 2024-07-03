package io.github.chenyilei2016.ac.core.cluster;

import io.github.chenyilei2016.ac.core.cluster.redis.RedisMasterElector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyilei
 * @since 2024/07/03 20:14
 */

@Configuration
public class MasterElectorAutoConfiguration {

    @Bean
    @ConditionalOnClass(name = {"org.redisson.api.RedissonClient"})
    @ConditionalOnMissingBean(value = MasterElector.class, search = SearchStrategy.CURRENT)
    public RedisMasterElector masterElector() {
        return new RedisMasterElector();
    }


}
