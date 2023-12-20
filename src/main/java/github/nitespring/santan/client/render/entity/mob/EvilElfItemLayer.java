package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;

import github.nitespring.santan.common.entity.mob.EvilElf;
import github.nitespring.santan.core.init.ItemInit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class EvilElfItemLayer extends BlockAndItemGeoLayer<EvilElf>{

	public EvilElfItemLayer(GeoRenderer<EvilElf> renderer) {
		super(renderer);
		
	}
	
	

	@Override
	protected ItemStack getStackForBone(GeoBone bone, EvilElf animatable) {
		 if (bone.getName().equals("item")) { 
			  return new ItemStack(ItemInit.CANDYBAR.get());
		  }else {
		
		
		return null;
		
		  }
	}
	
	@Override
	protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, EvilElf animatable) {
		if (bone.getName().equals("item")) { 
			  return ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
		  }else
		return null;
	}
	
	



@Override
protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, EvilElf animatable,
		MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
	if (bone.getName().equals("item")) { 
		poseStack.translate(0, -0.35, -0.5); 
		//poseStack.mulPose(Axis.XP.rotationDegrees(0)); 
		//poseStack.mulPose(Axis.YP.rotationDegrees(0)); 
		//poseStack.mulPose(Axis.ZP.rotationDegrees(180));
   
      
	  }
	super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
}
	


}
