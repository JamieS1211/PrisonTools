package com.github.jamies1211.prisontools.Data.customData;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class MyKeys {
    public MyKeys() {}
    public static void dummy() {} // invoke static constructor

    // Event Keys
    public static final Key<Value<Boolean>> IS_EVENT_TOKEN;
    public static final Key<Value<String >> EVENT_TYPE;

    // EVTrain Keys
    public static final Key<Value<Boolean>> IS_EVTRAIN_TOKEN;
    public static final Key<Value<Double>> EVTRAIN_TIME;
    public static final Key<Value<Integer>> EVTRAIN_ZONE;

    // GYM Keys
    public static final Key<Value<Boolean>> IS_GYM_TOKEN;
    public static final Key<Value<String >> GYM_RANK;

    // Safari Keys
    public static final Key<Value<Boolean>> IS_SAFARI_TOKEN;
    public static final Key<Value<Double>> SAFARI_TIME;
    public static final Key<Value<Integer>> SAFARI_ZONE;


    static {
        //EVENT
        IS_EVENT_TOKEN = Key.builder()
                .type(TypeTokens.BOOLEAN_VALUE_TOKEN)
                .id("prisontools:iseventtoken")
                .name("Is Event Token")
                .query(DataQuery.of('.', "eventtoken.boolean"))
                .build();

        EVENT_TYPE = Key.builder()
                .type(TypeTokens.STRING_VALUE_TOKEN)
                .id("prisontools:eventtype")
                .name("Event Type")
                .query(DataQuery.of('.', "eventtoken.type"))
                .build();



        //EVTRAIN
        IS_EVTRAIN_TOKEN = Key.builder()
                .type(TypeTokens.BOOLEAN_VALUE_TOKEN)
                .id("prisontools:isevtraintoken")
                .name("Is EVTrain Token")
                .query(DataQuery.of('.', "evtraintoken.boolean"))
                .build();

        EVTRAIN_TIME = Key.builder()
                .type(TypeTokens.DOUBLE_VALUE_TOKEN)
                .id("prisontools:evtraintime")
                .name("EVTrain Time")
                .query(DataQuery.of('.', "evtraintoken.time"))
                .build();

        EVTRAIN_ZONE = Key.builder()
                .type(TypeTokens.INTEGER_VALUE_TOKEN)
                .id("prisontools:evtrainzone")
                .name("EVTrain Zone")
                .query(DataQuery.of('.', "evtraintoken.zone"))
                .build();

        //GYM
        IS_GYM_TOKEN = Key.builder()
                .type(TypeTokens.BOOLEAN_VALUE_TOKEN)
                .id("prisontools:isgymtoken")
                .name("Is Gym Token")
                .query(DataQuery.of('.', "gymtoken.boolean"))
                .build();

        GYM_RANK = Key.builder()
                .type(TypeTokens.STRING_VALUE_TOKEN)
                .id("prisontools:gymrank")
                .name("Gym Rank")
                .query(DataQuery.of('.', "gymtoken.rank"))
                .build();



        //SAFARI
        IS_SAFARI_TOKEN = Key.builder()
                .type(TypeTokens.BOOLEAN_VALUE_TOKEN)
                .id("prisontools:issafartoken")
                .name("Is Safari Token")
                .query(DataQuery.of('.', "safaritoken.boolean"))
                .build();

        SAFARI_TIME = Key.builder()
                .type(TypeTokens.DOUBLE_VALUE_TOKEN)
                .id("prisontools:safaritime")
                .name("Safari Time")
                .query(DataQuery.of('.', "safaritoken.time"))
                .build();

        SAFARI_ZONE = Key.builder()
                .type(TypeTokens.INTEGER_VALUE_TOKEN)
                .id("prisontools:safarizone")
                .name("Safari Zone")
                .query(DataQuery.of('.', "safaritoken.zone"))
                .build();
    }
}
