package venomized.mods.extendedsignals.core.util;

import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NBTHelp {
    public static <T extends Enum<T>> T safeReadEnum(CompoundTag tag, @NotNull String name, @NotNull Class<T> enumClass) {
        if (tag.contains(name, Tag.TAG_STRING)) {
            return NBTHelper.readEnum(tag, name, enumClass);
        }
        return null;
    }

    public static <T extends Enum<T>> void safeWriteEnum(CompoundTag tag, @NotNull String name, @Nullable T value) {
        if (value == null)
            return;
        NBTHelper.writeEnum(tag, name, value);
    }

    public static BlockPos safeReadBlockPos(CompoundTag tag, @NotNull String name) {
        if (!tag.contains(name, Tag.TAG_COMPOUND))
            return null;

        return NbtUtils.readBlockPos(tag.getCompound(name));
    }

    public static void safeWriteBlockPos(CompoundTag tag, @NotNull String name, @Nullable BlockPos value) {
        if (value == null)
            return;

        tag.put(name, NbtUtils.writeBlockPos(value));
    }
}
