package github.nitespring.santan.client.render.entity.mob;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.common.entity.mob.Tree;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TreeModel<T extends Tree> extends GeoModel<T> {

        @Override
        public ResourceLocation getAnimationResource(T animatable) {

            return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "animations/tree.animation.json");
        }

        @Override
        public ResourceLocation getModelResource(T object) {

            return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "geo/tree.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(T object) {

            return ResourceLocation.fromNamespaceAndPath(SaNtaNMod.MODID, "textures/entity/tree.png");
        }


        @Override
        public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
            super.setCustomAnimations(entity, uniqueID, customPredicate);
            GeoBone head = this.getAnimationProcessor().getBone("body_rotation");
            assert customPredicate != null;
            EntityModelData extraData = (EntityModelData) customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(0.25f*extraData.headPitch() * ((float) Math.PI / 180F));
            head.setRotY(0.15f*extraData.netHeadYaw() * ((float) Math.PI / 180F));

        }

    }