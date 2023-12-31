package github.nitespring.santan.core.init;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilElf;
import github.nitespring.santan.common.entity.mob.EvilSnowman;
import github.nitespring.santan.common.entity.mob.GingerbreadMan;
import github.nitespring.santan.common.entity.util.DamageHitboxEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
			 SaNtaNMod.MODID);
	
	public static final RegistryObject<EntityType<EvilSnowman>> SNOWMAN = ENTITIES.register("evil_snowman",
			() -> EntityType.Builder.<EvilSnowman>of(EvilSnowman::new, MobCategory.MONSTER)
			.sized(0.8f, 1.9f)
			.build("evil_snowman"));
	public static final RegistryObject<EntityType<EvilElf>> ELF = ENTITIES.register("elf",
			() -> EntityType.Builder.<EvilElf>of(EvilElf::new, MobCategory.MONSTER)
			.sized(0.5f, 1.2f)
			.build("elf"));
	public static final RegistryObject<EntityType<GingerbreadMan>> GINGERBREAD = ENTITIES.register("gingerbread_man",
			() -> EntityType.Builder.<GingerbreadMan>of(GingerbreadMan::new, MobCategory.MONSTER)
			.sized(0.9f, 2.4f)
			.build("gingerbread_man"));
	
	public static final RegistryObject<EntityType<DamageHitboxEntity>> HITBOX = ENTITIES.register("hitbox",
			() -> EntityType.Builder.<DamageHitboxEntity>of(DamageHitboxEntity::new, MobCategory.MISC)
			.sized(1.5f, 1.5f)
			.build("hitbox"));

}
