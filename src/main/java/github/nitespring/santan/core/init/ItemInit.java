package github.nitespring.santan.core.init;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.core.enums.YuleTiers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			 SaNtaNMod.MODID);
	
	public static final RegistryObject<Item> SNOWMAN = ITEMS.register("evil_snowman_spawn_egg", 
			() -> new ForgeSpawnEggItem(EntityInit.SNOWMAN, 14283506, 16737400, new Item.Properties()));
	
	public static final RegistryObject<Item> SNOWFLAKE = ITEMS.register("snowflake", 
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CANDY_FRAGMENT = ITEMS.register("candy_fragment", 
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CANDY_CLUMP = ITEMS.register("candy_clump", 
			() -> new Item(new Item.Properties()));
	
	public static final RegistryObject<SwordItem> CANDY_SWORD = ITEMS.register("candy_sword", 
			() -> new SwordItem(YuleTiers.CANDY, 3, -2.5F, new Item.Properties()));
	

}
