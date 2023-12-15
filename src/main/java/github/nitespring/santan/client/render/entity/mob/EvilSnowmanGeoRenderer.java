package github.nitespring.santan.client.render.entity.mob;

import github.nitespring.santan.common.entity.mob.EvilSnowman;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class EvilSnowmanGeoRenderer extends GeoEntityRenderer<EvilSnowman>{

	public EvilSnowmanGeoRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new EvilSnowmanModel());
        
        this.shadowRadius = 0.5F;
     
       
    }
	
	@Override
	protected float getDeathMaxRotation(EvilSnowman entityLivingBaseIn) {
		
		return 0f;
	}

	
	 @Override
	public RenderType getRenderType(EvilSnowman animatable, ResourceLocation texture, MultiBufferSource bufferSource,
			float partialTick) {
		 return RenderType.entityCutoutNoCull(texture);
	}
	 
	 
}
