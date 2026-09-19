package venomized.mods.extendedsignals.de.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

@OnlyIn(Dist.CLIENT)
public final class GermanyModels {
    private static PartialModel model(String path) {
        return PartialModel.of(ExtendedSignalsGermany.res(path));
    }
    
    public static final class HVModels {

        public static PartialModel HV_ZS3 = model("block/signals/de/zs3");
        public static PartialModel HV_ZS3V = model("block/signals/de/zs3v");

        public static void init() {
        }
    }

    public static final class HVKModels {
        public static PartialModel MAST_CENTER = model("block/signals/de/hvk/mast/center");
        public static PartialModel LEFT_400 = model("block/signals/de/hvk/mast/400_left");
        public static PartialModel RIGHT_400 = model("block/signals/de/hvk/mast/400_right");
        public static PartialModel LEFT_1000 = model("block/signals/de/hvk/mast/1000_left");
        public static PartialModel RIGHT_1000 = model("block/signals/de/hvk/mast/1000_right");

        public static PartialModel SIGN_MAIN = model("block/signals/de/hvk/main/sign");
        public static PartialModel SIGN_MAIN_OFFSET = model("block/signals/de/hvk/main/sign_offset");

        public static PartialModel SIGN_DISTANT = model("block/signals/de/hvk/distant/sign");
        public static PartialModel SIGN_DISTANT_OFFSET = model("block/signals/de/hvk/distant/sign_offset");

        public static PartialModel ZS3_MATRIX = model("block/signals/de/hvk/zs3/zs3_matrix");
        public static PartialModel ZS3_METAL = model("block/signals/de/hvk/zs3/zs3_metal");
        public static PartialModel ZS3V_MATRIX = model("block/signals/de/hvk/zs3/zs3v_matrix");
        public static PartialModel ZS3V_METAL = model("block/signals/de/hvk/zs3/zs3v_metal");

        public static void init() {
        }
    }

    public static final class KSModels {

        public static PartialModel KS_MATRIX = model("block/signals/de/ks/ks_matrix");
        public static PartialModel KS_MATRIX_DISTANT = model("block/signals/de/ks/ks_matrix_v");
        public static PartialModel KS_ZS3_METAL = model("block/signals/de/ks/ks_zs3_metal");
        public static PartialModel KS_ZS3V_METAL = model("block/signals/de/ks/ks_zs3v_metal");
        public static PartialModel KS_VR_R_400_LEFT = model("block/signals/de/ks/ks_vr_r_400_left");
        public static PartialModel KS_VR_R_400_RIGHT = model("block/signals/de/ks/ks_vr_r_400_right");
        public static PartialModel KS_VR_R_1000_LEFT = model("block/signals/de/ks/ks_vr_r_1000_left");
        public static PartialModel KS_VR_R_1000_RIGHT = model("block/signals/de/ks/ks_vr_r_1000_right");
        public static PartialModel KS_HP_VR_400_LEFT = model("block/signals/de/ks/ks_hp_vr_400_left");
        public static PartialModel KS_HP_VR_400_RIGHT = model("block/signals/de/ks/ks_hp_vr_400_right");
        public static PartialModel KS_HP_VR_1000_LEFT = model("block/signals/de/ks/ks_hp_vr_1000_left");
        public static PartialModel KS_HP_VR_1000_RIGHT = model("block/signals/de/ks/ks_hp_vr_1000_right");
        public static PartialModel KS_VR_400_LEFT = model("block/signals/de/ks/ks_vr_400_left");
        public static PartialModel KS_VR_400_RIGHT = model("block/signals/de/ks/ks_vr_400_right");
        public static PartialModel KS_VR_1000_LEFT = model("block/signals/de/ks/ks_vr_1000_left");
        public static PartialModel KS_VR_1000_RIGHT = model("block/signals/de/ks/ks_vr_1000_right");
        public static PartialModel KS_HP_400_LEFT = model("block/signals/de/ks/ks_hp_400_left");
        public static PartialModel KS_HP_400_RIGHT = model("block/signals/de/ks/ks_hp_400_right");
        public static PartialModel KS_HP_1000_LEFT = model("block/signals/de/ks/ks_hp_1000_left");
        public static PartialModel KS_HP_1000_RIGHT = model("block/signals/de/ks/ks_hp_1000_right");

        public static void init() {
        }
    }

    public static PartialModel GATE_ARM = model("block/crossings/de/gate_arm");
    public static PartialModel NE_3_2 = model("block/signs/de/ne_3_2");
    public static PartialModel NE_3_3 = model("block/signs/de/ne_3_3");
    public static final class Ne2Models {
        public static PartialModel WEST_REDUCED_BRAKE_DISTANCE = model("block/signs/de/ne_2_west_brake");
        public static PartialModel EAST_REDUCED_BRAKE_DISTANCE = model("block/signs/de/ne_2_east_brake");
        public static PartialModel SMALL_EAST_REDUCED_BRAKE_DISTANCE = model("block/signs/de/ne_2_small_east_brake");
        public static PartialModel SMALL_WEST_REDUCED_BRAKE_DISTANCE = model("block/signs/de/ne_2_small_west_brake");
        public static void init() {
        }
    }

    public static final class Ne5Models {
        public static PartialModel EAST = model("block/signs/de/ne_5_east");
        public static PartialModel EAST_BOTH = model("block/signs/de/ne_5_east_both");
        public static PartialModel EAST_LEFT = model("block/signs/de/ne_5_east_left");
        public static PartialModel EAST_RIGHT = model("block/signs/de/ne_5_east_right");

        public static PartialModel WEST_LEFT = model("block/signs/de/ne_5_west_left");
        public static PartialModel WEST_BOTH = model("block/signs/de/ne_5_west_both");
        public static PartialModel WEST_RIGHT = model("block/signs/de/ne_5_west_right");

        public static PartialModel SMALL_EAST = model("block/signs/de/ne_5_small_east");
        public static PartialModel SMALL_EAST_RIGHT = model("block/signs/de/ne_5_small_east_right");
        public static PartialModel SMALL_EAST_BOTH = model("block/signs/de/ne_5_small_east_both");
        public static PartialModel SMALL_EAST_LEFT = model("block/signs/de/ne_5_small_east_left");

        public static PartialModel SMALL_WEST_BOTH = model("block/signs/de/ne_5_small_west_both");
        public static PartialModel SMALL_WEST_LEFT = model("block/signs/de/ne_5_small_west_left");
        public static PartialModel SMALL_WEST_RIGHT = model("block/signs/de/ne_5_small_west_right");

        public static void init() {
        }
    }

    public static void init() {
        KSModels.init();
        HVModels.init();
        HVKModels.init();
        Ne2Models.init();
        Ne5Models.init();
    }
}
