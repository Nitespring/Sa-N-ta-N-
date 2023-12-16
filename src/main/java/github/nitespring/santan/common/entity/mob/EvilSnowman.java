package github.nitespring.santan.common.entity.mob;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class EvilSnowman extends AbstractYuleEntity implements GeoEntity{

	protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	
	public EvilSnowman(EntityType<? extends AbstractYuleEntity> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
		this.noCulling = true;
	}
	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {return this.factory;}
	@Override
	public boolean isBoss() {return false;}
	@Override
	protected int getYuleDefaultTeam() {return 0;}
	
	@Override
	public void registerControllers(ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "main_controller", 3, this::predicate));
		data.add(new AnimationController<>(this, "stun_controller", 0, this::hitStunPredicate));
		}
	
	private <E extends GeoAnimatable> PlayState hitStunPredicate(AnimationState<E> event) {
		
		if(hitStunTicks>0) {
		event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.snowman.hit"));
		}else {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.snowman.new2"));	
		}
		return PlayState.CONTINUE;
	}
	
	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
		int animState = this.getAnimationState();
		if(this.isDeadOrDying()) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.snowman.death"));
		}else {
			switch(animState) {
			case 21:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.snowman.attack"));
				break;
			case 22:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.snowman.attack2"));
				break;
			case 23:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.snowman.attack3"));
				break;
			default:
				 if(!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F)){
					 event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.snowman.walk"));	 
					 }else {
					 event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.snowman.idle"));
				 }
				break;
			}
		}
		
        return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		
		//this.goalSelector.addGoal(1, new EvilSnowman.AttackGoal(this));
		
	

	      super.registerGoals();
	      
	}

	 public static  AttributeSupplier.Builder setCustomAttributes(){
			return Monster.createMonsterAttributes()
					.add(Attributes.MAX_HEALTH, 30.0D)
					.add(Attributes.MOVEMENT_SPEED, 0.25D)
					.add(Attributes.ATTACK_DAMAGE, 4.0D)
					.add(Attributes.ATTACK_SPEED, 1.2D)
					.add(Attributes.ATTACK_KNOCKBACK, 0.1D)
					.add(Attributes.KNOCKBACK_RESISTANCE, 0.4D)
					.add(Attributes.FOLLOW_RANGE, 35);
	
		  }
	
	
	 @Override
		public void tick() {
			if(this.getAnimationState()!=0) {
			this.playAnimation();
			}
			super.tick();
		}
	 
	 
	 protected void playAnimation() {
		 
		 
		 
	 }
	 
	
	
}


