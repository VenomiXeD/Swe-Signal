package venomized.mods.extendedsignals.core.signalling;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.ExtendedSignalsCore;
import venomized.mods.extendedsignals.core.SignalLightState;

@RequiredArgsConstructor
public class SignalStateRemapper {
    @Getter
    private static Object2ObjectArrayMap<ResourceLocation, SignalStateRemapper> mappers = new Object2ObjectArrayMap<>();

    public static SignalStateRemapper NONE = register(new SignalStateRemapper(ExtendedSignalsCore.res("none")));

    @Getter
    private final ResourceLocation id;

    public static <T extends SignalStateRemapper> T register(T mapper) {
        if (mappers.containsKey(mapper.getId()))
            throw new IllegalArgumentException("A mapper already exists with this key");

        mappers.put(mapper.getId(), mapper);

        return mapper;
    }

    public static RawSignalState transform(ResourceLocation id, RawSignalState state) {
        if (mappers.containsKey(id))
            return mappers.get(id).remap(state);

        return state;
    }


    public RawSignalState remap(RawSignalState old) {
        return old;
    }
}
