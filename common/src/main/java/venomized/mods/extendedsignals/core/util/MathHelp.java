package venomized.mods.extendedsignals.core.util;

public final class MathHelp {
    public static float easeInOutBack(float x) {
        float c1 = 1.70158f;
        float c2 = c1 * 1.525f;
        return easeInOutBack(x, c1, c2);
    }

    public static float easeInOutBack(float x, float c1) {
        float c2 = c1 * 1.525f;
        return easeInOutBack(x, c1, c2);
    }

    public static float easeInOutBack(float x, float c1, float c2) {
        return x < 0.5f
                ? ((float) Math.pow(2f * x, 2f) * ((c2 + 1f) * 2f * x - c2)) / 2f
                : ((float) Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2;
    }

    public static double MsFromKph(final double kph) {
        return kph / 3.6d;
    }

    public static double KphFromMs(final double ms) {
        return ms * 3.6d;
    }

}