package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.GingerbreadMan;
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

@SuppressWarnings("ALL")
public class GingerbreadManGeoRenderer<T extends GingerbreadMan> extends GeoEntityRenderer<T>{

	public GingerbreadManGeoRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new GingerbreadManModel());

        this.shadowRadius = 0.5F;
        this.addRenderLayer(new GingerbreadManEmissiveLayer<T>(this));
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


    public static class GingerbreadManModel<T extends GingerbreadMan> extends GeoModel<T> {

        @Override
        public ResourceLocation getAnimationResource(T animatable) {

            return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "animations/gingerbread_man.animation.json");
        }

        @Override
        public ResourceLocation getModelResource(T object) {

            return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "geo/gingerbread_man.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(T object) {

            return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/gingerbread_man.png");
        }


        @Override
        public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
            super.setCustomAnimations(entity, uniqueID, customPredicate);
            GeoBone head = this.getAnimationProcessor().getBone("head_rotation");
            assert customPredicate != null;
            EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(extraData.headPitch() * ((float) (0.5 * Math.PI / 180F)));
            head.setRotY(extraData.netHeadYaw() * ((float) (0.2 * Math.PI / 180F)));


        }

    }

    public static class GingerbreadManEmissiveLayer<T extends GingerbreadMan> extends GeoRenderLayer<T> {

        private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/gingerbread_man_emissive.png");


        public GingerbreadManEmissiveLayer(GeoRenderer<T> entityRendererIn) {
            super(entityRendererIn);

        }


        @Override
        public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel,
                           RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
                           int packedLight, int packedOverlay) {
            
            RenderType cameo = RenderType.entityTranslucentEmissive(EYES);
    
            this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, cameo, bufferSource.getBuffer(cameo), partialTick, packedLight, packedOverlay, -1);
            
    
            
        }
        
        
        
        
    
    
        
    
        
    
        
    
    }
}
