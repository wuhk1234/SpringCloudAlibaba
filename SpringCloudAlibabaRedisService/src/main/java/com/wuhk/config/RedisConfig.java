package com.wuhk.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.redis.RedisReactiveHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.*;

/**
 * @className: RedisConfig
 * @description: redis连接池
 * @author: wuhk
 * @date: 2023/6/9 10:27
 * @version: 1.0
 * @company 航天信息
 **/

@Configuration
@ConditionalOnExpression(("'${spring.redis.host:}' != ''"))
@EnableCaching
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password:}")
    private String password;
    @Value("${spring.redis.database:0}")
    private int database;
    @Value("${spring.redis.timeout:5000}")
    private int timeout;
    @Value("${spring.redis.jedis.pool.max-active:20}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.max-wait:5000}")
    private long maxWaitMillis;
    @Value("${spring.redis.jedis.pool.max-idle:20}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle:5}")
    private int minIdle;
    @Value("${spring.redis.jedis.pool.time-between-eviction-runs:100000}")
    private long timeBetweenEvictionRuns;
    @Value("${spring.redis.lettuce.pool.max-wait:200}")
    private long maxWait;
    @Value("${spring.redis.lettuce.shutdown-timeout:10}")
    private long shutDownTimeout;
    /**
     * 连接池信息配置:
     *
     * @return JedisPoolConfig
     */
    /*@Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数
        jedisPoolConfig.setMaxTotal(maxActive);
        // 最大空闲连接数
        jedisPoolConfig.setMaxIdle(maxIdle);
        // 最小空闲连接数
        jedisPoolConfig.setMinIdle(minIdle);
        //
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRuns);
        // 其他属性可以自行添加
        return jedisPoolConfig;
    }

    *//**
     * 客户端连接:使用自定义的连接池
     *
     * @return JedisConnectionFactory
     *//*
    @Bean
    //必须指定一个连接池主键,默认用Jedis客户端连接池
    @Primary
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        JedisClientConfiguration build = JedisClientConfiguration.builder().usePooling().
                poolConfig(jedisPoolConfig).and().readTimeout(Duration.ofMillis(timeout)).build();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        if(StringUtils.isNotBlank(password)){
            redisStandaloneConfiguration.setPassword(password);
        }
        redisStandaloneConfiguration.setDatabase(database);
        return new JedisConnectionFactory(redisStandaloneConfiguration, build);
    }


    *//**
     * 缓存管理器
     *
     * @return RedisCacheManager
     *//*
    @Bean
    //必须指定一个会话管理器主键,默认用Jedis客户端缓存管理器
    @Primary
    public RedisCacheManager cacheManager() {
        return RedisCacheManager.create(jedisConnectionFactory(jedisPoolConfig()));
    }
*/

    /***********************************Lettuce客户端连接池*****************************/
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(100);
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeout))
                .shutdownTimeout(Duration.ofMillis(shutDownTimeout))
                .poolConfig(genericObjectPoolConfig)
                .build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
//        factory.setShareNativeConnection(true);
        factory.setValidateConnection(true);
        return factory;
    }
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean("redisCacheManager")
    //@Primary //必须指定一个会话管理器主键，否则会报：No CacheResolver specified, and no unique bean of type CacheManager found. Mark one as primary or declare a specific CacheManager to use.
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        //解决查询缓存转换异常的问题
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        // 配置1 ,
        RedisCacheConfiguration config1 = RedisCacheConfiguration.defaultCacheConfig()
                //缓存失效时间
                .entryTtl(Duration.ofSeconds(30))
                //key序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                //value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                //不允许缓存null值
                .disableCachingNullValues();
        //配置2 ,
        RedisCacheConfiguration config2 = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1000))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();

        //设置一个初始化的缓存空间set集合
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("my-redis-cache1");
        cacheNames.add("my-redis-cache2");

        //对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>(3);
        configurationMap.put("my-redis-cache1", config1);
        configurationMap.put("my-redis-cache2", config2);

        return RedisCacheManager.builder(lettuceConnectionFactory)
                //默认缓存配置
                .cacheDefaults(config1)
                //初始化缓存空间
                .initialCacheNames(cacheNames)
                //初始化缓存配置
                .withInitialCacheConfigurations(configurationMap).build();
    }



    ////////////////////////////////////////////////////////////
    // config one model
    ////////////////////////////////////////////////////////////
    @Bean
    public ReactiveRedisTemplate<String, Object> productModelReactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        valueSerializer.setObjectMapper(objectMapper);
        RedisSerializationContext<String, Object> context = RedisSerializationContext.<String, Object>newSerializationContext()
                .key(stringSerializer)
                .value(valueSerializer)
                .hashKey(stringSerializer)
                .hashValue(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

    ////////////////////////////////////////////////////////////
    // config model list
    ////////////////////////////////////////////////////////////

    @Bean
    public ReactiveRedisTemplate<String, Object> getProductWhitelistModelReactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer(List.class);
        valueSerializer.setObjectMapper(objectMapper);
        RedisSerializationContext<String, Object> context = RedisSerializationContext.<String, Object>newSerializationContext()
                .key(stringSerializer)
                .value(valueSerializer)
                .hashKey(stringSerializer)
                .hashValue(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }


    @Bean("reactiveRedisTemplate")
    public ReactiveRedisTemplate<String, Object> objectReactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        valueSerializer.setObjectMapper(objectMapper);

        RedisSerializationContext<String, Object> context = RedisSerializationContext.<String, Object>newSerializationContext()
                .key(stringSerializer)
                .value(valueSerializer)
                .hashKey(stringSerializer)
                .hashValue(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public RedisReactiveHealthIndicator redisReactiveHealthIndicator(ReactiveRedisConnectionFactory factory) {
        return new RedisReactiveHealthIndicator(factory);
    }

}