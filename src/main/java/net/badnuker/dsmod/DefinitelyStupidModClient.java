package net.badnuker.dsmod;

import net.badnuker.dsmod.client.render.entity.CollapsedCoreRenderer;
import net.badnuker.dsmod.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DefinitelyStupidModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.COLLAPSED_CORE, CollapsedCoreRenderer::new);
    }
}
