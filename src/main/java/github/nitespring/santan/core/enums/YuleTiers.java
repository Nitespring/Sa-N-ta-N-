package github.nitespring.santan.core.enums;

import java.util.function.Supplier;

import github.nitespring.santan.core.init.ItemInit;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum YuleTiers implements Tier {
   CANDY(4, 2031, 9.0F, 4.0F, 15, () -> {
      return Ingredient.of(ItemInit.CANDY_CLUMP.get());
   });

   private final int level;
   private final int uses;
   private final float speed;
   private final float damage;
   private final int enchantmentValue;
   private final LazyLoadedValue<Ingredient> repairIngredient;

   private YuleTiers(int p_43332_, int p_43333_, float p_43334_, float p_43335_, int p_43336_, Supplier<Ingredient> p_43337_) {
      this.level = p_43332_;
      this.uses = p_43333_;
      this.speed = p_43334_;
      this.damage = p_43335_;
      this.enchantmentValue = p_43336_;
      this.repairIngredient = new LazyLoadedValue<>(p_43337_);
   }

   public int getUses() {
      return this.uses;
   }

   public float getSpeed() {
      return this.speed;
   }

   public float getAttackDamageBonus() {
      return this.damage;
   }

   public int getLevel() {
      return this.level;
   }

   public int getEnchantmentValue() {
      return this.enchantmentValue;
   }

   public Ingredient getRepairIngredient() {
      return this.repairIngredient.get();
   }

   
}