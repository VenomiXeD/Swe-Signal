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
        public final ModConfigSpec.IntValue signalPassedStateChangeDelay;

        public final ModConfigSpec.BooleanValue creativeBlockBreakingMovementBehavior;

        ServerConfig(ModConfigSpec.Builder builder) {
            builder.push("signals");
            defaultScanDistance = builder.comment("Default scan distance for signalling, also changes brass signals reservation distance")
                    .defineInRange("defaultScanDistance", 2048, 32, Double.MAX_VALUE);

            defaultMinScanDistance = builder.comment("Default scan distance for signalling, also changes brass signals reservation distance")
                    .defineInRange("defaultMinScanDistance", 128, 16, Double.MAX_VALUE);

            signalPassedStateChangeDelay = builder.comment("The delay (in ticks) for how long it takes for a signal to go from proceed to stop after a train has passed")
                    .defineInRange("signalPassDelay", 20 * 4, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("creative");
            creativeBlockBreakingMovementBehavior = builder.comment("Makes Movement behavior blocks (Drills, Saws, Rollers) have infinite break speed and disables stalling. Use with caution")
                    .define("creativeBlockBreakingMovementBehavior", false);
            builder.pop();
        }
    }

    public static class ClientConfig {
        public final ModConfigSpec.BooleanValue overlay_alwaysDisplayed;
        public final ModConfigSpec.BooleanValue overlay_showTrackGraph;

        public final ModConfigSpec.BooleanValue flareEnabled;

        public final ModConfigSpec.DoubleValue flareMinDistance;
        public final ModConfigSpec.DoubleValue flareMaxDistance;

        public final ModConfigSpec.DoubleValue flareMinAmbientBrightness;
        public final ModConfigSpec.DoubleValue flareMaxAmbientBrightness;

        public final ModConfigSpec.DoubleValue flareAlphaMultiplier;

        public ClientConfig(ModConfigSpec.Builder builder) {
            builder.push("visuals");
            overlay_alwaysDisplayed = builder.comment("[EXPERIMENTAL]\nIf Create Train signals should show the Overlay on tracks at all times; False will only show them with relevant signal items (Create Wrench, Track, Extended Signals items)")
                    .define("alwaysDisplaySignalPlates", true);

            overlay_showTrackGraph = builder.comment("[EXPERIMENTAL]\nIf Tracks should also display the Track graph (similar to debug Track graphs in F3)")
                    .define("showTrackGraphsWhileUsingTracks", true);

            builder.push("flare");
            flareEnabled = builder.comment("[EXPRIMENTAL]\nRender a signal flare over lights").define("flareEnabled", false);
            flareMinDistance = builder.comment("Flare minimum distance where brightness is 0").defineInRange("flareMinDist", 16d, 0, Double.MAX_VALUE);
            flareMaxDistance = builder.comment("Flare maximum distance where brightness is 1").defineInRange("flareMaxDist", 32d, 0, Double.MAX_VALUE);

            flareMinAmbientBrightness = builder.comment("Flare maximum brightness when ambient brightness is either equal or more").defineInRange("flareMinAmbientBrightness", 5d, 0d, 15d);
            flareMaxAmbientBrightness = builder.comment("Flare minimum brightness when ambient brightness is either equal or less").defineInRange("flareMaxAmbientBrightness", 15, 0, 15d);

            flareAlphaMultiplier = builder.comment("Flare brightness alpha multiplier").defineInRange("flareAlpha", 1d, 0d, 1d);
            builder.pop();

            builder.pop();
        }
    }
}
