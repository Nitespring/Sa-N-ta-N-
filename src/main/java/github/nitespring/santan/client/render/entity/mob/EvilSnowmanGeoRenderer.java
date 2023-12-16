package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;

import github.nitespring.santan.common.entity.mob.EvilSnowman;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EvilSnowmanGeoRenderer extends GeoEntityRenderer<EvilSnowman>{

	public EvilSnowmanGeoRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new EvilSnowmanModel());
        
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new EvilSnowmanEmissiveLayer<EvilSnowman>(this));
     
       
    }
	
	@Override
	protected float getDeathMaxRotation(EvilSnowman entityLivingBaseIn) {
		
		return 0f;
	}
	
	@Override
	public int getPackedOverlay(EvilSnowman animatable, float u) {

		return OverlayTexture.NO_OVERLAY;
	}

	
	 @Override
	public RenderType getRenderType(EvilSnowman animatable, ResourceLocation texture, MultiBufferSource bufferSource,
			float partialTick) {
		 return RenderType.entityCutoutNoCull(texture);
	}
	 
	 @Override
	public void render(EvilSnowman entity, float entityYaw, float partialTick, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight) {
		 
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
	}
	 
	 
}
