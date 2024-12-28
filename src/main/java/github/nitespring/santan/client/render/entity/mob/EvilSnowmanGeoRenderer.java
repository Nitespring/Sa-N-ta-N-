package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilSnowman;
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
public class EvilSnowmanGeoRenderer<T extends EvilSnowman> extends GeoEntityRenderer<T>{

	public EvilSnowmanGeoRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new EvilSnowmanModel());
        
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new EvilSnowmanEmissiveLayer<T>(this));
     
       
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


    public static class EvilSnowmanModel<T extends EvilSnowman> extends GeoModel<T> {

        @Override
        public ResourceLocation getAnimationResource(T animatable) {

            return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "animations/snowman.animation.json");
        }

        @Override
        public ResourceLocation getModelResource(T object) {

            return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "geo/snowman.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(T object) {

            return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/snowman.png");
        }


        @Override
        public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
            super.setCustomAnimations(entity, uniqueID, customPredicate);
            GeoBone head = this.getAnimationProcessor().getBone("head_rotation");
            GeoBone hat = this.getAnimationProcessor().getBone("hat");
            GeoBone antlers = this.getAnimationProcessor().getBone("antlers");
            assert customPredicate != null;
            EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
            switch(entity.getSnowmanType()) {
            case 1,4:
                hat.setHidden(true);
                antlers.setHidden(false);
                break;
            case 2:
                hat.setHidden(false);
                antlers.setHidden(true);
                break;
            case 3:
                hat.setHidden(true);
                antlers.setHidden(true);
                break;
            default:
                hat.setHidden(false);
                antlers.setHidden(false);
                break;
            }

        }

    }

    public static class EvilSnowmanEmissiveLayer<T extends EvilSnowman> extends GeoRenderLayer<T> {

        private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/snowman_emissive_eyes.png");
        private static final ResourceLocation ANTLERS1 = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/snowman_emissive_antlers1.png");
        private static final ResourceLocation ANTLERS2 = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/snowman_emissive_antlers2.png");
        private static final ResourceLocation ANTLERS3 = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/snowman_emissive_antlers3.png");


        public EvilSnowmanEmissiveLayer(GeoRenderer<T> entityRendererIn) {
            super(entityRendererIn);

        }


        @Override
        public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel,
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



            this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, cameo, bufferSource.getBuffer(cameo), partialTick, packedLight, packedOverlay, -1);



        }












    }
}
