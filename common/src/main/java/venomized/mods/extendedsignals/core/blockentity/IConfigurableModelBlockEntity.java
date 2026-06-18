package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public interface IConfigurableModelBlockEntity {
    double getXLocOffset();

    double getYLocOffset();

    double getZLocOffset();

    void setXLocOffset(double offset);

    void setYLocOffset(double offset);

    void setZLocOffset(double offset);

    void setLocOffset(Vec3 offset);

    double getXGblOffset();

    double getYGblOffset();

    double getZGblOffset();

    void setXGblOffset(double offset);

    void setYGblOffset(double offset);

    void setZGblOffset(double offset);

    void setGblOffset(Vec3 offset);

    float getXOrientation();

    float getYOrientation();

    float getZOrientation();

    void setXOrientation(float rotInDeg);

    void setYOrientation(float rotInDeg);

    void setZOrientation(float rotInDeg);

    void setOrientation(Vec3 orientationInDeg);

    default boolean supportsConfiguration() {
        return true;
    }

    @Nullable
    EnumSet<?> variations();
    
}
