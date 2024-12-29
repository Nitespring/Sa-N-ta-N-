package github.nitespring.santan.core.tags;

import github.nitespring.santan.SaNtaNMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public final class CustomBiomeTags {

    public static final TagKey<Biome> SPAWN_SNOW_ENEMIES_DAFAULT = create("spawn_snow_enemies_default");

    private CustomBiomeTags() {
    }


    private static TagKey<Biome> create(String p_203847_) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID , p_203847_));
    }
    /*public static TagKey<Block> create(ResourceLocation name) {
        return TagKey.create(Registries.BLOCK, name);
    }*/


}
