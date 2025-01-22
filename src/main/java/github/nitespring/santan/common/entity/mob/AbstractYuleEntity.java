package github.nitespring.santan.common.entity.mob;

import java.util.EnumSet;
import java.util.UUID;

import javax.annotation.Nullable;

import github.nitespring.santan.common.entity.util.DamageHitboxEntity;
import github.nitespring.santan.core.tags.CustomBiomeTags;
import github.nitespring.santan.core.tags.CustomBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractYuleEntity extends PathfinderMob{

	protected int hitStunTicks=0;
	public abstract boolean isBoss();

	private static final EntityDataAccessor<Integer> ANIMATION_TICK = SynchedEntityData.defineId(AbstractYuleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(AbstractYuleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(AbstractYuleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(AbstractYuleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TEAM = SynchedEntityData.defineId(AbstractYuleEntity.class, EntityDataSerializers.INT);
	private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS));
	
	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerUUID;
	
	public AbstractYuleEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
		super(p_21683_, p_21684_);
		
	}
	public int getAnimationTick() {return this.entityData.get(ANIMATION_TICK);}
	public void setAnimationTick(int anim) {this.entityData.set(ANIMATION_TICK, anim);}
	public void increaseAnimationTick(int i) {setAnimationTick(getAnimationTick()+1);}
	public void increaseAnimationTick() {increaseAnimationTick(1);}
	public void resetAnimationTick() {setAnimationTick(0);}
	public int getAnimationState() {return this.entityData.get(ANIMATION_STATE);}
	public void setAnimationState(int anim) {this.entityData.set(ANIMATION_STATE, anim);}
	 
	public int getCombatState() {return this.entityData.get(COMBAT_STATE);}
	public void setCombatState(int anim) {this.entityData.set(COMBAT_STATE, anim);}
	 
	public int getEntityState() {return this.entityData.get(ENTITY_STATE);}
	public void setEntityState(int anim) {this.entityData.set(ENTITY_STATE, anim);}
	 
	public int getYuleTeam() {return this.entityData.get(TEAM);}
	public void setYuleTeam(int anim) {this.entityData.set(TEAM, anim);}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ANIMATION_TICK, 0);
		builder.define(ANIMATION_STATE, 0);
		builder.define(COMBAT_STATE, 0);
		builder.define(ENTITY_STATE, 0);
		builder.define(TEAM, getYuleDefaultTeam());
	}

	
	protected abstract int getYuleDefaultTeam();

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setAnimationState(tag.getInt("AnimationId"));
		this.setCombatState(tag.getInt("CombatStateId"));
		this.setEntityState(tag.getInt("EntityStateId"));
		this.setYuleTeam(tag.getInt("YuleTeam"));
		if (tag.hasUUID("Owner")) {
	         this.ownerUUID = tag.getUUID("Owner");
	      }
		}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("AnimationId", this.getAnimationState());
		tag.putInt("CombatStateId", this.getCombatState());
		tag.putInt("EntityStateId", this.getEntityState());
		tag.putInt("YuleTeam", this.getYuleTeam());
		if (this.ownerUUID != null) {
	    	  tag.putUUID("Owner", this.ownerUUID);
	      }	
		}
	
	 @Nullable
	   public LivingEntity getOwner() {
	      return this.owner;
	   }
	
	 
	 public void setOwner(LivingEntity p_33995_) {
	      this.owner = p_33995_;
	      this.ownerUUID= p_33995_.getUUID();
	   }
	 
	 @Override
	protected boolean shouldDespawnInPeaceful() {

		return true;
	}

	
	 
	 @Override
		public void startSeenByPlayer(ServerPlayer p_184178_1_) {
		      super.startSeenByPlayer(p_184178_1_);
		      
		      if (this.isBoss()) {
		      this.bossEvent.addPlayer(p_184178_1_);
		      }
		   }
	 
     @Override
	 public void stopSeenByPlayer(ServerPlayer p_184203_1_) {
    	
		      super.stopSeenByPlayer(p_184203_1_);
		      this.bossEvent.removePlayer(p_184203_1_);
		   } 
		
     @Override
     public void tick() {
	          float h = this.getHealth(); 
	          this.bossEvent.setProgress(h / this.getMaxHealth());
	 
	          if(hitStunTicks>=-1) {
	     		 hitStunTicks--;
	     	 }
	          super.tick(); 
     		}
     
     @SuppressWarnings("deprecation")
	 @Override
	 public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		 spawnGroupData=super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
		 if (!this.getPersistentData().contains("YuleTeam")||getPersistentData().getInt("YuleTeam")==0) {
			 this.setYuleTeam(this.getYuleDefaultTeam());
		 }
		 if (this.owner != null) {
			 if (this.owner.getPersistentData().contains("YuleTeam")) {
				 this.setYuleTeam(this.owner.getPersistentData().getInt("YuleTeam"));
			 } else if (this.owner instanceof Player) {
				 this.setYuleTeam(4);
			 }
		 }
		 return spawnGroupData;
	 }
     
     @Override
     public boolean isAlliedTo(Entity e) {
     	if(this.getOwner()!=null) {
     		return this.getOwner().isAlliedTo(e);
     	}else {
			int teamOwner = this.getYuleTeam();
			if(e instanceof AbstractYuleEntity mob && mob.getYuleTeam()== this.getYuleTeam()){
				return true;
			}else if (e.getPersistentData().contains("YuleTeam") && teamOwner == e.getPersistentData().getInt("YuleTeam")) {
				return true;
			} else {
				return super.isAlliedTo(e);
			}
     	}
     }
	@Override
	public boolean ignoreExplosion(Explosion explosion) {

		return explosion.getDirectSourceEntity() instanceof AbstractYuleEntity||explosion.getIndirectSourceEntity() instanceof AbstractYuleEntity;
	}
     
     @Override
 	public boolean hurt(DamageSource source, float f) {
    	 Entity e = source.getEntity();
 		if(f>0 && (e != null && !(e instanceof DamageHitboxEntity && ((DamageHitboxEntity)e).getOwner() == this))) {
 		 if(hitStunTicks<=0) {
 		 hitStunTicks=5;
 		 }
 		 }
 		 
 		 
 		return super.hurt(source, f);
 	}
     
     public class CopyOwnerTargetGoal extends TargetGoal {
	      private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

	      public CopyOwnerTargetGoal(PathfinderMob p_34056_) {
	         super(p_34056_, false);
	      }

	      public boolean canUse() {
	         return AbstractYuleEntity.this.getOwner() != null && AbstractYuleEntity.this.getOwner() instanceof Mob && ((Mob)AbstractYuleEntity.this.getOwner()).getTarget() != null && this.canAttack(((Mob)AbstractYuleEntity.this.getOwner()).getTarget(), this.copyOwnerTargeting);
	      }

	      public void start() {
	    	  AbstractYuleEntity.this.setTarget(((Mob)AbstractYuleEntity.this.getOwner()).getTarget());
	         super.start();
	      }
	   } 
     
     public static boolean checkSnowMonsterSpawnRules(EntityType<? extends AbstractYuleEntity> p_219014_, ServerLevelAccessor level, MobSpawnType p_219016_, BlockPos pos, RandomSource p_219018_) {
         return level.getDifficulty() != Difficulty.PEACEFUL
				 && (isDarkEnoughToSpawn(level, pos, p_219018_) || (level.getLevel().isRaining() && level.canSeeSky(pos)))
				 && checkSnowMobSpawnRules(p_219014_, level, p_219016_, pos, p_219018_);
      }

	public static boolean checkSnowAnyLightMonsterSpawnRules(EntityType<? extends AbstractYuleEntity> p_219020_, LevelAccessor p_219021_, MobSpawnType p_219022_, BlockPos p_219023_, RandomSource p_219024_) {
		return p_219021_.getDifficulty() != Difficulty.PEACEFUL
			 && checkSnowMobSpawnRules(p_219020_, p_219021_, p_219022_, p_219023_, p_219024_);
	}

	public static boolean checkSnowMobSpawnRules(EntityType<? extends AbstractYuleEntity> e, LevelAccessor level, MobSpawnType p_217060_, BlockPos pos, RandomSource r) {
		BlockPos blockpos = pos.below();
		return (level.getBlockState(blockpos).is(CustomBlockTags.SPAWN_SNOW_ENEMIES) || level.getBiome(pos).is(CustomBiomeTags.SPAWN_SNOW_ENEMIES_DAFAULT))
				&& (p_217060_ == MobSpawnType.SPAWNER || level.getBlockState(blockpos).isValidSpawn(level, blockpos, e));
	}
      
      
	public static boolean isDarkEnoughToSpawn(ServerLevelAccessor p_33009_, BlockPos p_33010_, RandomSource p_33011_) {
		if (p_33009_.getBrightness(LightLayer.SKY, p_33010_) > p_33011_.nextInt(32)) {
		 	return false;
		} else if (p_33009_.getBrightness(LightLayer.BLOCK, p_33010_) > 0) {
		 	return false;
		} else {
		 int i = p_33009_.getLevel().isThundering() ? p_33009_.getMaxLocalRawBrightness(p_33010_, 10) : p_33009_.getMaxLocalRawBrightness(p_33010_);
		 	return i <= p_33011_.nextInt(8);
		}
	}
      
      @Override
		protected void registerGoals() {
			
    	  this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
    	  this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    	  this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, true));
    	  
			this.goalSelector.addGoal(8, new AbstractYuleEntity.LookAtPlayerGoal(this, LivingEntity.class, 1.0F));
		      this.goalSelector.addGoal(8, new AbstractYuleEntity.RandomLookAroundGoal(this));
		
		      this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
		      
		      this.targetSelector.addGoal(2, new AbstractYuleEntity.CopyOwnerTargetGoal(this));

		      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	     
		      this.goalSelector.addGoal(3, new AbstractYuleEntity.WaterAvoidingRandomStrollGoal(this, 0.8D));
		      
		}

	public class WaterAvoidingRandomStrollGoal extends RandomStrollGoal {
		public static final float PROBABILITY = 0.001F;
		protected final float probability;

		public WaterAvoidingRandomStrollGoal(AbstractYuleEntity mob, double speedModifier) {
			this(mob, speedModifier, 0.001F);
		}

		public WaterAvoidingRandomStrollGoal(AbstractYuleEntity mob, double speedModifier, float probability) {
			super(mob, speedModifier);
			this.probability = probability;
		}

		@Override
		public boolean canUse() {
			if(((AbstractYuleEntity)mob).getAnimationState()!=0){
				return false;
			}else {
				return super.canUse();
			}
		}

		@Override
		public boolean canContinueToUse() {
			if(((AbstractYuleEntity)mob).getAnimationState()!=0){
				return false;
			}else {
				return super.canContinueToUse();
			}
		}

		@Nullable
		@Override
		protected Vec3 getPosition() {
			if (this.mob.isInWaterOrBubble()) {
				Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
				return vec3 == null ? super.getPosition() : vec3;
			} else {
				return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
			}
		}
	}
	public class RandomLookAroundGoal extends Goal {
		private final AbstractYuleEntity mob;
		private double relX;
		private double relZ;
		private int lookTime;

		public RandomLookAroundGoal(AbstractYuleEntity mob) {
			this.mob = mob;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			if(mob.getAnimationState()!=0){
				return false;
			}else {
				return this.mob.getRandom().nextFloat() < 0.02F;
			}
		}

		@Override
		public boolean canContinueToUse() {
			if(mob.getAnimationState()!=0){
				return false;
			}else {
				return this.lookTime >= 0;
			}
		}

		@Override
		public void start() {
			double d0 = (Math.PI * 2) * this.mob.getRandom().nextDouble();
			this.relX = Math.cos(d0);
			this.relZ = Math.sin(d0);
			this.lookTime = 20 + this.mob.getRandom().nextInt(20);
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			this.lookTime--;
			this.mob.getLookControl().setLookAt(this.mob.getX() + this.relX, this.mob.getEyeY(), this.mob.getZ() + this.relZ);
		}
	}
	public class LookAtPlayerGoal extends Goal {
		public static final float DEFAULT_PROBABILITY = 0.02F;
		protected final AbstractYuleEntity mob;
		@Nullable
		protected Entity lookAt;
		protected final float lookDistance;
		private int lookTime;
		protected final float probability;
		private final boolean onlyHorizontal;
		protected final Class<? extends LivingEntity> lookAtType;
		protected final TargetingConditions lookAtContext;

		public LookAtPlayerGoal(AbstractYuleEntity mob, Class<? extends LivingEntity> lookAtType, float lookDistance) {
			this(mob, lookAtType, lookDistance, 0.02F);
		}

		public LookAtPlayerGoal(AbstractYuleEntity mob, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability) {
			this(mob, lookAtType, lookDistance, probability, false);
		}

		public LookAtPlayerGoal(AbstractYuleEntity mob, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability, boolean onlyHorizontal) {
			this.mob = mob;
			this.lookAtType = lookAtType;
			this.lookDistance = lookDistance;
			this.probability = probability;
			this.onlyHorizontal = onlyHorizontal;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
			if (lookAtType == Player.class) {
				this.lookAtContext = TargetingConditions.forNonCombat()
						.range((double)lookDistance)
						.selector(p_25531_ -> EntitySelector.notRiding(mob).test(p_25531_));
			} else {
				this.lookAtContext = TargetingConditions.forNonCombat().range((double)lookDistance);
			}
		}

		@Override
		public boolean canUse() {
			if(mob.getAnimationState()!=0&&this.mob.getTarget()==null){
				return false;
			}else if (this.mob.getRandom().nextFloat() >= this.probability) {
				return false;
			} else {
				if (this.mob.getTarget() != null) {
					this.lookAt = this.mob.getTarget();
				}

				if (this.lookAtType == Player.class) {
					this.lookAt = this.mob.level().getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
				} else {
					this.lookAt = this.mob
							.level()
							.getNearestEntity(
									this.mob
											.level()
											.getEntitiesOfClass(
													this.lookAtType,
													this.mob.getBoundingBox().inflate((double)this.lookDistance, 3.0, (double)this.lookDistance),
													p_148124_ -> true
											),
									this.lookAtContext,
									this.mob,
									this.mob.getX(),
									this.mob.getEyeY(),
									this.mob.getZ()
							);
				}

				return this.lookAt != null;
			}
		}

		@Override
		public boolean canContinueToUse() {
			if(mob.getAnimationState()!=0&&this.mob.getTarget()==null){
				return false;
			}else if (!this.lookAt.isAlive()) {
				return false;
			} else {
				return this.mob.distanceToSqr(this.lookAt) > (double)(this.lookDistance * this.lookDistance) ? false : this.lookTime > 0;
			}
		}

		@Override
		public void start() {
			this.lookTime = this.adjustedTickDelay(40 + this.mob.getRandom().nextInt(40));
		}

		@Override
		public void stop() {
			this.lookAt = null;
		}

		@Override
		public void tick() {
			if (this.lookAt.isAlive()) {
				double d0 = this.onlyHorizontal ? this.mob.getEyeY() : this.lookAt.getEyeY();
				this.mob.getLookControl().setLookAt(this.lookAt.getX(), d0, this.lookAt.getZ());
				this.lookTime--;
			}
		}
	}
}
