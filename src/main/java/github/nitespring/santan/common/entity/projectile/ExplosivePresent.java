package github.nitespring.santan.common.entity.projectile;

import github.nitespring.santan.common.entity.mob.AbstractYuleEntity;
import github.nitespring.santan.common.entity.mob.FestiveTree;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ExplosivePresent extends Projectile implements GeoEntity {

    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(AbstractYuleEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COLOUR = SynchedEntityData.defineId(ExplosivePresent.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> PRESENT_ROTATION = SynchedEntityData.defineId(ExplosivePresent.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RIBBON_ROTATION = SynchedEntityData.defineId(ExplosivePresent.class, EntityDataSerializers.FLOAT);
    protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public ExplosivePresent(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public int getAnimationState() {return this.entityData.get(ANIMATION_STATE);}
    public void setAnimationState(int anim) {this.entityData.set(ANIMATION_STATE, anim);}

    public int getColour() {return this.entityData.get(COLOUR);}
    public void setColour(int i) {this.entityData.set(COLOUR, i);}

    public float getPresentRotation() {return this.entityData.get(PRESENT_ROTATION);}
    public void setPresentRotation(float i) {this.entityData.set(PRESENT_ROTATION, i);}

    public float getRibbonRotation() {return this.entityData.get(RIBBON_ROTATION);}
    public void setRibbonRotation(float i) {this.entityData.set(RIBBON_ROTATION, i);}


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main_controller", 4, this::predicate));
        controllers.add(new AnimationController<>(this, "ribbon_controller", 0, this::ribbonPredicate));
    }
    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        int animState = this.getAnimationState();

        switch(animState) {
            case 1:
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.present.explode"));
                break;
            default:
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.present.new"));
                break;
        }
        return PlayState.CONTINUE;
    }
    private <E extends GeoAnimatable> PlayState ribbonPredicate(AnimationState<E> event) {
        int animState = this.getAnimationState();

        switch(animState) {
            case 1:
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.present.explode"));
                break;
            default:
                event.getController().setAnimation(RawAnimation.begin().thenLoop("animation.present.new"));
                break;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(ANIMATION_STATE, 0);
        builder.define(COLOUR, 0);
        builder.define(PRESENT_ROTATION, 0F);
        builder.define(RIBBON_ROTATION, 0F);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setColour(tag.getInt("Colour"));
        this.setAnimationState(tag.getInt("AnimationId"));
    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Colour", this.getColour());
        tag.putInt("AnimationId", this.getAnimationState());
    }
}
