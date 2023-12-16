package github.nitespring.santan.core.event;


import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.AbstractYuleEntity;
import github.nitespring.santan.core.init.EntityInit;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = SaNtaNMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitySpawnRegistration {
	
	@SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            
            SpawnPlacements.register(EntityInit.SNOWMAN.get(), 
            		SpawnPlacements.Type.ON_GROUND, 
            		Types.MOTION_BLOCKING_NO_LEAVES, 
            		AbstractYuleEntity::checkYuleMonsterSpawnRules);
            
      
        });
    }
	
	
	

}
