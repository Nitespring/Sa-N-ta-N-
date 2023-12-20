package github.nitespring.santan.client.render.entity.mob;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilElf;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class EvilElfModel extends GeoModel<EvilElf>{

	@Override
	public ResourceLocation getAnimationResource(EvilElf animatable) {
		
		return new ResourceLocation(SaNtaNMod.MODID, "animations/elf.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(EvilElf object) {
		
		return new ResourceLocation(SaNtaNMod.MODID, "geo/elf.geo.json");
			
	}

	@Override
	public ResourceLocation getTextureResource(EvilElf object) {
		switch(object.getCoatColour()) {
		case 1:
			return new ResourceLocation(SaNtaNMod.MODID, "textures/entity/elf_green.png");

		default:
			return new ResourceLocation(SaNtaNMod.MODID, "textures/entity/elf_red.png");

		}
	}
	
	
	@Override
	public void setCustomAnimations(EvilElf entity, long uniqueID, AnimationState<EvilElf> customPredicate) {
    	super.setCustomAnimations(entity, uniqueID, customPredicate);
    	CoreGeoBone head = this.getAnimationProcessor().getBone("head_rotation");
        assert customPredicate != null;
        EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
        head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
        head.updateScale(1.25f, 1.25f, 1.25f);
		
	}

}
