package github.nitespring.santan;

import github.nitespring.santan.core.init.EntityInit;
import github.nitespring.santan.core.init.ItemInit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import software.bernie.geckolib.GeckoLib;


@Mod(SaNtaNMod.MODID)
public class SaNtaNMod
{
    public static final String MODID = "santan";

    //private static final Logger LOGGER = LogUtils.getLogger();
    
    
    public SaNtaNMod(IEventBus bus, ModContainer modContainer)
    {


        //bus.addListener(this::setup);
        ItemInit.ITEMS.register(bus);
        EntityInit.ENTITIES.register(bus);

    }


}
