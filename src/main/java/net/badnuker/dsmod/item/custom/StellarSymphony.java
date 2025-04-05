package net.badnuker.dsmod.item.custom;

import net.badnuker.dsmod.entity.CollapsedCore;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StellarSymphony extends Item {
    public StellarSymphony(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            return CollapsedCore.spawn(world, user, hand);
        }
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }
}
