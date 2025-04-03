package net.badnuker.dsmod.entity;

import net.badnuker.dsmod.DefinitelyStupidMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<CollapsedCore> COLLAPSED_CORE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(DefinitelyStupidMod.MOD_ID, "collapsed_core"),
            FabricEntityTypeBuilder.<CollapsedCore>create(SpawnGroup.MISC, CollapsedCore::new).build()
    );

    public static void registerModEntities() {
        DefinitelyStupidMod.LOGGER.info("Registering Entities");
    }
}
