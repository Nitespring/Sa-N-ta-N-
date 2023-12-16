package github.nitespring.santan.client.render.entity.projectile;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class InvisibleProjectileRenderer extends EntityRenderer<Entity>{

	public InvisibleProjectileRenderer(Context p_174008_) {
		super(p_174008_);
		
	}

	@Override
	public ResourceLocation getTextureLocation(Entity p_114482_) {
		
		return null;
	}

}
