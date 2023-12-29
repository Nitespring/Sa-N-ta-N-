package github.nitespring.santan.common.entity.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DamageHitboxEntity extends Entity{
	
	@Nullable
	private LivingEntity owner;
	@Nullable
	private UUID ownerUUID;
	private int delayTicks = 1;
	private float damage = 4.0f;
	private List<LivingEntity> hitEntities = new ArrayList<LivingEntity>();

	public DamageHitboxEntity(EntityType<?> p_19870_, Level p_19871_) {
		super(p_19870_, p_19871_);
	}

	public DamageHitboxEntity(EntityType<?> p_19870_, Level p_19871_,Vec3 pos, float dmg) {
		super(p_19870_, p_19871_);
		this.setPos(pos);
		this.damage=dmg;
	}
	public DamageHitboxEntity(EntityType<?> p_19870_, Level p_19871_,Vec3 pos, float dmg, int delayTicks) {
		this(p_19870_, p_19871_, pos, dmg);
		this.delayTicks = delayTicks;

	}

	@Override
	protected void defineSynchedData() {
		
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag p_20052_) {
	      if (p_20052_.hasUUID("Owner")) {
	         this.ownerUUID = p_20052_.getUUID("Owner");
	      }
		
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag p_20139_) {
	      if (this.ownerUUID != null) {
	    	  p_20139_.putUUID("Owner", this.ownerUUID);
	      }
		
	}
	
	public void setOwner(@Nullable LivingEntity p_36939_) {
	    this.owner = p_36939_;
	    this.ownerUUID = p_36939_ == null ? null : p_36939_.getUUID();
	}

	@Nullable
	public LivingEntity getOwner() {
	   if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
	      Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
	      if (entity instanceof LivingEntity) {
	         this.owner = (LivingEntity)entity;
	      }
	   }

	   return this.owner;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	@Override
	public boolean fireImmune() {return true;}
	@Override
	public boolean isOnFire() {return false;}
	 
	 @Override
	public void tick() {
		delayTicks--;
		if(delayTicks<=0) {
			
			this.remove(RemovalReason.DISCARDED);
		}
		for(LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.75D, 0.5D, 0.75D))) {
            if(this.getOwner()==null || !livingentity.isAlliedTo(this.getOwner())) {
            	if(!hitEntities.contains(livingentity)) {
         	    this.dealDamageTo(livingentity);
         	    hitEntities.add(livingentity);
            	}
            }
         }
		super.tick();
	}
	 
	 private void dealDamageTo(LivingEntity p_36945_) {
	      LivingEntity livingentity = this.getOwner();
	      if (p_36945_.isAlive() && !p_36945_.isInvulnerable() && p_36945_ != livingentity) {
	         if (livingentity == null) {
	            p_36945_.hurt(this.damageSources().generic(), damage);
	         } else {
	            if (livingentity.isAlliedTo(p_36945_)||livingentity==p_36945_) {
	               return;
	            }else {

	            p_36945_.hurt(this.damageSources().mobAttack(livingentity), damage);
	            }
	         }

	      }
	   }

	 
	 //Arrow;
	 
}
