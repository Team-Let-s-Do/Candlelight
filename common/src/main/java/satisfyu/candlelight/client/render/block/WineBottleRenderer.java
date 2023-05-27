package satisfyu.candlelight.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import satisfyu.candlelight.block.WineBottleBlock;
import satisfyu.candlelight.block.entity.WineBottleBlockEntity;

import static satisfyu.candlelight.block.WineBottleBlock.COUNT;
import static satisfyu.candlelight.client.ClientUtil.renderBlock;

@Environment(EnvType.CLIENT)
public class WineBottleRenderer implements BlockEntityRenderer<WineBottleBlockEntity> {

    public WineBottleRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(WineBottleBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!entity.hasLevel()) {
            return;
        }
        BlockState selfState = entity.getBlockState();
        if (selfState.getBlock() instanceof WineBottleBlock) {
            BlockState defaultState = selfState.getBlock().defaultBlockState().setValue(COUNT, 0);
            matrices.pushPose();
            applyBlockAngle(matrices, selfState);
            switch (entity.getCount()) {
                default -> renderOne(entity, matrices, vertexConsumers, defaultState);
                case 2 -> renderTwo(entity, matrices, vertexConsumers, defaultState);
                case 3 -> renderThree(entity, matrices, vertexConsumers, defaultState);
            }
            matrices.popPose();
        }

    }

    public static void applyBlockAngle(PoseStack matrices, BlockState state) {
        switch (state.getValue(WineBottleBlock.FACING)) {
            case NORTH -> {
                matrices.translate(0f, 0f, 1f);
                matrices.mulPose(Vector3f.YP.rotationDegrees(90));
            }
            case WEST -> {
                matrices.translate(1f, 0f, 1f);
                matrices.mulPose(Vector3f.YP.rotationDegrees(180));
            }
            case SOUTH -> {
                matrices.translate(1f, 0f, 0f);
                matrices.mulPose(Vector3f.YP.rotationDegrees(270));
            }
        }
    }

    private void renderOne(WineBottleBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, BlockState defaultState) {
        renderBlock(defaultState, matrices, vertexConsumers, entity);
    }

    private void renderTwo(WineBottleBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, BlockState defaultState) {
        matrices.translate(-0.15f, 0f, -0.25f);
        renderBlock(defaultState, matrices, vertexConsumers, entity);
        matrices.translate(.1f, 0f, .8f);
        matrices.mulPose(Vector3f.YP.rotationDegrees(30));
        renderBlock(defaultState, matrices, vertexConsumers, entity);
}

    private void renderThree(WineBottleBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, BlockState defaultState) {
        matrices.translate(-0.25f, 0f, -0.25f);
        renderBlock(defaultState, matrices, vertexConsumers, entity);
        matrices.translate(.15f, 0f, .5f);
        renderBlock(defaultState, matrices, vertexConsumers, entity);
        matrices.translate(.1f, 0f, 0f);
        matrices.mulPose(Vector3f.YP.rotationDegrees(30));
        renderBlock(defaultState, matrices, vertexConsumers, entity);
    }
}