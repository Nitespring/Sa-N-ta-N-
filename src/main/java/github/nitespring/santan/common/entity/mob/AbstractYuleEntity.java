package github.nitespring.santan.common.entity.mob;

import java.util.UUID;

import javax.annotation.Nullable;

import github.nitespring.santan.common.entity.util.DamageHitboxEntity;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class AbstractYuleEntity extends PathfinderMob{

	protected int hitStunTicks=0;
	public abstract boolean isBoss();
	
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
			}else if (e.getPersistentData().contains("DSTeam") && teamOwner == e.getPersistentData().getInt("DSTeam")) {
				return true;
			} else {
				return super.isAlliedTo(e);
			}
     	}
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
     
     public static boolean checkYuleMonsterSpawnRules(EntityType<? extends AbstractYuleEntity> p_219014_, ServerLevelAccessor p_219015_, MobSpawnType p_219016_, BlockPos p_219017_, RandomSource p_219018_) {
         return p_219015_.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(p_219015_, p_219017_, p_219018_) && checkYuleMobSpawnRules(p_219014_, p_219015_, p_219016_, p_219017_, p_219018_);
      }

      public static boolean checkYuleAnyLightMonsterSpawnRules(EntityType<? extends AbstractYuleEntity> p_219020_, LevelAccessor p_219021_, MobSpawnType p_219022_, BlockPos p_219023_, RandomSource p_219024_) {
         return p_219021_.getDifficulty() != Difficulty.PEACEFUL && checkYuleMobSpawnRules(p_219020_, p_219021_, p_219022_, p_219023_, p_219024_);
      }

      public static boolean checkYuleMobSpawnRules(EntityType<? extends AbstractYuleEntity> p_217058_, LevelAccessor p_217059_, MobSpawnType p_217060_, BlockPos p_217061_, RandomSource p_217062_) {
          BlockPos blockpos = p_217061_.below();
          return p_217060_ == MobSpawnType.SPAWNER || p_217059_.getBlockState(blockpos).isValidSpawn(p_217059_, blockpos, p_217058_);
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
    	  
			this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, LivingEntity.class, 1.0F));
		      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		
		      this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
		      
		      this.targetSelector.addGoal(2, new AbstractYuleEntity.CopyOwnerTargetGoal(this));

		      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	     
		      this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		      
		}
	
}
