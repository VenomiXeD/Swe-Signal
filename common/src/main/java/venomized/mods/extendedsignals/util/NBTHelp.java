package venomized.mods.extendedsignals.util;

import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public final class NBTHelp {
    public static <T extends Enum<T>> T safeReadEnum(CompoundTag tag, String name, Class<T> enumClass) {
        if (tag.contains(name, Tag.TAG_STRING)) {
            return NBTHelper.readEnum(tag, name, enumClass);
        }
        return null;
    }

    public static <T extends Enum<T>> void safeWriteEnum(CompoundTag tag, String name, T value) {
        if (value == null)
            return;
        NBTHelper.writeEnum(tag, name, value);
    }
}
