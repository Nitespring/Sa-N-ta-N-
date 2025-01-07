package github.nitespring.santan.common.entity.mob;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.Random;

public class FestiveTree extends Tree{
    private static final EntityDataAccessor<Integer> TREE_TYPE = SynchedEntityData.defineId(FestiveTree.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIGHT_STATE = SynchedEntityData.defineId(FestiveTree.class, EntityDataSerializers.INT);

    public FestiveTree(EntityType<? extends AbstractYuleEntity> e, Level l) {
        super(e, l);
    }
    public static  AttributeSupplier.Builder setCustomAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.ATTACK_SPEED, 1.2D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.2D)
                .add(Attributes.FOLLOW_RANGE, 35);

    }
    public int getTreeType() {return this.entityData.get(TREE_TYPE);}
    public void setTreeType(int i) {this.entityData.set(TREE_TYPE, i);}

    public int getLightState() {return this.entityData.get(LIGHT_STATE);}

    public void setLightState(int i) {this.entityData.set(LIGHT_STATE, i);}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TREE_TYPE, 0);
        builder.define(LIGHT_STATE, 0);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setTreeType(tag.getInt("TreeType"));
    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("TreeType", this.getTreeType());

    }
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @org.jetbrains.annotations.Nullable SpawnGroupData spawnGroupData) {
        int r = new Random().nextInt(255);
        if(r<=31){
            setTreeType(1);
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public void tick() {
        if(this.tickCount%8==0){
            this.setLightState(this.getLightState()+1);
        }
        if(this.getLightState()>=3) {
            this.setLightState(0);
        }

        super.tick();
    }
}
