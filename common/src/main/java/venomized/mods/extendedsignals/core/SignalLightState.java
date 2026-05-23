package venomized.mods.extendedsignals.core;

import net.minecraft.util.Mth;

public class SignalLightState {
    private float previousRed;
    private float previousGreen;
    private float previousBlue;

    private float currentRed;
    private float currentGreen;
    private float currentBlue;

    private boolean lit = true;

    public void setColor(float red, float green, float blue) {
        this.previousRed = this.currentRed;
        this.previousGreen = this.currentGreen;
        this.previousBlue = this.currentBlue;

        this.currentRed = Mth.clamp(red, 0.0f, 1.0f);
        this.currentGreen = Mth.clamp(green, 0.0f, 1.0f);
        this.currentBlue = Mth.clamp(blue, 0.0f, 1.0f);
    }

    public void setColor(int redPower, int greenPower, int bluePower) {
        setColor(
                redPower / 20.0f,
                greenPower / 20.0f,
                bluePower / 20.0f
        );
    }

    public float r(float partialTick) {
        if (!lit) return 0.0f;
        return Mth.lerp(partialTick, previousRed, currentRed);
    }

    public float g(float partialTick) {
        if (!lit) return 0.0f;
        return Mth.lerp(partialTick, previousGreen, currentGreen);
    }

    public float b(float partialTick) {
        if (!lit) return 0.0f;
        return Mth.lerp(partialTick, previousBlue, currentBlue);
    }

    public SignalLightState powered(boolean lit) {
        this.lit = lit;
        return this;
    }
}