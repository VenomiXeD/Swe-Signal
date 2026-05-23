package venomized.mods.extendedsignals.core.blockentity;

import lombok.Getter;
import lombok.Setter;

public interface IOrientedBlockEntity {
    String TAG_ORIENTATION_INDEX_NAME = "orientation_rotation";

    float getYOrientation();

    void setYOrientation(float pYOrientation);
}
