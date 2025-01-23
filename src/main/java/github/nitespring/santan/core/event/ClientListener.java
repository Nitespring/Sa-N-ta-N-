package github.nitespring.santan.core.event;

import github.nitespring.santan.SaNtaNMod;
import github.nitespring.santan.client.render.entity.mob.*;
import github.nitespring.santan.client.render.entity.projectile.ExplosivePresentGeoRenderer;
import github.nitespring.santan.client.render.entity.projectile.GreatSnowBallRenderer;
import github.nitespring.santan.client.render.entity.projectile.InvisibleProjectileRenderer;
import github.nitespring.santan.common.entity.mob.SnowyTree;
import github.nitespring.santan.core.init.EntityInit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;


@EventBusSubscriber(modid = SaNtaNMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientListener {
	
	 @SubscribeEvent
	 	public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
		 
		 event.registerEntityRenderer(EntityInit.SNOWMAN.get(), EvilSnowmanGeoRenderer::new);
		 event.registerEntityRenderer(EntityInit.GINGERBREAD.get(), GingerbreadManGeoRenderer::new);
		 event.registerEntityRenderer(EntityInit.ELF.get(), EvilElfGeoRenderer::new);
		 event.registerEntityRenderer(EntityInit.SNOWY_TREE.get(), SnowyTreeGeoRenderer::new);
		 event.registerEntityRenderer(EntityInit.TREE.get(), FestiveTreeGeoRenderer::new);
		 event.registerEntityRenderer(EntityInit.HITBOX.get(), InvisibleProjectileRenderer::new);
		 event.registerEntityRenderer(EntityInit.HITBOX_LARGE.get(), InvisibleProjectileRenderer::new);
		 event.registerEntityRenderer(EntityInit.PRESENT.get(), ExplosivePresentGeoRenderer::new);
		 event.registerEntityRenderer(EntityInit.SNOWBALL.get(), GreatSnowBallRenderer::new);
		 
	 }

}
