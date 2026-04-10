package venomized.mods.extendedsignals.data;

import net.minecraft.network.chat.Component;
import venomized.mods.extendedsignals.ExtendedSignalsCore;

import java.util.Map;

public class SwSignalLang {
    public static final Map<String, String> LANGUAGE_NAMES = Map.of(
            "se", "Swedish",
            "en", "English",
            "fr", "French",
            "de", "German",
            "es", "Spanish"
    );

    public static void languageEntries() {
        schedule("door", "summary", "Door Control");
        SwSignalLang.schedule("door", "title", "Close doors");
        SwSignalLang.schedule("instruction", "door", "Close doors");
    }

    public static Component schedule(final String name, final String type, final String value) {
        final String key = "%s.schedule.%s.%s".formatted(ExtendedSignalsCore.MOD_ID, name, type);
        return ExtendedSignalsCore.REGISTRATE.get().addRawLang(key, value);
    }

    public static String fromISO639_1(String iso639_1_code) {
        return LANGUAGE_NAMES.getOrDefault(iso639_1_code.toLowerCase(), "Unknown");
    }
}
