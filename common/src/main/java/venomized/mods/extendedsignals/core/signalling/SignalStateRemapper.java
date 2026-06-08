package venomized.mods.extendedsignals.core.signalling;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import venomized.mods.extendedsignals.core.ExtendedSignals;

@RequiredArgsConstructor
public class SignalStateRemapper {
    @Getter
    private final ResourceLocation id;
    public static final Object2ObjectArrayMap<ResourceLocation, SignalStateRemapper> MAPPERS = new Object2ObjectArrayMap<>();


    public static SignalStateRemapper NONE = register(new SignalStateRemapper(ExtendedSignals.res("none")));

    public static <T extends SignalStateRemapper> T register(T mapper) {
        if (MAPPERS.containsKey(mapper.getId()))
            throw new IllegalArgumentException("A mapper already exists with this key");

        MAPPERS.put(mapper.getId(), mapper);

        return mapper;
    }

    public static SignalStateNode transform(ResourceLocation id, SignalStateNode state) {
        if (MAPPERS.containsKey(id))
            return MAPPERS.get(id).remap(state);

        return state;
    }


    public SignalStateNode remap(SignalStateNode old) {
        return old;
    }
}
