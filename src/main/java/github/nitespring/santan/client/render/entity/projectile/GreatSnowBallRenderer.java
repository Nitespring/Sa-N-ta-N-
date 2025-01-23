package github.nitespring.santan.client.render.entity.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import github.nitespring.santan.common.entity.projectile.GreatSnowBall;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class GreatSnowBallRenderer extends EntityRenderer<GreatSnowBall> {

    private final BlockRenderDispatcher dispatcher;

    public GreatSnowBallRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(GreatSnowBall entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        BlockState blockstate = Blocks.SNOW_BLOCK.defaultBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = entity.level();
            if (blockstate != level.getBlockState(entity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                poseStack.pushPose();
                BlockPos blockpos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
                poseStack.translate(-0.5, 0.0, -0.5);
                if(entity.getExplosionType()==0){
                    poseStack.scale(0.4f, 0.4f, 0.4f);
                }
                var model = this.dispatcher.getBlockModel(blockstate);
                for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(1), net.neoforged.neoforge.client.model.data.ModelData.EMPTY))
                    this.dispatcher
                            .getModelRenderer()
                            .tesselateBlock(
                                    level,
                                    this.dispatcher.getBlockModel(blockstate),
                                    blockstate,
                                    blockpos,
                                    poseStack,
                                    buffer.getBuffer(net.neoforged.neoforge.client.RenderTypeHelper.getMovingBlockRenderType(renderType)),
                                    false,
                                    RandomSource.create(),
                                    1,
                                    OverlayTexture.NO_OVERLAY,
                                    net.neoforged.neoforge.client.model.data.ModelData.EMPTY,
                                    renderType
                            );
                poseStack.popPose();
                super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
            }
        }


    }

    @Override
    public ResourceLocation getTextureLocation(GreatSnowBall entity) {
        return null;
    }
}
