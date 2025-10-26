package venomized.mc.mods.swsignals.client.sound.train;

import net.minecraft.client.resources.sounds.Sound;

import java.util.function.Supplier;

public class TrainSoundConfiguration<T extends TrainSound> {
    protected TrainSoundConfiguration(Supplier<T> soundSupplier) {
        this.trainSoundSupplier = soundSupplier;
    }
    private final Supplier<T> trainSoundSupplier;
    public static <E extends TrainSound> Supplier<TrainSoundConfiguration<E>> ofTrainSound(Supplier<E> supplier) {
        return () -> new TrainSoundConfiguration(supplier);
    }

    public T create() {
        return trainSoundSupplier.get();
    }
}
