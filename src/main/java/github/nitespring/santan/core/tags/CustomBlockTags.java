package github.nitespring.santan.core.tags;

import github.nitespring.santan.SaNtaNMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class CustomBlockTags {

    public static final TagKey<Block> SPAWN_SNOW_ENEMIES = create("spawn_snow_enemies");
    public static final TagKey<Block> SNOW_BREAKABLE = create("snow_breakable");
    public static final TagKey<Block> SNOW_BREAKABLE_1 = create("snow_breakable_1");

    private CustomBlockTags() {
    }


    private static TagKey<Block> create(String p_203847_) {
        return TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID , p_203847_));
    }

    /*public static TagKey<Block> create(ResourceLocation name) {
        return TagKey.create(Registries.BLOCK, name);
    }*/


}
