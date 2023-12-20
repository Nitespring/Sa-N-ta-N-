package github.nitespring.santan.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;


public class YuleFoods extends Foods{
	 @SuppressWarnings("deprecation")
	public static final FoodProperties CANDY = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F)
	.effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 0), 1.0F)
	.effect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0), 1.0F)
	.effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0), 1.0F).alwaysEat().build();
}
