package venomized.mc.mods.swsignals.core.signals;

public abstract class SignalDisplayPattern {
    public static float[] intToRGB(int i) {
        float r, g, b;
        r = (i >> 16 & 0xFF) / 255.0F;
        g = (i >> 8 & 0xFF) / 255.0F;
        b = (i & 0xFF) / 255.0F;
        return new float[] {r, g, b};
    }
    public static int rgbToInt(final float r, final float g, final float b) {
        int i = (int)(r * 255) << 16 | (int)(g * 255) << 8 | (int)(b * 255);
        return i;
    }

    private float[] lightLevels;
    private int[] lightColors;

    public float[] getLightAt(int lightPosition) {
        float[] result = intToRGB(lightColors[lightPosition]);
        result[0] *= lightLevels[lightPosition];
        result[1] *= lightLevels[lightPosition];
        result[2] *= lightLevels[lightPosition];

        return result;
    }

    public enum LightStyle {
        UNLIT,
        LIT,
        BLINKING,
        PULSING,
    }
}
