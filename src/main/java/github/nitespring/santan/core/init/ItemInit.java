package github.nitespring.santan.core.init;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.item.CandybarGreatsword;
import github.nitespring.santan.common.item.YuleFoods;
import github.nitespring.santan.core.enums.YuleTiers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM,
			 SaNtaNMod.MODID);
	
	
	//Items
	public static final DeferredHolder<Item,Item> SNOWMAN = ITEMS.register("evil_snowman_spawn_egg",
			() -> new SpawnEggItem(EntityInit.SNOWMAN.get(), 14283506, 16737400, new Item.Properties()));
	public static final DeferredHolder<Item,Item> GINGERBREAD = ITEMS.register("gingerbread_man_spawn_egg", 
			() -> new SpawnEggItem(EntityInit.GINGERBREAD.get(), 5253403, 16729088, new Item.Properties()));
	public static final DeferredHolder<Item,Item> ELF = ITEMS.register("elf_spawn_egg", 
			() -> new SpawnEggItem(EntityInit.ELF.get(), 12124160, 1142289, new Item.Properties()));
	
	public static final DeferredHolder<Item,Item> SNOWFLAKE = ITEMS.register("snowflake", 
			() -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item,Item> CANDY_FRAGMENT = ITEMS.register("candy_fragment", 
			() -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).food(YuleFoods.CANDY)));
	public static final DeferredHolder<Item,Item> CANDY_CLUMP = ITEMS.register("candy_clump", 
			() -> new Item(new Item.Properties()));
	public static final DeferredHolder<Item,Item> CANDYBAR = ITEMS.register("candybar", 
			() -> new Item(new Item.Properties()));
	
	public static final DeferredHolder<Item,CandybarGreatsword> CANDY_SWORD = ITEMS.register("candy_sword", 
			() -> new CandybarGreatsword(YuleTiers.CANDY, 3, -2.5F, new Item.Properties()));
	

}
