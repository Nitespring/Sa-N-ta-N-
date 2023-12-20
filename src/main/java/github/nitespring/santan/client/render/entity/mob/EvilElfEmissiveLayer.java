package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilElf;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class EvilElfEmissiveLayer<T extends EvilElf> extends GeoRenderLayer<EvilElf>{

	private static final ResourceLocation EYES = new ResourceLocation(SaNtaNMod.MODID, "textures/entity/elf_emissive.png");
	
	
	public EvilElfEmissiveLayer(GeoRenderer<EvilElf> entityRendererIn) {
		super(entityRendererIn);
	
	}
	
	
	@Override
	public void render(PoseStack poseStack, EvilElf animatable, BakedGeoModel bakedModel,
			RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
			int packedLight, int packedOverlay) {
		
		RenderType cameo = RenderType.eyes(EYES);

		this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, cameo, bufferSource.getBuffer(cameo), partialTick, packedLight, packedOverlay, 1f, 1f, 1f, 1f);
		

		
	}
	
	
	
	


	

	

	

}
