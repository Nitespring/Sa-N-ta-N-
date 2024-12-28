package github.nitespring.santan.core.enums;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import github.nitespring.santan.core.init.ItemInit;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public enum YuleTiers implements Tier {
   CANDY(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2031, 9.0F, 4.0F, 15, () -> {
      return Ingredient.of(ItemInit.CANDY_CLUMP.get());
   });


   private final TagKey<Block> incorrectBlocksForDrops;
   private final int uses;
   private final float speed;
   private final float damage;
   private final int enchantmentValue;
   private final Supplier<Ingredient> repairIngredient;

   private YuleTiers(TagKey<Block> pIncorrectBlockForDrops, int pUses, float pSpeed, float pDamage, int pEnchantmentValue, Supplier<Ingredient> pRepairIngredient) {
      this.incorrectBlocksForDrops = pIncorrectBlockForDrops;
      this.uses = pUses;
      this.speed = pSpeed;
      this.damage = pDamage;
      this.enchantmentValue = pEnchantmentValue;
      this.repairIngredient = Suppliers.memoize(pRepairIngredient::get);
   }

   @Override
   public int getUses() {
      return this.uses;
   }

   @Override
   public float getSpeed() {
      return this.speed;
   }

   @Override
   public float getAttackDamageBonus() {
      return this.damage;
   }

   @Override
   public TagKey<Block> getIncorrectBlocksForDrops() {
      return this.incorrectBlocksForDrops;
   }

   @Override
   public int getEnchantmentValue() {
      return this.enchantmentValue;
   }

   @Override
   public Ingredient getRepairIngredient() {
      return this.repairIngredient.get();
   }
   
}