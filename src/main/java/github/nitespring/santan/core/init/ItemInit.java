package github.nitespring.santan.core.init;

import github.nitespring.santan.SaNtaNMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			 SaNtaNMod.MODID);
	
	public static final RegistryObject<Item> SNOWMAN = ITEMS.register("evil_snowman_spawn_egg", 
			() -> new ForgeSpawnEggItem(EntityInit.SNOWMAN, 14283506, 8496292, new Item.Properties()));

}
