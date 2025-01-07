package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilSnowman;
import github.nitespring.santan.common.entity.mob.FestiveTree;
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

public class FestiveTreeGeoRenderer<T extends FestiveTree> extends GeoEntityRenderer<T>{

	public FestiveTreeGeoRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new TreeModel());
        
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new FestiveTreeOverlayLayer<>(this));
        this.addRenderLayer(new FestiveTreeEmissiveLayer<T>(this));
     
       
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



    public static class FestiveTreeEmissiveLayer<T extends FestiveTree> extends GeoRenderLayer<T> {

        private static final ResourceLocation LIGHTS1 = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/tree_lights1.png");
        private static final ResourceLocation LIGHTS2 = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/tree_lights2.png");
        private static final ResourceLocation LIGHTS3 = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/tree_lights3.png");


        public FestiveTreeEmissiveLayer(GeoRenderer<T> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel,
                           RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
                           int packedLight, int packedOverlay) {

            RenderType cameo;
            switch(animatable.getLightState()) {
            case 1:
                cameo = RenderType.eyes(LIGHTS2);
                break;
            case 2:
                cameo = RenderType.eyes(LIGHTS3);
                break;
            default:
                cameo = RenderType.eyes(LIGHTS1);
                break;
            }
            if(animatable.getTreeType()==1&&animatable.getAnimationState()!=12) {
                this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, cameo, bufferSource.getBuffer(cameo), partialTick, packedLight, packedOverlay, -1);
            }
        }

    }
    public static class FestiveTreeOverlayLayer<T extends FestiveTree> extends GeoRenderLayer<T> {

        private static final ResourceLocation OVERLAY = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/tree_festive.png");



        public FestiveTreeOverlayLayer(GeoRenderer<T> entityRendererIn) {
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
