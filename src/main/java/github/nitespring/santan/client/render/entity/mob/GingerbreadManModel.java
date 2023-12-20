package github.nitespring.santan.client.render.entity.mob;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.GingerbreadMan;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class GingerbreadManModel extends GeoModel<GingerbreadMan>{

	@Override
	public ResourceLocation getAnimationResource(GingerbreadMan animatable) {
		
		return new ResourceLocation(SaNtaNMod.MODID, "animations/gingerbread_man.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(GingerbreadMan object) {
		
		return new ResourceLocation(SaNtaNMod.MODID, "geo/gingerbread_man.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GingerbreadMan object) {
		
		return new ResourceLocation(SaNtaNMod.MODID, "textures/entity/gingerbread_man.png");
	}
	
	
	@Override
	public void setCustomAnimations(GingerbreadMan entity, long uniqueID, AnimationState<GingerbreadMan> customPredicate) {
    	super.setCustomAnimations(entity, uniqueID, customPredicate);
    	CoreGeoBone head = this.getAnimationProcessor().getBone("head_rotation");
        assert customPredicate != null;
        EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX(extraData.headPitch() * ((float) (0.25 * Math.PI / 180F)));
        head.setRotY(extraData.netHeadYaw() * ((float) (0.25 * Math.PI / 180F)));
       
		
	}

}
