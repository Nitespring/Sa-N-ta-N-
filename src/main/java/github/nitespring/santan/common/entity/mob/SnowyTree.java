package github.nitespring.santan.common.entity.mob;

import github.nitespring.santan.common.entity.projectile.ExplosivePresent;
import github.nitespring.santan.common.entity.projectile.GreatSnowBall;
import github.nitespring.santan.core.init.EntityInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SnowyTree extends Tree{

    public SnowyTree(EntityType<? extends AbstractYuleEntity> e, Level l) {
        super(e, l);
    }
    public static  AttributeSupplier.Builder setCustomAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.ATTACK_SPEED, 1.2D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.2D)
                .add(Attributes.FOLLOW_RANGE, 35);

    }

    @Override
    public void rangedAttackSmall() {
        Random r = new Random();
        float angleMax = (float) (Math.PI / 6);
        for(int i=0; i<=2; i++) {
            float rx = r.nextFloat(-1, 1);
            float rz = r.nextFloat(-1, 1);
            float rPw = r.nextFloat(0, 1);
            Vec3 aim0 = new Vec3(0, 1, 0);
            Vec3 aimF = aim0.add(angleMax * rx, 0, angleMax * rz).normalize();
            Vec3 pos0 = position();

            Level level = level();
            GreatSnowBall snowball = new GreatSnowBall(EntityInit.SNOWBALL.get(), level);
            snowball.setPos(pos0.x + 0.1 * aimF.x(), pos0.y + 2.0f, pos0.z + 0.1 * aimF.z());
            snowball.setDeltaMovement(aimF.scale(0.25f + 0.5f * rPw));
            snowball.setExplosionRadius(1.5f);
            snowball.setAttackDamage(2.5f);
            snowball.setOwner(this);
            snowball.setExplosionType(0);
            level.addFreshEntity(snowball);
        }
    }

    @Override
    public void rangedAttackLarge() {
        Random r = new Random();
        float angleMax = (float) (Math.PI/24);
        float angleY = (float) (Math.PI/6);
        float rx = r.nextFloat(-1,1);
        float rz = r.nextFloat(-1,1);
        float rPw = r.nextFloat(0,1);
        Vec3 aim0 = getLookAngle();
        float d0 = 1.0f;
        if(getTarget()!=null){
            aim0 = getTarget().position().add(position().scale(-1)).normalize();
            d0 = Math.min(5,0.1f*distanceTo(getTarget()));
        }
        Vec3 aimF = aim0.add(
                angleMax*rx,
                angleY*d0,
                angleMax*rz
        ).normalize();
        Vec3 pos0 = position();

        Level level = level();
        GreatSnowBall snowball = new GreatSnowBall(EntityInit.SNOWBALL.get(),level);
        snowball.setPos(pos0.x+0.1*aimF.x(),pos0.y+1.5f,pos0.z+0.1*aimF.z());
        snowball.setDeltaMovement(aimF.scale(0.75f+0.25f*rPw + 0.25*d0));
        snowball.setExplosionRadius(2.5f);
        snowball.setAttackDamage(6.0f);
        snowball.setOwner(this);
        snowball.setExplosionType(1);
        level.addFreshEntity(snowball);

    }
}
