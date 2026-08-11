package venomized.mods.extendedsignals.core;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ExtendedSignalsConfig {
    public static final ModConfigSpec SERVER_SPEC;
    public static final ServerConfig SERVER;

    public static final ModConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;

    static {
        ModConfigSpec.Builder builder;
        builder = new ModConfigSpec.Builder();
        SERVER = new ServerConfig(builder);
        SERVER_SPEC = builder.build();

        builder = new ModConfigSpec.Builder();
        CLIENT = new ClientConfig(builder);
        CLIENT_SPEC = builder.build();
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

    public static class ClientConfig {
        public final ModConfigSpec.BooleanValue alwaysDisplaySignalPlates;
        public final ModConfigSpec.BooleanValue showTrackGraphsWhenUsingTracks;

        public ClientConfig(ModConfigSpec.Builder builder) {
            builder.push("visuals");
            alwaysDisplaySignalPlates = builder.comment("[EXPERIMENTAL]\nIf Create Train signals should show the Overlay on tracks at all times; False will only show them with relevant signal items (Create Wrench, Track, Extended Signals items)")
                    .define("alwaysDisplaySignalPlates", true);

            showTrackGraphsWhenUsingTracks = builder.comment("[EXPERIMENTAL]\nIf Tracks should also display the Track graph (similar to debug Track graphs in F3)")
                    .define("showTrackGraphsWhileUsingTracks", true);

            builder.pop();
        }
    }
}
