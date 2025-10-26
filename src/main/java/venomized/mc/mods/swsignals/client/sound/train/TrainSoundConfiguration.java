package venomized.mc.mods.swsignals.client.sound.train;

import java.util.function.Supplier;

public class TrainSoundConfiguration<T extends TrainSound> {
    private final Supplier<T> trainSoundSupplier;

    protected TrainSoundConfiguration(Supplier<T> soundSupplier) {
        this.trainSoundSupplier = soundSupplier;
    }

    public static <E extends TrainSound> Supplier<TrainSoundConfiguration<E>> ofTrainSound(Supplier<E> supplier) {
        return () -> new TrainSoundConfiguration(supplier);
    }

    public T create() {
        return trainSoundSupplier.get();
    }
}
