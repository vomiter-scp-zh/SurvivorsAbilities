package com.vomiter.survivorsabilities;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = SurvivorsAbilities.MODID)
public class SAConfig {
    public static class Common {
        public final ModConfigSpec.BooleanValue forceCancelOverburden;
        public Common(ModConfigSpec.Builder builder) {
            forceCancelOverburden = builder
                    .comment("This is a fallback option when some mods in your modpack override the effect of workhorse. It should be false if the effect works just fine.")
                    .define("forceCancelOverburden", false);
        }
    }

    public static final ModConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

}
