package com.heureso.entities;

import com.heureso.mapping.MeterReading;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.string.SetArgs;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Singleton;

import java.time.Duration;
import java.util.function.Supplier;

// Reference: https://quarkus.io/guides/redis-reference

//https://github.com/cescoffier/quarkus-redis-demo/blob/main/src/main/java/me/escoffier/quarkus/supes/MyRedisCache.java

@Singleton
public class MeterReadingValueCache {

    private final ReactiveValueCommands<String, MeterReading> commands;



    public MeterReadingValueCache(ReactiveRedisDataSource ds)
    {
        this.commands = ds.value(MeterReading.class);
    }


    public Uni<MeterReading> get(String key) {
        return commands.get(key).onItem().invoke(
            item -> System.out.println("Found " + item.readingamount)
        );
    }

    public Uni<Void> set(String key, MeterReading result) {
        return commands.set(key, result, new SetArgs().ex(Duration.ofSeconds(1000)));
    }

    public void evict(String key) {
        commands.getdel(key);
    }

    public Uni<MeterReading> getOrSetIfAbsent(String key, Supplier<MeterReading> computation)
    {
        return get(key).onItem()
            .transform( item ->
            {
                if ( item != null ) return item;
            else {
                    var result = computation.get();
                    set(key, result);
                    return result;
                }
        });
    }
}
