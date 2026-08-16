package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.world.phys.Vec3;

public interface IConfigurableModelBlockEntity {
    default boolean supportsTranslation() {
        return true;
    }

    double getXLocOffset();

    void setXLocOffset(double offset);

    double getYLocOffset();

    void setYLocOffset(double offset);

    double getZLocOffset();

    void setZLocOffset(double offset);

    void setLocOffset(Vec3 offset);

    double getXGblOffset();

    void setXGblOffset(double offset);

    double getYGblOffset();

    void setYGblOffset(double offset);

    double getZGblOffset();

    void setZGblOffset(double offset);

    void setGblOffset(Vec3 offset);

    float getXOrientation();

    void setXOrientation(float rotInDeg);

    float getYOrientation();

    void setYOrientation(float rotInDeg);

    float getZOrientation();

    void setZOrientation(float rotInDeg);

    void setOrientation(Vec3 orientationInDeg);

    VariantData variantData();
}
