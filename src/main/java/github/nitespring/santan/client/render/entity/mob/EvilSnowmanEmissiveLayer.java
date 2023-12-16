package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilSnowman;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class EvilSnowmanEmissiveLayer<T extends EvilSnowman> extends GeoRenderLayer<EvilSnowman>{

	private static final ResourceLocation EYES = new ResourceLocation(SaNtaNMod.MODID, "textures/entity/snowman_emissive_eyes.png");
	private static final ResourceLocation ANTLERS1 = new ResourceLocation(SaNtaNMod.MODID, "textures/entity/snowman_emissive_antlers1.png");
	private static final ResourceLocation ANTLERS2 = new ResourceLocation(SaNtaNMod.MODID, "textures/entity/snowman_emissive_antlers2.png");
	private static final ResourceLocation ANTLERS3 = new ResourceLocation(SaNtaNMod.MODID, "textures/entity/snowman_emissive_antlers3.png");
	
	
	public EvilSnowmanEmissiveLayer(GeoRenderer<EvilSnowman> entityRendererIn) {
		super(entityRendererIn);
	
	}
	
	
	@Override
	public void render(PoseStack poseStack, EvilSnowman animatable, BakedGeoModel bakedModel,
			RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
			int packedLight, int packedOverlay) {
		
		RenderType cameo = RenderType.eyes(EYES);
		if(animatable.getSnowmanType()==4||animatable.getSnowmanType()==5) {
			switch(animatable.getLightState()) {
			case 0,1,2:
				cameo = RenderType.eyes(ANTLERS1);
				break;
			case 3,4,5:
				cameo = RenderType.eyes(ANTLERS2);
				break;
			case 6,7,8:
				cameo = RenderType.eyes(ANTLERS3);
				break;
			}
			
		}

		
		
		this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, cameo, bufferSource.getBuffer(cameo), partialTick, packedLight, packedOverlay, 1f, 1f, 1f, 1f);
		

		
	}
	
	
	
	


	

	

	

}
