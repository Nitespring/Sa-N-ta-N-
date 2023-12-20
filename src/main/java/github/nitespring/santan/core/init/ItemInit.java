package github.nitespring.santan.core.init;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.item.CandybarGreatsword;
import github.nitespring.santan.common.item.YuleFoods;
import github.nitespring.santan.core.enums.YuleTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			 SaNtaNMod.MODID);
	
	
	//Items
	public static final RegistryObject<Item> SNOWMAN = ITEMS.register("evil_snowman_spawn_egg", 
			() -> new ForgeSpawnEggItem(EntityInit.SNOWMAN, 14283506, 16737400, new Item.Properties()));
	public static final RegistryObject<Item> GINGERBREAD = ITEMS.register("gingerbread_man_spawn_egg", 
			() -> new ForgeSpawnEggItem(EntityInit.GINGERBREAD, 5253403, 16729088, new Item.Properties()));
	public static final RegistryObject<Item> ELF = ITEMS.register("elf_spawn_egg", 
			() -> new ForgeSpawnEggItem(EntityInit.ELF, 12124160, 1142289, new Item.Properties()));
	
	public static final RegistryObject<Item> SNOWFLAKE = ITEMS.register("snowflake", 
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CANDY_FRAGMENT = ITEMS.register("candy_fragment", 
			() -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(YuleFoods.CANDY)));
	public static final RegistryObject<Item> CANDY_CLUMP = ITEMS.register("candy_clump", 
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CANDYBAR = ITEMS.register("candybar", 
			() -> new Item(new Item.Properties()));
	
	public static final RegistryObject<CandybarGreatsword> CANDY_SWORD = ITEMS.register("candy_sword", 
			() -> new CandybarGreatsword(YuleTiers.CANDY, 3, -2.5F, new Item.Properties()));
	

}
