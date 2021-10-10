package com.example.SubnetUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;



@SpringBootTest
class CacheValidation {



    @Test
    void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
        
    	// AWS Elasticache Cluster config

        Config config = new Config();
        config.useClusterServers()
              .addNodeAddress("redis://myrediscluster-0001-001.ykrmy9.0001.use1.cache.amazonaws.com:6379");
        
        
        RedissonClient redisson = Redisson.create(config);
        
        RMap<String, List<String>> map = redisson.getMap("subnet");
        
        List<String> list  = new ArrayList<>();
        list.add("224.0.0.0/3");
        
        map.put("200", list);
        
        List<String> r1  = map.get("200");
        
    	
    }
}