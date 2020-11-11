package org.acme;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.StorageType;
import org.infinispan.distribution.group.Grouper;
import org.infinispan.eviction.EvictionType;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;

@ApplicationScoped
public class ScoreService {

    Cache<Object, Score> scoreCache; 

    Logger log = LoggerFactory.getLogger(ScoreService.class); 

    EmbeddedCacheManager cacheManager; 

    public List<Score> getAll() { 
        return new ArrayList<>(scoreCache.values());
    }

    public void save(Score entry) { 
        scoreCache.put(getKey(entry), entry);
    }

    public void delete(Score entry) { 
        scoreCache.remove(getKey(entry));
    }

    public void getEntry(Score entry){ 
        scoreCache.get(getKey(entry));
    }

    public static String getKey(Score entry){
        return entry.getPlayerId()+","+entry.getCourse();
    }

    public Score findById(String key) {
        return scoreCache.get(key);
    }

    void onStart(@Observes @Priority(value = 1) StartupEvent ev){
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global.transport().clusterName("ScoreCard");
        cacheManager = new DefaultCacheManager(global.build());

        ConfigurationBuilder config = new ConfigurationBuilder();

        config.expiration().lifespan(5, TimeUnit.MINUTES)
                .clustering().cacheMode(CacheMode.REPL_SYNC);

        cacheManager.defineConfiguration("scoreboard", config.build());
        scoreCache = cacheManager.getCache("scoreboard");
        scoreCache.addListener(new CacheListener());


        log.info("Cache initialized");

    }
 
}