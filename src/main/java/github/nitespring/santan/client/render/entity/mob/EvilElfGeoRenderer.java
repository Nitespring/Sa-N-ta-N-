package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;

import github.nitespring.santan.common.entity.mob.EvilElf;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EvilElfGeoRenderer extends GeoEntityRenderer<EvilElf>{

	public EvilElfGeoRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new EvilElfModel());
        
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new EvilElfItemLayer(this));
          
    }
	
	@Override
	protected float getDeathMaxRotation(EvilElf entityLivingBaseIn) {
		
		return 0f;
	}
	
	@Override
	public int getPackedOverlay(EvilElf animatable, float u) {

		return OverlayTexture.NO_OVERLAY;
	}

	
	 @Override
	public RenderType getRenderType(EvilElf animatable, ResourceLocation texture, MultiBufferSource bufferSource,
			float partialTick) {
		 return RenderType.entityCutoutNoCull(texture);
	}
	 
	 @Override
	public void render(EvilElf entity, float entityYaw, float partialTick, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight) {
		 
		 float scaleFactor = 0.5f;
		 poseStack.pushPose();
		 poseStack.scale(scaleFactor, scaleFactor, scaleFactor);

		 poseStack.translate(0, 0, 0);
		     
		 super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		 poseStack.popPose();
	}
	 
	 
}
