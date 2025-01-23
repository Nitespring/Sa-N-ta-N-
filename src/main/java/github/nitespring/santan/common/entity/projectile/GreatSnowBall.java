package github.nitespring.santan.common.entity.projectile;

import github.nitespring.santan.core.tags.CustomBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GreatSnowBall extends AbstractHurtingProjectile {

    public float explosionRadius = 1.5f;
    public float attackDamage = 2.5f;
    private static final EntityDataAccessor<Integer> EXPLOSION_TYPE = SynchedEntityData.defineId(GreatSnowBall.class, EntityDataSerializers.INT);


    public GreatSnowBall(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public void setExplosionRadius(float explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getExplosionType() {return this.entityData.get(EXPLOSION_TYPE);}
    public void setExplosionType(int anim) {this.entityData.set(EXPLOSION_TYPE, anim);}

    public float getAttackDamage() {
        return attackDamage;
    }


    @Override
    public void tick() {
        super.tick();
        Vec3 mov = this.getDeltaMovement().multiply(0.95f,1,0.95f).add(0,-0.1,0);
        setDeltaMovement(mov);
        if(isInWater()){
            setDeltaMovement(0,0,0);
            explode();
        }
        if(tickCount>=1000){
            this.discard();
        }
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(EXPLOSION_TYPE, 0);
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return null;
    }

    @Override
    public boolean fireImmune() {return true;}

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setExplosionType(tag.getInt("ExplosionType"));
    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ExplosionType", this.getExplosionType());
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        setDeltaMovement(0,this.getDeltaMovement().y,0);
        if(result.getDirection()==Direction.UP){
            explode();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        explode();
    }

    private void explode() {
        playSound(SoundEvents.SNOW_PLACE);
        for(LivingEntity livingentity : level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(explosionRadius, explosionRadius, explosionRadius))) {
            if(((livingentity instanceof Player ||livingentity!=this.getOwner())&&!(this.getOwner().isAlliedTo(livingentity)))) {
                if(livingentity.hurtTime<=0) {

                    livingentity.hurt(this.level().damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), this.getAttackDamage());
                }
            }
        }



        float spread = explosionRadius;
        int x0 = this.blockPosition().getX();
        int y0 = this.blockPosition().getY();
        int z0 = this.blockPosition().getZ();
        List<BlockPos> overriddenBlocks = new ArrayList<>();


        for(int i = 0; i<=12; i++) {
            for(int j = 0;  j<=spread; j++) {
                for(int k = 0; k<=spread; k++) {
                    double a=  Math.PI/6;
                    double d = j;
                    int xVar = (int) (d*Math.sin(i*a));
                    float yVar = (k-spread)*j;
                    int zVar = (int) (d*Math.cos(i*a));;
                    int x= x0+xVar;
                    int z= z0+zVar;
                    float y = y0+yVar;

                    BlockPos blockPos = new BlockPos(x, (int) y,z);

                    if(!overriddenBlocks.contains(blockPos)) {
                        overriddenBlocks.add(blockPos);
                        BlockState block = level().getBlockState(blockPos);
                        BlockState blockBelow = level().getBlockState(blockPos.below());
                        if (!this.level().isClientSide()) {

                            if (getExplosionType() == 1) {
                                if (block.is(CustomBlockTags.SNOW_BREAKABLE_1)) {
                                    level().destroyBlock(blockPos, true, this.getOwner());
                                    level().gameEvent(this, GameEvent.BLOCK_DESTROY, blockPos);
                                }
                                if (block.is(Blocks.WATER)) {
                                    BlockState blockstate1 = Blocks.ICE.defaultBlockState();
                                    level().setBlock(blockPos, blockstate1, 11);
                                    //level().destroyBlock(blockPos, true, this.getOwner());
                                    level().gameEvent(this, GameEvent.BLOCK_CHANGE, blockPos);
                                }else if (block.is(BlockTags.AIR) && !blockBelow.is(BlockTags.AIR)) {
                                    BlockState blockstate1 = Blocks.POWDER_SNOW.defaultBlockState();
                                    level().setBlock(blockPos, blockstate1, 11);
                                    level().gameEvent(this, GameEvent.BLOCK_PLACE, blockPos);
                                }
                            } else {
                                if (block.is(CustomBlockTags.SNOW_BREAKABLE)) {
                                    level().destroyBlock(blockPos, true, this.getOwner());
                                    level().gameEvent(this, GameEvent.BLOCK_DESTROY, blockPos);
                                }
                                if (block.is(Blocks.SNOW)) {
                                    SnowLayerBlock snow = (SnowLayerBlock) block.getBlock();
                                    int layers = block.getValue(SnowLayerBlock.LAYERS);
                                    BlockState blockstate1 = block.setValue(SnowLayerBlock.LAYERS, Math.min(layers + 1, 8));
                                    level().setBlock(blockPos, blockstate1, 11);
                                    //level().destroyBlock(blockPos, true, this.getOwner());
                                    level().gameEvent(this, GameEvent.BLOCK_CHANGE, blockPos);
                                }else if (block.is(Blocks.WATER)) {
                                    BlockState blockstate1 = Blocks.ICE.defaultBlockState();
                                    level().setBlock(blockPos, blockstate1, 11);
                                    //level().destroyBlock(blockPos, true, this.getOwner());
                                    level().gameEvent(this, GameEvent.BLOCK_CHANGE, blockPos);
                                }else if (blockBelow.is(Blocks.WATER)) {
                                    BlockState blockstate1 = Blocks.ICE.defaultBlockState();
                                    level().setBlock(blockPos.below(), blockstate1, 11);
                                    //level().destroyBlock(blockPos, true, this.getOwner());
                                    level().gameEvent(this, GameEvent.BLOCK_CHANGE, blockPos);
                                } else if (blockBelow.is(Blocks.SNOW)) {
                                    int layers = blockBelow.getValue(SnowLayerBlock.LAYERS);
                                    BlockState blockstate1 = blockBelow.setValue(SnowLayerBlock.LAYERS, Math.min(layers + 1, 8));
                                    level().setBlock(blockPos.below(), blockstate1, 11);
                                    //level().destroyBlock(blockPos, true, this.getOwner());
                                    level().gameEvent(this, GameEvent.BLOCK_CHANGE, blockPos);
                                }else if (block.is(BlockTags.AIR) && !blockBelow.is(BlockTags.AIR)) {
                                    BlockState blockstate1 = Blocks.SNOW.defaultBlockState();
                                    level().setBlock(blockPos, blockstate1, 11);
                                    level().gameEvent(this, GameEvent.BLOCK_PLACE, blockPos);
                                }
                            }
                        }
                    }
                    for(int n = 0; n<=1; n++) {
                        double xVar1 = d * Math.sin(i * a);
                        float yVar1 = k / 2;
                        double zVar1 = d * Math.cos(i * a);
                        ;
                        level().addParticle(ParticleTypes.SNOWFLAKE,
                                x0 + 0.6 * (1 + 2.5 * n) * xVar1 + 0.25 * (random.nextFloat() - 0.5),
                                y0 + 0.05 * (1 + 2.5 * n) * yVar1 + 0.25 * (random.nextFloat() - 0.5),
                                z0 + 0.6 * (1 + 2.5 * n) * zVar1 + 0.25 * (random.nextFloat() - 0.5),
                                0.1 * xVar1 + 0.25 * (random.nextFloat() - 0.5),
                                0.02f * yVar1 + 0.15 * (random.nextFloat() - 0.5),
                                0.1 * zVar1 + 0.25 * (random.nextFloat() - 0.5));
                        level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState()),
                                x0 + 0.5 * (1 + 2.5 * n) * xVar1 + 0.25 * (random.nextFloat() - 0.5),
                                y0 + 0.15 * (1 + 2.5 * n) * yVar1 + 0.25 * (random.nextFloat() - 0.5),
                                z0 + 0.5 * (1 + 2.5 * n) * zVar1 + 0.25 * (random.nextFloat() - 0.5),
                                0.15 * xVar1 + 0.25 * (random.nextFloat() - 0.5),
                                0.1f * yVar1 + 0.05 * (random.nextFloat() - 0.5),
                                0.15 * zVar1 + 0.25 * (random.nextFloat() - 0.5));

                    }
                }
            }
        }
        this.discard();
    }

}
