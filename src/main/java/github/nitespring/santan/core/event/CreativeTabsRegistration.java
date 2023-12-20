package github.nitespring.santan.core.event;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.core.init.ItemInit;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod.EventBusSubscriber(modid = SaNtaNMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CreativeTabsRegistration {
	
	
	 //public static CreativeModeTab ITEM_TAB;
	 //public static CreativeModeTab ENTITY_TAB;

	 @SubscribeEvent
	  public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event)
	    {
	        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
	        {
	            event.accept(ItemInit.SNOWMAN);
	            event.accept(ItemInit.GINGERBREAD);
	            event.accept(ItemInit.ELF);
	            
	        }
	        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
	        {
	            event.accept(ItemInit.CANDY_CLUMP);
	            event.accept(ItemInit.CANDY_FRAGMENT);
	            event.accept(ItemInit.SNOWFLAKE);
	            
	        }
	        if (event.getTabKey() == CreativeModeTabs.COMBAT)
	        {
	            event.accept(ItemInit.CANDY_SWORD);
	            
	        }
	      
	    }
	
	
	

}
