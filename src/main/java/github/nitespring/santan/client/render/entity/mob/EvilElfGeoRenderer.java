package github.nitespring.santan.client.render.entity.mob;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.EvilElf;
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
public class EvilElfGeoRenderer<T extends EvilElf> extends GeoEntityRenderer<T>{

	public EvilElfGeoRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new EvilElfModel());
        
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new EvilElfItemLayer(this));
        this.addRenderLayer(new EvilElfEmissiveLayer<T>(this));
          
    }
	
	@Override
	protected float getDeathMaxRotation(EvilElf entityLivingBaseIn) {
		
		return 0f;
	}


	@Override
	public int getPackedOverlay(EvilElf animatable, float u, float partialTick) {
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


	public static class EvilElfModel<T extends EvilElf> extends GeoModel<T> {

		@Override
		public ResourceLocation getAnimationResource(T animatable) {

			return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "animations/elf.animation.json");
		}

		@Override
		public ResourceLocation getModelResource(T object) {

			return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "geo/elf.geo.json");

		}



		@Override
		public ResourceLocation getTextureResource(T object) {
			switch(object.getCoatColour()) {
			case 1:
				return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/elf_green.png");

			default:
				return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/elf_red.png");

			}
		}



		@Override
		public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
			super.setCustomAnimations(entity, uniqueID, customPredicate);
			GeoBone head = this.getAnimationProcessor().getBone("head_rotation");
			assert customPredicate != null;
			EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(extraData.headPitch() * ((float) Math.PI / 180F));
			head.setRotY(extraData.netHeadYaw() * ((float) Math.PI / 180F));
			head.updateScale(1.25f, 1.25f, 1.25f);

		}

	}

	public static class EvilElfEmissiveLayer<T extends EvilElf> extends GeoRenderLayer<T> {

		private static final ResourceLocation EYES = ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/elf_emissive.png");


		public EvilElfEmissiveLayer(GeoRenderer<T> entityRendererIn) {
			super(entityRendererIn);

		}


		@Override
		public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel,
						   RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
						   int packedLight, int packedOverlay) {

			RenderType cameo = RenderType.eyes(EYES);

			this.getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, cameo, bufferSource.getBuffer(cameo), partialTick, packedLight, packedOverlay, -1);



		}












	}

	public static class EvilElfItemLayer<T extends EvilElf> extends BlockAndItemGeoLayer<T> {

		public EvilElfItemLayer(GeoRenderer<T> renderer) {
			super(renderer);

		}



		@Override
		protected ItemStack getStackForBone(GeoBone bone, T animatable) {
			 if (bone.getName().equals("item")) {
				  return new ItemStack(ItemInit.CANDYBAR.get());
			  }else {


			return null;

			  }
		}

		@Override
		protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
			if (bone.getName().equals("item")) {
				  return ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
			  }else
			return null;
		}





	@Override
	protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, T animatable,
			MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
		if (bone.getName().equals("item")) {
			poseStack.translate(0, -0.35, -0.5);
			//poseStack.mulPose(Axis.XP.rotationDegrees(0));
			//poseStack.mulPose(Axis.YP.rotationDegrees(0));
			//poseStack.mulPose(Axis.ZP.rotationDegrees(180));


		  }
		super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
	}



	}
}
