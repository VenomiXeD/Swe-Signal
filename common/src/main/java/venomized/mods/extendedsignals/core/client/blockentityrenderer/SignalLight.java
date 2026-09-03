package venomized.mods.extendedsignals.core.client.blockentityrenderer;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.Mth;
import venomized.mods.extendedsignals.core.util.TrackedValue;

public class SignalLight {
    @Getter
    final private double x;
    @Getter
    final private double y;
    @Getter
    final private double z;
    @Getter
    final private float xScale;
    @Getter
    final private float yScale;
    @Getter
    final private float zScale;
    @Getter
    private LightState state;

    public SignalLight() {
        this(0, 0, 0, 1, 1, 1);
    }

    public SignalLight(double x, double y, double z, float xScale, float yScale, float zScale) {
        state = new LightState();
        this.x = x;
        this.y = y;
        this.z = z;

        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;
    }

    public SignalLight(double x, double y, double z, float xScale, float yScale, float zScale, int defaultRed, int defaultGreen, int defaultBlue) {
        this(x, y, z, xScale, yScale, zScale);
        withDefaultColor(defaultRed, defaultGreen, defaultBlue);
    }

    public static SignalLight redLight(double x, double y, double z, float xScale, float yScale, float zScale) {
        return new SignalLight(x, y, z, xScale, yScale, zScale).withDefaultColor(255, 0, 0);
    }

    public static SignalLight greenLight(double x, double y, double z, float xScale, float yScale, float zScale) {
        return new SignalLight(x, y, z, xScale, yScale, zScale).withDefaultColor(0, 255, 0);
    }

    public static SignalLight blueLight(double x, double y, double z, float xScale, float yScale, float zScale) {
        return new SignalLight(x, y, z, xScale, yScale, zScale).withDefaultColor(0, 0, 255);
    }

    public static SignalLight whiteLight(double x, double y, double z, float xScale, float yScale, float zScale) {
        return new SignalLight(x, y, z, xScale, yScale, zScale).withDefaultColor(255, 255, 255);
    }

    public static SignalLight yellowLight(double x, double y, double z, float xScale, float yScale, float zScale) {
        return new SignalLight(x, y, z, xScale, yScale, zScale).withDefaultColor(255, 191, 0);
    }

    public SignalLight withDefaultColor(int r, int g, int b) {
        state.defaultRed = r;
        state.defaultGreen = g;
        state.defaultBlue = b;
        return this;
    }

    public static class LightState {
        private final TrackedValue<Integer> red = new TrackedValue<>(0, this::redChanged);
        private final TrackedValue<Integer> green = new TrackedValue<>(0, this::greenChanged);
        private final TrackedValue<Integer> blue = new TrackedValue<>(0, this::blueChanged);
        @Setter
        private boolean ignoreFadeTicks;
        @Setter
        private long currentTick;
        @Setter
        private float fadeSeconds = 5f / 20f;
        private int defaultRed;
        private int defaultGreen;
        private int defaultBlue;
        private long redChangeTimestamp;
        private long greenChangeTimestamp;
        private long blueChangeTimestamp;

        private void redChanged(int newVal, int oldVal) {
            redChangeTimestamp = System.nanoTime();
        }

        private void greenChanged(int newVal, int oldVal) {
            greenChangeTimestamp = System.nanoTime();
        }

        private void blueChanged(int newVal, int oldVal) {
            blueChangeTimestamp = System.nanoTime();
        }

        public void setLit(boolean lit) {
            setRed(lit ? defaultRed : 0);
            setGreen(lit ? defaultGreen : 0);
            setBlue(lit ? defaultBlue : 0);
        }

        public void setRed(int newRed) {
            red.change(newRed);
        }

        public void setGreen(int newGreen) {
            green.change(newGreen);
        }

        public void setBlue(int newBlue) {
            blue.change(newBlue);
        }

        public void setColor(int newRed, int newGreen, int newBlue) {
            setRed(newRed);
            setGreen(newGreen);
            setBlue(newBlue);
        }

        public int getRedOutput(float partialTick) {
            float s = (System.nanoTime() - redChangeTimestamp) / 1_000_000_000f;
            float progress = Math.min(1, (s / (fadeSeconds)));
            return (int) Mth.lerp(ignoreFadeTicks ? 1 : progress, red.fromValue(), red.toValue());
        }

        public int getGreenOutput(float partialTick) {
            float s = (System.nanoTime() - greenChangeTimestamp) / 1_000_000_000f;
            float progress = Math.min(1, (s / (fadeSeconds)));
            return (int) Mth.lerp(ignoreFadeTicks ? 1 : progress, green.fromValue(), green.toValue());
        }

        public int getBlueOutput(float partialTick) {
            float s = (System.nanoTime() - blueChangeTimestamp) / 1_000_000_000f;
            float progress = Math.min(1, (s / (fadeSeconds)));
            return (int) Mth.lerp(ignoreFadeTicks ? 1 : progress, blue.fromValue(), blue.toValue());
        }
    }
}
