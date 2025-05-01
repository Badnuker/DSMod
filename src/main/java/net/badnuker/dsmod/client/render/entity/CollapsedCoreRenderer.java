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
        int j = 8;
        float h = (j % 4 * 16 + 0) / 64.0F;
        float k = (j % 4 * 16 + 16) / 64.0F;
        float l = (j / 4 * 16 + 0) / 64.0F;
        float m = (j / 4 * 16 + 16) / 64.0F;
        float n = 1.0F;
        float o = 0.5F;
        float p = 0.25F;
        float q = 255.0F;
        float r = (collapsedCore.age + g) / 2.0F;
        int s = (int)((MathHelper.sin(r + 0.0F) + 1.0F) * 0.5F * 255.0F);
        int t = 255;
        int u = (int)((MathHelper.sin(r + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
        matrixStack.translate(0.0F, 0.1F, 0.0F);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        float v = 0.3F;
        matrixStack.scale(0.3F, 0.3F, 0.3F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        vertex(vertexConsumer, matrix4f, matrix3f, -0.5F, -0.25F, s, 255, u, h, m, i);
        vertex(vertexConsumer, matrix4f, matrix3f, 0.5F, -0.25F, s, 255, u, k, m, i);
        vertex(vertexConsumer, matrix4f, matrix3f, 0.5F, 0.75F, s, 255, u, k, l, i);
        vertex(vertexConsumer, matrix4f, matrix3f, -0.5F, 0.75F, s, 255, u, h, l, i);
        matrixStack.pop();
        super.render(collapsedCore, f, g, matrixStack, vertexConsumerProvider, i);
    }

    private static void vertex(
            VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, int red, int green, int blue, float u, float v, int light
    ) {
        vertexConsumer.vertex(positionMatrix, x, y, 0.0F)
                .color(red, green, blue, 128)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(normalMatrix, 0.0F, 1.0F, 0.0F)
                .next();
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