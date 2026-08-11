package venomized.mods.extendedsignals.core;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ExtendedSignalsConfig {
    public static final ModConfigSpec SERVER_SPEC;
    public static final ServerConfig SERVER;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        SERVER = new ServerConfig(builder);
        SERVER_SPEC = builder.build();
    }

    public static class ServerConfig {
        public final ModConfigSpec.DoubleValue defaultScanDistance;
        public final ModConfigSpec.DoubleValue defaultMinScanDistance;

        ServerConfig(ModConfigSpec.Builder builder) {
            builder.push("signals");
            defaultScanDistance = builder.comment("Default scan distance for signalling, also changes brass signals reservation distance")
                    .defineInRange("defaultScanDistance", 2048, 32, Double.MAX_VALUE);

            defaultMinScanDistance = builder.comment("Default scan distance for signalling, also changes brass signals reservation distance")
                    .defineInRange("defaultMinScanDistance", 128, 16, Double.MAX_VALUE);
            builder.pop();
        }
    }
}
