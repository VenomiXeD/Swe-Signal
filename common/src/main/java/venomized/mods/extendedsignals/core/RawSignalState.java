package venomized.mods.extendedsignals.core;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;

public class RawSignalState {
    @Getter
    @Setter
    private boolean proceed;

    public static RawSignalState fromNBT(final CompoundTag tag) {
        final RawSignalState rawSignalState = new RawSignalState();
        rawSignalState.setProceed(tag.getBoolean("proceed"));
        return rawSignalState;
    }

    public RawSignalState withProceed(final boolean proceed) {
        this.proceed = proceed;
        return this;
    }

    public CompoundTag toNBT() {
        final CompoundTag tag = new CompoundTag();
        tag.putBoolean("proceed", isProceed());
        return tag;
    }

    /**
     * @param obj the reference object with which to compare.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof RawSignalState other))
            return false;

        return this.isProceed() == other.isProceed();
    }
}
