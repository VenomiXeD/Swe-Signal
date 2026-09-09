package venomized.mods.extendedsignals.core.block;

import venomized.mods.extendedsignals.core.client.sound.train.EmptyTrainSound;
import venomized.mods.extendedsignals.core.client.sound.train.TrainSound;

public class BlockSteamMuffler extends ExtendedSignalsBlock implements ITrainSoundModifierBlock {
    /**
     * @param pProperties
     */
    public BlockSteamMuffler(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public TrainSound constructTrainSound() {
        return new EmptyTrainSound();
    }
}
