package venomized.mods.extendedsignals.core.blockentity;

import net.minecraft.core.Direction;

import java.util.UUID;

public interface ISignalBoundaryReferenceProvider {
    UUID id();

    Direction.AxisDirection direction();
}
