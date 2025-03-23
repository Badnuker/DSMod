package net.badnuker.dsmod.item;

import net.badnuker.dsmod.DefinitelyStupidMod;
import net.badnuker.dsmod.item.custom.StellarSymphony;
import net.badnuker.dsmod.item.custom.Switch;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item StellarSymphony = registerItems("stellar_symphony", new StellarSymphony(new Item.Settings().maxCount(1)));
    public static final Item Switch = registerItems("switch", new Switch(new Item.Settings().maxCount(1)));

    private static Item registerItems(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(DefinitelyStupidMod.MOD_ID, id), item);
    }

    public static void registerModItems() {
        DefinitelyStupidMod.LOGGER.info("Registering Items");
    }
}
