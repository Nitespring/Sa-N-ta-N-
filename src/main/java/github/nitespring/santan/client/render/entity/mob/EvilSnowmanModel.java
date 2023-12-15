package github.nitespring.santan.client.render.entity.mob;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilSnowman;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class EvilSnowmanModel extends GeoModel<EvilSnowman>{

	@Override
	public ResourceLocation getAnimationResource(EvilSnowman animatable) {
		
		return new ResourceLocation(SaNtaNMod.MODID, "animations/snowman.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(EvilSnowman object) {
		
		return new ResourceLocation(SaNtaNMod.MODID, "geo/snowman.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EvilSnowman object) {
		
		return new ResourceLocation(SaNtaNMod.MODID, "textures/entity/snowman.png");
	}
	
	
	@Override
	public void setCustomAnimations(EvilSnowman entity, long uniqueID, AnimationState<EvilSnowman> customPredicate) {
    	super.setCustomAnimations(entity, uniqueID, customPredicate);
    	CoreGeoBone head = this.getAnimationProcessor().getBone("head_rotation");
        assert customPredicate != null;
        EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
        head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
        
		
	}

}
