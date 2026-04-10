package venomized.mods.extendedsignals.se;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraftforge.fml.common.Mod;

@Mod(SEExtendedSignals.MOD_ID)
public class SEExtendedSignals {
    public static final String MOD_ID = "extended_signals_sweden";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));
}
