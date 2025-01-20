package github.nitespring.santan.client.render.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilElf;
import github.nitespring.santan.common.entity.projectile.ExplosivePresent;
import github.nitespring.santan.core.init.ItemInit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@SuppressWarnings("ALL")
public class ExplosivePresentGeoRenderer<T extends ExplosivePresent> extends GeoEntityRenderer<T>{

	public ExplosivePresentGeoRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new ExplosivePresentModel());
        this.shadowRadius = 0.5F;
    }
	
	@Override
	protected float getDeathMaxRotation(ExplosivePresent entityLivingBaseIn) {
		
		return 0f;
	}


	@Override
	public int getPackedOverlay(ExplosivePresent animatable, float u, float partialTick) {
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
		 
		 float scaleFactor = 0.5f;
		 poseStack.pushPose();
		 poseStack.scale(scaleFactor, scaleFactor, scaleFactor);

		 poseStack.translate(0, 0, 0);
		     
		 super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		 poseStack.popPose();
	}


	public static class ExplosivePresentModel<T extends ExplosivePresent> extends GeoModel<T> {

		@Override
		public ResourceLocation getAnimationResource(T animatable) {

			return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "animations/present.animation.json");
		}

		@Override
		public ResourceLocation getModelResource(T object) {

			return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "geo/present.geo.json");

		}



		@Override
		public ResourceLocation getTextureResource(T object) {
			switch(object.getColour()) {
			case 1:
				return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/present/present_gold.png");
			case 2:
				return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/present/present_green.png");
			case 3:
				return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/present/present_blue.png");
			default:
				return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/present/present_red.png");

			}
		}



		@Override
		public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
			super.setCustomAnimations(entity, uniqueID, customPredicate);
			GeoBone present = this.getAnimationProcessor().getBone("root");
			GeoBone ribbon = this.getAnimationProcessor().getBone("ribbon_root");
			assert customPredicate != null;
			EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
			present.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
			ribbon.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));


		}

	}

}
