package github.nitespring.santan.core.event;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.core.init.ItemInit;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;


@EventBusSubscriber(modid = SaNtaNMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CreativeTabsRegistration {
	
	
	 //public static CreativeModeTab ITEM_TAB;
	 //public static CreativeModeTab ENTITY_TAB;

	 @SubscribeEvent
	  public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event)
	    {
	        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
	        {
	            event.accept(ItemInit.SNOWMAN.get());
	            event.accept(ItemInit.GINGERBREAD.get());
	            event.accept(ItemInit.ELF.get());
	            
	        }
	        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
	        {
	            event.accept(ItemInit.CANDY_CLUMP.get());
	            event.accept(ItemInit.CANDY_FRAGMENT.get());
	            event.accept(ItemInit.SNOWFLAKE.get());
	            
	        }
	        if (event.getTabKey() == CreativeModeTabs.COMBAT)
	        {
	            event.accept(ItemInit.CANDY_SWORD.get());
	            
	        }
	      
	    }
	
	
	

}
