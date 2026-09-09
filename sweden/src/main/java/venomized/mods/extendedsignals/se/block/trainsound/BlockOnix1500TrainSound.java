package venomized.mods.extendedsignals.se.block.trainsound;

import venomized.mods.extendedsignals.core.block.ExtendedSignalsBlock;
import venomized.mods.extendedsignals.core.block.ITrainSoundModifierBlock;
import venomized.mods.extendedsignals.core.client.sound.train.TrainSound;
import venomized.mods.extendedsignals.se.client.sound.train.TrainSoundX60;

public class BlockOnix1500TrainSound extends ExtendedSignalsBlock implements ITrainSoundModifierBlock {
    /**
     * @param pProperties
     */
    public BlockOnix1500TrainSound(Properties pProperties) {
        super(pProperties);
    }

    /**
     * @return
     */
    @Override
    public TrainSound constructTrainSound() {
        return new TrainSoundX60();
    }
}
