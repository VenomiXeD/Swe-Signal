package venomized.mods.extendedsignals.de.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import venomized.mods.extendedsignals.de.ExtendedSignalsGermany;

@OnlyIn(Dist.CLIENT)
public final class GermanyModels {
    public static PartialModel HV_ZS3 = PartialModel.of(
            ExtendedSignalsGermany.res("block/signals/de/zs3")
    );

    public static PartialModel HV_ZS3V = PartialModel.of(
            ExtendedSignalsGermany.res("block/signals/de/zs3v")
    );

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
