package venomized.mods.extendedsignals.de;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import venomized.mods.extendedsignals.core.ModTemplate;

@Mod(ExtendedSignalsGermany.MOD_ID)
public class ExtendedSignalsGermany extends ModTemplate {
    public static final String MOD_ID = "extended_signals_de";

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MOD_ID));

    /**
     * @param context
     */
    public ExtendedSignalsGermany(FMLJavaModLoadingContext context) {
        super(context);
    }

    /**
     *
     */
    @Override
    protected void commonInitialization() {

    }
}
