package github.nitespring.santan.common.entity.mob;

import github.nitespring.santan.common.entity.util.DamageHitboxEntity;
import github.nitespring.santan.core.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;


public class Tree extends AbstractYuleEntity implements GeoEntity{

	protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	 

	public Tree(EntityType<? extends AbstractYuleEntity> e, Level l) {
		super(e, l);
		this.noCulling = true;
	}
	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {return this.factory;}
	@Override
	public boolean isBoss() {return false;}
	@Override
	protected int getYuleDefaultTeam() {return 0;}
	
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>(this, "main_controller", 4, this::predicate));
		data.add(new AnimationController<>(this, "stun_controller", 2, this::hitStunPredicate));
		}
	
	private <E extends GeoAnimatable> PlayState hitStunPredicate(AnimationState<E> event) {
		
		if(hitStunTicks>0) {
		event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.hit"));
		}else {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tree.new"));
		}
		return PlayState.CONTINUE;
	}
	
	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
		int animState = this.getAnimationState();
		if(this.isDeadOrDying()) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.death"));
		}else {
			switch(animState) {
			case 11:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.go_asleep"));
				break;
			case 12:
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tree.sleep"));
				break;
			case 13:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.awake"));
				break;
			case 14:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.fall"));
				break;
			case 15:
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tree.fallen"));
				break;
			case 16:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.get_up"));
				break;
			case 17:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.endure"));
				break;
			case 18:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.stop_endure"));
				break;
			case 21:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.shoot"));
				break;
			case 22:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.run"));
				break;
			case 23:
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tree.trash"));
				break;
			case 24:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.tree.barrage"));
				break;
			default:
				 if(!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F)){
					 event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tree.walk"));
					 }else {
					 event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.tree.idle"));
				 }
				break;
			}
		}
        return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new Tree.AttackGoal(this));
		super.registerGoals();
	}


	@Override
	public void tick() {
		if(this.getAnimationState()!=0&&!this.isDeadOrDying()) {
			this.playAnimation();
		}/*else{
			if(this.tickCount%5==0) {
				if (this.getTarget() == null && !this.isAggressive()) {
					int r = new Random().nextInt(2047);
					if (r <= 4) {
						setAnimationState(11);
					}
				}
			}
		}*/
		super.tick();
	}



	@Override
	public boolean ignoreExplosion(Explosion explosion) {

		return explosion.getDirectSourceEntity() instanceof AbstractYuleEntity||explosion.getIndirectSourceEntity() instanceof AbstractYuleEntity;
	}

	protected void playAnimation() {
		  increaseAnimationTick();
		  Vec3 pos = this.position();
			switch(this.getAnimationState()) {
				case 11: //Go Asleep
					this.stopInPlace();
					this.getNavigation().stop();
					this.getMoveControl().setWantedPosition(pos.x,pos.y,pos.z,0);
					this.getLookControl().setLookAt(pos.x,pos.y+1,pos.z);
					if (getAnimationTick() >= 12) {
						resetAnimationTick();
						setAnimationState(12);
					}
					break;
				case 12: //Sleep
					int r = new Random().nextInt(2047);
					this.stopInPlace();
					this.getNavigation().stop();
					this.getMoveControl().setWantedPosition(pos.x,pos.y,pos.z,0);
					this.getLookControl().setLookAt(pos.x,pos.y+1,pos.z);
					if ((this.getTarget()!=null&&this.isAggressive())||(r <= 3 && getAnimationTick() >=511)) {
						resetAnimationTick();
						setAnimationState(13);
					}
					break;
				case 13: //Wake Up
					if (getAnimationTick() >= 18) {
						resetAnimationTick();
						setAnimationState(0);
					}
					break;
				case 14: //Fall
					if (getAnimationTick() >= 10) {
						resetAnimationTick();
						setAnimationState(15);
					}
					break;
				case 15: //Fallen
					this.stopInPlace();
					if (getAnimationTick() >= 56) {
						int r1 = new Random().nextInt(511);
						if(r1<=63) {
							resetAnimationTick();
							setAnimationState(16);
						}
					}
					break;
				case 16: //Get Up
					if (getAnimationTick() >= 18) {
						resetAnimationTick();
						setAnimationState(0);
					}
					break;
				case 17: //Endure
					if (getAnimationTick() >= 12) {
						resetAnimationTick();
						setAnimationState(24);
					}
					break;
				case 18: //Stop Endure
					if (getAnimationTick() >= 12) {
						resetAnimationTick();
						setAnimationState(0);
					}
					break;
				//Attack
				case 21: //Shoot
					if(getAnimationTick()>=8){
						this.stopInPlace();
					}
					if (getAnimationTick() == 16) {
						this.playSound(SoundEvents.GENERIC_EXPLODE.value());
					}
					if (getAnimationTick() == 20) {
						this.rangedAttackLarge();
					}
					if (getAnimationTick() >= 36) {
						resetAnimationTick();
						setAnimationState(0);
					}
					break;
				case 22: //Run
					moveToTarget(1.8f, 10.0f,10.0f);
					if ((getAnimationTick() % 10) == 3) {
						this.playSound(SoundEvents.GENERIC_EXPLODE.value());
						this.rangedAttackSmall();
					}
					if (getAnimationTick() >= 64) {
						int r2 = new Random().nextInt(255);
						if(r2<=15) {
							this.stopInPlace();
							resetAnimationTick();
							setAnimationState(0);
						}else if(r2<=31) {
							this.stopInPlace();
							resetAnimationTick();
							setAnimationState(14);
						} else if(r2<=63) {
							if(this.getTarget()!=null&&this.distanceToSqr(this.getTarget())<=8) {
								this.stopInPlace();
								resetAnimationTick();
								setAnimationState(23);
							}
						}
					}
					break;
				case 23: //Trash
					this.stopInPlace();
					if (getAnimationTick() % 6 == 0) {
						DamageHitboxEntity h = new DamageHitboxEntity(EntityInit.HITBOX_LARGE.get(), level(),
								this.position(),
								(float) this.getAttributeValue(Attributes.ATTACK_DAMAGE), 5);
						h.setOwner(this);
						this.level().addFreshEntity(h);
					}
					if (getAnimationTick() >= 36) {
						int r3 = new Random().nextInt(255);
						if(r3<=63) {
							resetAnimationTick();
							setAnimationState(0);
						}
					}
					break;
				case 24: //Barrage
					this.stopInPlace();
					if (getAnimationTick() == 6||getAnimationTick() == 13||getAnimationTick() == 19
							||getAnimationTick() == 23||getAnimationTick() == 28||getAnimationTick() == 34) {
						this.playSound(SoundEvents.GENERIC_EXPLODE.value());
						this.rangedAttackSmall();

					}
					if (getAnimationTick() >= 44) {
						resetAnimationTick();
						setAnimationState(0);
					}
					break;
			}
	 }

	public void rangedAttackSmall(){}
	public void rangedAttackLarge(){

	}

	public void moveToTarget(float speed, float deltaYaw, float deltaPitch){
		boolean flag = this.getTarget()!=null;
		if(flag) {
			this.getLookControl().setLookAt(this.getTarget(), deltaYaw, deltaPitch);
			Path path = this.getNavigation().createPath(this.getTarget(), 0);
			this.getNavigation().moveTo(path, speed);
		}

	}

	public void doAttack(float dmgFlat, float dmgMull, float range){
		this.playSound(SoundEvents.WOOD_FALL);
		DamageHitboxEntity h = new DamageHitboxEntity(EntityInit.HITBOX.get(), level(),
				this.position().add((range*1.35f) * this.getLookAngle().x,
						0.25,
						(range*1.35f) * this.getLookAngle().z),
				(float) this.getAttributeValue(Attributes.ATTACK_DAMAGE)*dmgMull+dmgFlat, 5);
		h.setOwner(this);
		this.level().addFreshEntity(h);
	}
	
	 
	 @Override
	public boolean hurt(DamageSource source, float f) {
		float damageFinal = f/2;
		if(getAnimationState()==17||getAnimationState()==24){
			damageFinal = f/4;
		}
		 if(source.is(DamageTypeTags.BYPASSES_ARMOR)){
			 damageFinal = f;
		 }
		if(source.getDirectEntity() instanceof LivingEntity attacker && attacker.getMainHandItem().is(ItemTags.AXES)) {
			damageFinal = f*1.2f;
		}
		if(source.is(DamageTypeTags.IS_FIRE)||source.is(DamageTypeTags.BURN_FROM_STEPPING)||source.is(DamageTypeTags.IS_LIGHTNING)){
			damageFinal = f*2;
		}
		 
		float width = this.getBbWidth() * 0.5f;
		float height = this.getBbHeight() * 0.5f;
		for (int i = 0; i < 4; ++i) {
				
			Vec3 off = new Vec3(new Random().nextDouble() * width - width / 2, new Random().nextDouble() * height - height / 2,
					new Random().nextDouble() * width - width / 2);
		if(this.level() instanceof ServerLevel) {
			((ServerLevel) this.level()).sendParticles( new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SPRUCE_LOG.defaultBlockState()),
					this.position().x+new Random().nextDouble()-0.5, 
					this.position().y+new Random().nextDouble()*2-0.75, 
					this.position().z+new Random().nextDouble()-0.5, 
					6,  
					off.x, 
					off.y + 1.0D,
					off.z, 0.05D);
			}
		}
		return super.hurt(source, damageFinal);
	}
	 
	 
	@Nullable
	protected SoundEvent getAmbientSound() {
	  return SoundEvents.WOOD_STEP;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource p_29929_) {
	  return SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
	  return SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR;
	}




	public class AttackGoal extends Goal{

		private final double speedModifier = 1.0f;
		private final boolean followingTargetEvenIfNotSeen = true;
		protected final Tree mob;
		private Path path;
		private double pathedTargetX;
		private double pathedTargetY;
		private double pathedTargetZ;
		private int ticksUntilNextPathRecalculation;
		private int ticksUntilNextAttack;
		private long lastCanUseCheck;
		private int failedPathFindingPenalty = 0;
		private boolean canPenalize = false;




		public AttackGoal(Tree entityIn) {
			this.mob = entityIn;
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}
		
		
		@Override
		public boolean canUse() {
			if(this.mob.getAnimationState()==0) {
			 long i = this.mob.level().getGameTime();
		      if (i - this.lastCanUseCheck < 20L) {
		         return false;
		      } else {
		         this.lastCanUseCheck = i;
		         LivingEntity livingentity = this.mob.getTarget();
		         if (livingentity == null) {
		            return false;
		         } else if (!livingentity.isAlive()) {
		            return false;
		         } else {
		           if (canPenalize) {
		               if (--this.ticksUntilNextPathRecalculation <= 0) {
		                  this.path = this.mob.getNavigation().createPath(livingentity, 0);
		                  this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
		                  return this.path != null;
		               } else {
		                  return true;
		               }
		            }
		            this.path = this.mob.getNavigation().createPath(livingentity, 0);
		            if (this.path != null) {
		               return true;
		            } else {
		               return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
		            }
		         }
		      }
			}else{
				return false;
			}
		}
		
		
		@Override
		public boolean canContinueToUse() {
			LivingEntity livingentity = this.mob.getTarget();
			if(this.mob.getAnimationState()!=0) {
				return false;
			}else if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else if (!this.followingTargetEvenIfNotSeen) {
				return !this.mob.getNavigation().isDone();
			} else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
				return false;
			} else {
				return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
			}
		}
		@Override
		public void start() {
			this.mob.getNavigation().moveTo(this.path, this.speedModifier);
			this.mob.setAggressive(true);
			this.ticksUntilNextPathRecalculation = 0;
			this.ticksUntilNextAttack = 10;

			this.mob.setAnimationState(0);
		}
		@Override
		public void stop() {
			LivingEntity livingentity = this.mob.getTarget();
			if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
				this.mob.setTarget((LivingEntity)null);
			}

			this.mob.getNavigation().stop();
		}
		
		   
			
		   
		@Override
		public void tick() {
			 LivingEntity target = this.mob.getTarget();
		      double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
		      double reach = this.getAttackReachSqr(target);

			  this.doMovement(target, reach);
			  this.checkForCloseRangeAttack(distance, reach);
			  this.checkForLongRangeAttack();
			  this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
			
		}
		
		
		@SuppressWarnings("unused")
		private void checkForPreciseAttack() {
			 if (this.ticksUntilNextAttack <= 0) {
				 
				 this.mob.setAnimationState(42);
			 }
			
		}
		
		
		@SuppressWarnings("unused")
		protected void doMovement(LivingEntity livingentity, Double d0) {
			this.mob.getLookControl().setLookAt(this.mob.getTarget(), 30.0F, 30.0F);
			this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
			   if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
			         this.pathedTargetX = livingentity.getX();
			         this.pathedTargetY = livingentity.getY();
			         this.pathedTargetZ = livingentity.getZ();
			         this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
			         if (this.canPenalize) {
			            this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
			            if (this.mob.getNavigation().getPath() != null) {
			               net.minecraft.world.level.pathfinder.Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
			               if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
			                  failedPathFindingPenalty = 0;
			               else
			                  failedPathFindingPenalty += 10;
			            } else {
			               failedPathFindingPenalty += 10;
			            }
			         }
			         if (d0 > 1024.0D) {
			            this.ticksUntilNextPathRecalculation += 10;
			         } else if (d0 > 256.0D) {
			            this.ticksUntilNextPathRecalculation += 5;
			         }

			         if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
			            this.ticksUntilNextPathRecalculation += 15;
			         }
			      }
				  
			  }



		protected void checkForLongRangeAttack(){
			if (this.ticksUntilNextAttack <= 0) {
				int r = this.mob.getRandom().nextInt(2048);
				if(r<=127) {
					this.mob.setAnimationState(21);
				}else if(r<=255) {
					this.mob.setAnimationState(22);
				}
			}
		}
		
		
		
		protected void checkForCloseRangeAttack(double distance, double reach){
			if (distance <= reach && this.ticksUntilNextAttack <= 0) {
				int r = this.mob.getRandom().nextInt(2048);
				if(r<=511) {
					this.mob.setAnimationState(23);
				}else if(r<=1023) {
					this.mob.setAnimationState(17);
				}
			}
		}
		   

	    
	    
	  
	   

		protected void resetAttackCooldown() {
			this.ticksUntilNextAttack = 20;
		 }
				
		
		  protected double getAttackReachSqr(LivingEntity p_179512_1_) {
		      return (double)(this.mob.getBbWidth() * 3.0F * this.mob.getBbWidth() * 2.0F + p_179512_1_.getBbWidth());
		   }

	}
	 
	 
	
	
}


