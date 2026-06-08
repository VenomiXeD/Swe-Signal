package venomized.mods.extendedsignals.core;

import com.electronwill.nightconfig.core.ConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.checkerframework.checker.units.qual.C;

public class ExtendedSignalsConfig {
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final ServerConfig SERVER;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        SERVER = new ServerConfig(builder);
        SERVER_SPEC = builder.build();
    }

    public static class ServerConfig {
        public final ForgeConfigSpec.DoubleValue defaultScanDistance;
        public final ForgeConfigSpec.DoubleValue defaultMinScanDistance;

        ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.push("signals");
            defaultScanDistance = builder.comment("Default scan distance for signalling, also changes brass signals reservation distance")
                    .defineInRange("defaultScanDistance", 2048, 256, Double.MAX_VALUE);

            defaultMinScanDistance = builder.comment("Default scan distance for signalling, also changes brass signals reservation distance")
                    .defineInRange("defaultMinScanDistance", 256, 64, Double.MAX_VALUE);
            builder.pop();
        }
    }
}
