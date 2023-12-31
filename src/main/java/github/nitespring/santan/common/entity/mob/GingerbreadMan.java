package github.nitespring.santan.common.entity.mob;

import java.util.EnumSet;
import java.util.Random;

import javax.annotation.Nullable;

import github.nitespring.santan.common.entity.util.DamageHitboxEntity;
import github.nitespring.santan.core.init.EntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class GingerbreadMan extends AbstractYuleEntity implements GeoEntity{
	
	protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	protected int animationTick = 0;
	protected Vec3 aimVec;
	
	public GingerbreadMan(EntityType<? extends AbstractYuleEntity> p_33002_, Level p_33003_) {
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
		data.add(new AnimationController<>(this, "main_controller", 4, this::predicate));
		data.add(new AnimationController<>(this, "stun_controller", 2, this::hitStunPredicate));
		data.add(new AnimationController<>(this, "rotation_controller", 0, this::rotationPredicate));
		}
	
	private <E extends GeoAnimatable> PlayState rotationPredicate(AnimationState<E> event) {
		int animState = this.getAnimationState();
		
			switch(animState) {
			case 23:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.gingerbread_man.attack3_rotation"));
				break;
			case 24:
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.gingerbread_man.attack3_continue_rotation"));
				break;
			default:
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.gingerbread_man.null"));
				break;
			}
		
		
        return PlayState.CONTINUE;
	}
	
	private <E extends GeoAnimatable> PlayState hitStunPredicate(AnimationState<E> event) {
		
		if(hitStunTicks>0) {
		event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.gingerbread_man.hit"));
		}else {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.gingerbread_man.null"));	
		}
		return PlayState.CONTINUE;
	}
	
	private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
		int animState = this.getAnimationState();
		if(this.isDeadOrDying()) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.gingerbread_man.death"));
		}else {
			switch(animState) {
			case 21:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.gingerbread_man.attack"));
				break;
			case 22:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.gingerbread_man.attack2"));
				break;
			case 23:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.gingerbread_man.attack3"));
				break;
			case 24:
				event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.gingerbread_man.attack3_continue"));
				break;
			case 25:
				event.getController().setAnimation(RawAnimation.begin().thenPlay("animation.gingerbread_man.attack4"));
				break;

			default:
				 if(!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F)){
					 event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.gingerbread_man.walk"));	 
					 }else {
					 event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.gingerbread_man.idle"));
				 }
				break;
			}
		}
		
        return PlayState.CONTINUE;
	}
	
	@Override
	protected void registerGoals() {
		
		this.goalSelector.addGoal(1, new GingerbreadMan.AttackGoal(this));
		
	

	      super.registerGoals();
	      
	}

	 public static  AttributeSupplier.Builder setCustomAttributes(){
			return Monster.createMonsterAttributes()
					.add(Attributes.MAX_HEALTH, 50.0D)
					.add(Attributes.MOVEMENT_SPEED, 0.20D)
					.add(Attributes.ATTACK_DAMAGE, 8.0D)
					.add(Attributes.ATTACK_SPEED, 1.2D)
					.add(Attributes.ATTACK_KNOCKBACK, 0.3D)
					.add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
					.add(Attributes.FOLLOW_RANGE, 35);
	
		  }
	 
	 
	 @Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_,
			MobSpawnType p_21436_, SpawnGroupData p_21437_, CompoundTag p_21438_) {
		
		
		 
		return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
	}
	 

	
	
	 @Override
		public void tick() {
			if(this.getAnimationState()!=0&&!this.isDeadOrDying()) {
			this.playAnimation();
			}
			super.tick();
		}
	 
	 
	 protected void playAnimation() {
		 animationTick++;
			this.getNavigation().stop();
			switch(this.getAnimationState()) {
			//Attack
			case 21:
				if(animationTick==15) {

						
						DamageHitboxEntity h = new DamageHitboxEntity(EntityInit.HITBOX.get(), level(), 
						this.position().add((1.5f)*this.getLookAngle().x,
											0.25,
											(1.5f)*this.getLookAngle().z), 
						(float)this.getAttributeValue(Attributes.ATTACK_DAMAGE), 5);
						h.setOwner(this);
						this.level().addFreshEntity(h);
						
				
				}
				if(animationTick>=34) {
					animationTick=0;
					setAnimationState(22);
				}
				break;

			 case 22:
					if(animationTick==15) {
		
							
							DamageHitboxEntity h = new DamageHitboxEntity(EntityInit.HITBOX.get(), level(), 
							this.position().add((1.5f)*this.getLookAngle().x,
												0.25,
												(1.5f)*this.getLookAngle().z), 
							(float)this.getAttributeValue(Attributes.ATTACK_DAMAGE), 5);
							h.setOwner(this);
							this.level().addFreshEntity(h);
							
					
					}
					if(animationTick>=38) {
						animationTick=0;
						setAnimationState(0);
					}
					break;
				
			case 23:
				if(animationTick>=15) {
			
						
						DamageHitboxEntity h = new DamageHitboxEntity(EntityInit.HITBOX.get(), level(), 
						this.position().add((1.5f)*this.getLookAngle().x,
											0.25,
											(1.5f)*this.getLookAngle().z), 
						(float)this.getAttributeValue(Attributes.ATTACK_DAMAGE)+2, 5);
						h.setOwner(this);
						this.level().addFreshEntity(h);
						if(this.getTarget()!=null) {
							this.aimVec= this.getTarget().position().add(this.position().scale(-1.0));
						}else{
					        this.aimVec=this.getLookAngle();
						}
						animationTick=0;
						setAnimationState(24);
				}
				break;
			case 24:
				if(this.aimVec!=null) {
					this.setDeltaMovement(this.aimVec.normalize().scale(0.4));
				}else {
					this.setDeltaMovement(this.getLookAngle().normalize().scale(0.4));
				}	
				/*
				if(this.getTarget()!=null&&this.aimVec!=this.getTarget().position().add(this.position().scale(-1.0))) {
					Vec3 aim = this.getTarget().position().add(this.position().scale(-1.0)).normalize();
					Vec3 mov = this.aimVec.normalize();
					this.aimVec=mov.add((aim.x - mov.x)/(Math.abs(aim.x - mov.x)) * 0.2D, (aim.y - mov.y)/(Math.abs(aim.y - mov.y)) * 0.2D, (aim.z - mov.z)/(Math.abs(aim.z - mov.z)) * 0.2D);
				}
				*/
				//if(this.getTarget()!=null) {this.getLookControl().setLookAt(this.getTarget(), 80.0F, 80.0F);}
				
				if((animationTick-5)%10==0) {
			
						
						DamageHitboxEntity h = new DamageHitboxEntity(EntityInit.HITBOX.get(), level(), 
						this.position().add((0.25f)*this.getLookAngle().x,
											0.25,
											(0.25f)*this.getLookAngle().z), 
						(float)this.getAttributeValue(Attributes.ATTACK_DAMAGE)-4, 5);
						h.setOwner(this);
						this.level().addFreshEntity(h);
						
				
				}
				if(animationTick>=57) {
					animationTick=0;
					setAnimationState(0);
				}
				break;
			case 25:
				if(animationTick==10) {
			
						
						DamageHitboxEntity h = new DamageHitboxEntity(EntityInit.HITBOX.get(), level(), 
						this.position().add((1.5f)*this.getLookAngle().x,
											0.25,
											(1.5f)*this.getLookAngle().z), 
						(float)this.getAttributeValue(Attributes.ATTACK_DAMAGE)+2, 5);
						h.setOwner(this);
						this.level().addFreshEntity(h);
						
				
				}
				if(animationTick>=18) {
					animationTick=0;
					setAnimationState(0);
				}
				break;
			}
		 
		 
	 }
	 
	 
	 @Override
	public boolean hurt(DamageSource source, float f) {
		 
		float width = this.getBbWidth() * 0.5f;
		float height = this.getBbHeight() * 0.5f;
		for (int i = 0; i < 6; ++i) {
				
			Vec3 off = new Vec3(new Random().nextDouble() * width - width / 2, new Random().nextDouble() * height - height / 2,
					new Random().nextDouble() * width - width / 2);
		if(this.level() instanceof ServerLevel) {
			((ServerLevel) this.level()).sendParticles( new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BROWN_CONCRETE.defaultBlockState()), 
					this.position().x+new Random().nextDouble()-0.5, 
					this.position().y+new Random().nextDouble()*2-0.75, 
					this.position().z+new Random().nextDouble()-0.5, 
					7,  
					off.x, 
					off.y + 1.0D, 
					off.z, 0.05D);
			}
		}
		return super.hurt(source, f);
	}
	 
	 
	 @Nullable
	   protected SoundEvent getAmbientSound() {
	      return SoundEvents.ZOMBIE_AMBIENT;
	   }

	   @Nullable
	   protected SoundEvent getHurtSound(DamageSource p_29929_) {
	      return SoundEvents.ZOMBIE_HURT;
	   }

	   @Nullable
	   protected SoundEvent getDeathSound() {
	      return SoundEvents.ZOMBIE_DEATH;
	   }
	 

	   @Override
	protected void tickDeath() {
		   ++this.deathTime;
		      if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
		         this.level().broadcastEntityEvent(this, (byte)60);
		         this.remove(Entity.RemovalReason.KILLED);
		      }
	}
	   

	public class AttackGoal extends Goal{

			
		   private final double speedModifier = 1.1f;
		   private final boolean followingTargetEvenIfNotSeen = true;
		   protected final GingerbreadMan mob;
		   private Path path;
		   private double pathedTargetX;
		   private double pathedTargetY;
		   private double pathedTargetZ;
		   private int ticksUntilNextPathRecalculation;
		   private int ticksUntilNextAttack;
		   private long lastCanUseCheck;
		   private int failedPathFindingPenalty = 0;
		   private boolean canPenalize = false;
		   

		
		
		  public AttackGoal(GingerbreadMan entityIn) {
		      this.mob = entityIn;
		      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
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
			  if(this.mob.getRandom().nextInt(4096)>=4048) {this.mob.setAnimationState(23);}
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
		
		
		
		
		
		
		
		   protected void checkForCloseRangeAttack(double distance, double reach){
			   if (distance <= reach && this.ticksUntilNextAttack <= 0) {
				    int r = this.mob.getRandom().nextInt(2048);
					    if(r<=800) {
					    
					    	this.mob.setAnimationState(21);
					    	
					    }else if(r<=1000){
					    	
					    	this.mob.setAnimationState(23);
					    }else if(r<=1800){
					    	
					    	this.mob.setAnimationState(25);
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


