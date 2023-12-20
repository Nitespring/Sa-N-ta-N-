package github.nitespring.santan.core.event;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilElf;
import github.nitespring.santan.common.entity.mob.EvilSnowman;
import github.nitespring.santan.common.entity.mob.GingerbreadMan;
import github.nitespring.santan.core.init.EntityInit;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid = SaNtaNMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EntityAttributeRegistration {
	
	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		

		event.put(EntityInit.SNOWMAN.get(), EvilSnowman.setCustomAttributes().build());
		event.put(EntityInit.GINGERBREAD.get(), GingerbreadMan.setCustomAttributes().build());
		event.put(EntityInit.ELF.get(), EvilElf.setCustomAttributes().build());
		
	}

}
