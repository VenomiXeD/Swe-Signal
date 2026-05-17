package venomized.mods.extendedsignals;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class ModTemplate {
    public ModTemplate(FMLJavaModLoadingContext context) {
        IEventBus eventbus = context.getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
        eventbus.register(this);

        initializeContent();
    }

    protected abstract void initializeContent();
}
