package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilSnowman;
import github.nitespring.santan.common.entity.mob.SnowyTree;
import github.nitespring.santan.common.entity.mob.Tree;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class SnowyTreeGeoRenderer<T extends SnowyTree> extends GeoEntityRenderer<T>{

	public SnowyTreeGeoRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new TreeModel());
        
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new SnowyTreeOverlayLayer<>(this));
        this.addRenderLayer(new SnowyTreeEmissiveLayer<T>(this));
     
       
    }
	
	@Override
	protected float getDeathMaxRotation(T entityLivingBaseIn) {
		
		return 0f;
	}

    @Override
    public int getPackedOverlay(T animatable, float u, float partialTick) {
		return OverlayTexture.NO_OVERLAY;
	}

	
	 @Override
	public RenderType getRenderType(T animatable, ResourceLocation texture, MultiBufferSource bufferSource,
			float partialTick) {
		 return RenderType.entityCutoutNoCull(texture);
	}
	 
	 @Override
	public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight) {
		 
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
	}


    public static class SnowyTreeEmissiveLayer<T extends SnowyTree> extends GeoRenderLayer<T> {

        private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/tree_emissive.png");



        public SnowyTreeEmissiveLayer(GeoRenderer<T> entityRendererIn) {
            super(entityRendererIn);

        }


        @Override
        public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel,
                           RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
                           int packedLight, int packedOverlay) {

            RenderType cameo = RenderType.entityCutoutNoCull(EYES);

            if(animatable.getAnimationState()!=12) {
                this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, cameo, bufferSource.getBuffer(cameo), partialTick, 255, packedOverlay, -1);
            }

        }

    }

    public static class SnowyTreeOverlayLayer<T extends SnowyTree> extends GeoRenderLayer<T> {

        private static final ResourceLocation OVERLAY = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/tree_snowy.png");



        public SnowyTreeOverlayLayer(GeoRenderer<T> entityRendererIn) {
            super(entityRendererIn);

        }


        @Override
        public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel,
                           RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
                           int packedLight, int packedOverlay) {

            RenderType cameo = RenderType.entityCutoutNoCull(OVERLAY);


            this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, cameo, bufferSource.getBuffer(cameo), partialTick, packedLight, packedOverlay, -1);


        }

    }
}
