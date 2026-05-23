package venomized.mods.extendedsignals.se.block.se;

import venomized.mods.extendedsignals.core.block.BlockSignal;

public abstract class BlockSwedenSignal extends BlockSignal {
    /**
     * @param pProperties
     * @param signalLightCount
     */
    public BlockSwedenSignal(Properties pProperties, int signalLightCount) {
        super(pProperties, signalLightCount);
    }

    /**
     * @return
     */
    @Override
    public double lightXPosition() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public double lightYPosition() {
        return (19.75d / 16d);
    }

    /**
     * @return
     */
    @Override
    public double lightZPosition() {
        return 0.25d / 16d;
    }

    /**
     * @return
     */
    @Override
    public double lightSeparationDistance() {
        return (26.75d / 16d) - (19.7d / 16d);
    }

    /**
     * @return
     */
    @Override
    public float lightXScale() {
        return 3;
    }

    /**
     * @return
     */
    @Override
    public float lightYScale() {
        return 3;
    }

    /**
     * @return
     */
    @Override
    public float lightZScale() {
        return 0;
    }
}
