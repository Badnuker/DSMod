package net.badnuker.dsmod.client.render.entity;

import net.badnuker.dsmod.DefinitelyStupidMod;
import net.badnuker.dsmod.entity.CollapsedCore;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class CollapsedCoreRenderer extends EntityRenderer<CollapsedCore> {
    private static final Identifier TEXTURE = new Identifier(DefinitelyStupidMod.MOD_ID, "textures/entity/collapsed_core.png");
    private static final RenderLayer LAYER = RenderLayer.getItemEntityTranslucentCull(TEXTURE);

    public CollapsedCoreRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.15F;
        this.shadowOpacity = 0.75F;
    }

    @Override
    public void render(CollapsedCore collapsedCore, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        int j = 5;
        float h = (float)(j % 4 * 16) / 64.0F;
        float k = (float)(j % 4 * 16 + 16) / 64.0F;
        float l = (float)(j / 4 * 16) / 64.0F;
        float m = (float)(j / 4 * 16 + 16) / 64.0F;
        float r = ((float)collapsedCore.getCoreAge() + g) / 2.0F;
        int s = (int)((MathHelper.sin(r + 0.0F) + 1.0F) * 0.5F * 255.0F);
        int u = (int)((MathHelper.sin(r + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
        matrixStack.translate(0.0F, 0.1F, 0.0F);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrixStack.scale(0.3F, 0.3F, 0.3F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        vertex(vertexConsumer, matrix4f, matrix3f, -0.5F, -0.25F, s, u, h, m, i);
        vertex(vertexConsumer, matrix4f, matrix3f, 0.5F, -0.25F, s, u, k, m, i);
        vertex(vertexConsumer, matrix4f, matrix3f, 0.5F, 0.75F, s, u, k, l, i);
        vertex(vertexConsumer, matrix4f, matrix3f, -0.5F, 0.75F, s, u, h, l, i);
        matrixStack.pop();
        super.render(collapsedCore, f, g, matrixStack, vertexConsumerProvider, i);
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, int red, int blue, float u, float v, int light) {
        vertexConsumer.vertex(positionMatrix, x, y, 0.0F).color(red, 255, blue, 128).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }

    @Override
    public Identifier getTexture(CollapsedCore entity) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLight(CollapsedCore entity, BlockPos pos) {
        return 15; // 始终全亮
    }
}