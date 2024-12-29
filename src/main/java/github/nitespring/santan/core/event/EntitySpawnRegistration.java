package github.nitespring.santan.core.event;


import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.AbstractYuleEntity;
import github.nitespring.santan.core.init.EntityInit;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = SaNtaNMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EntitySpawnRegistration {
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void registerEntitySpawn(RegisterSpawnPlacementsEvent event) {

            
            event.register(EntityInit.SNOWMAN.get(),
            		SpawnPlacementTypes.ON_GROUND,
            		Types.MOTION_BLOCKING_NO_LEAVES, 
            		AbstractYuleEntity::checkSnowMonsterSpawnRules,
					RegisterSpawnPlacementsEvent.Operation.REPLACE);
			event.register(EntityInit.GINGERBREAD.get(),
					SpawnPlacementTypes.ON_GROUND,
					Types.MOTION_BLOCKING_NO_LEAVES,
            		AbstractYuleEntity::checkSnowMonsterSpawnRules,
					RegisterSpawnPlacementsEvent.Operation.REPLACE);
			event.register(EntityInit.ELF.get(),
					SpawnPlacementTypes.ON_GROUND,
					Types.MOTION_BLOCKING_NO_LEAVES,
            		AbstractYuleEntity::checkSnowMonsterSpawnRules,
					RegisterSpawnPlacementsEvent.Operation.REPLACE);

    }
	
	
	

}
