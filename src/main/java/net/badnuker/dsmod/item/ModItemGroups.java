package net.badnuker.dsmod.item;

import net.badnuker.dsmod.DefinitelyStupidMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup DS_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(DefinitelyStupidMod.MOD_ID, "ds_group"),
            ItemGroup.create(null, -1).displayName(Text.translatable("itemGroup.test_group"))
                    .icon(() -> new ItemStack(ModItems.STELLAR_SYMPHONY))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.STELLAR_SYMPHONY);
                        entries.add(ModItems.SWITCH);
                        entries.add(ModItems.BRUSH);
                    }).build());

    public static void registerModItemsGroups() {
        DefinitelyStupidMod.LOGGER.info("Registering Item Groups");
    }
}
