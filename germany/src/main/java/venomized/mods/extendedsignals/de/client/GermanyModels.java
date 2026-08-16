package venomized.mods.extendedsignals.de.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

@OnlyIn(Dist.CLIENT)
public final class GermanyModels {
    public static final class HVModels {

        public static PartialModel HV_ZS3 = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/zs3")
        );
        public static PartialModel HV_ZS3V = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/zs3v")
        );

        public static void init() {
        }
    }

    public static final class KSModels {

        public static PartialModel KS_MATRIX = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_matrix")
        );
        public static PartialModel KS_MATRIX_DISTANT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_matrix_v")
        );
        public static PartialModel KS_ZS3_METAL = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_zs3_metal")
        );
        public static PartialModel KS_ZS3V_METAL = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_zs3v_metal")
        );
        public static PartialModel KS_VR_R_400_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_vr_r_400_left")
        );
        public static PartialModel KS_VR_R_400_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_vr_r_400_right")
        );
        public static PartialModel KS_VR_R_1000_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_vr_r_1000_left")
        );
        public static PartialModel KS_VR_R_1000_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_vr_r_1000_right")
        );
        public static PartialModel KS_HP_VR_400_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_hp_vr_400_left")
        );
        public static PartialModel KS_HP_VR_400_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_hp_vr_400_right")
        );
        public static PartialModel KS_HP_VR_1000_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_hp_vr_1000_left")
        );
        public static PartialModel KS_HP_VR_1000_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_hp_vr_1000_right")
        );
        public static PartialModel KS_VR_400_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_vr_400_left")
        );
        public static PartialModel KS_VR_400_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_vr_400_right")
        );
        public static PartialModel KS_VR_1000_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_vr_1000_left")
        );
        public static PartialModel KS_VR_1000_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_vr_1000_right")
        );
        public static PartialModel KS_HP_400_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_hp_400_left")
        );
        public static PartialModel KS_HP_400_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_hp_400_right")
        );
        public static PartialModel KS_HP_1000_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_hp_1000_left")
        );
        public static PartialModel KS_HP_1000_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signals/de/ks/ks_hp_1000_right")
        );

        public static void init() {
        }
    }

    public static PartialModel GATE_ARM = PartialModel.of(
            ExtendedSignalsGermany.res("block/crossings/de/gate_arm")
    );

    public static PartialModel NE_3_2 = PartialModel.of(
            ExtendedSignalsGermany.res("block/signs/de/ne_3_2")
    );

    public static PartialModel NE_3_3 = PartialModel.of(
            ExtendedSignalsGermany.res("block/signs/de/ne_3_3")
    );

    public static final class Ne2Models {
        // public static PartialModel WEST = PartialModel.of(
        //         ExtendedSignalsGermany.res("block/signs/de/ne_2_east")
        // );
        public static PartialModel WEST_REDUCED_BRAKE_DISTANCE = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_2_west_brake")
        );
        public static PartialModel EAST_REDUCED_BRAKE_DISTANCE = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_2_east_brake")
        );

        public static PartialModel SMALL_EAST_REDUCED_BRAKE_DISTANCE = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_2_small_east_brake")
        );

        public static PartialModel SMALL_WEST_REDUCED_BRAKE_DISTANCE = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_2_small_west_brake")
        );

        public static void init() {
        }
    }

    public static final class Ne5Models {
        public static PartialModel EAST = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_east")
        );

        public static PartialModel EAST_BOTH = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_east_both")
        );

        public static PartialModel EAST_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_east_left")
        );

        public static PartialModel EAST_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_east_right")
        );

        // public static PartialModel NE_2_WEST = PartialModel.of(
        //         ExtendedSignalsGermany.res("block/signs/de/ne_2_east")
        // );


        public static PartialModel WEST_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_west_left")
        );
        public static PartialModel WEST_BOTH = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_west_both")
        );

        public static PartialModel WEST_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_west_right")
        );


        public static PartialModel SMALL_EAST = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_small_east")
        );
        public static PartialModel SMALL_EAST_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_small_east_right")
        );
        public static PartialModel SMALL_EAST_BOTH = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_small_east_both")
        );
        public static PartialModel SMALL_EAST_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_small_east_left")
        );


        // public static PartialModel NE_2_WEST = PartialModel.of(
        //         ExtendedSignalsGermany.res("block/signs/de/ne_2_east")
        // );

        public static PartialModel SMALL_WEST_BOTH = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_small_west_both")
        );

        public static PartialModel SMALL_WEST_LEFT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_small_west_left")
        );

        public static PartialModel SMALL_WEST_RIGHT = PartialModel.of(
                ExtendedSignalsGermany.res("block/signs/de/ne_5_small_west_right")
        );

        public static void init() {
        }
    }

    public static void init() {
        KSModels.init();
        HVModels.init();
        Ne2Models.init();
        Ne5Models.init();
    }
}
