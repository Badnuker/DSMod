package net.badnuker.dsmod.item.custom;

import net.badnuker.dsmod.entity.CollapsedCoreEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StellarSymphony extends Item {
    private static final int MAX_CORES = 4;

    public StellarSymphony(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            int coreCount = world.getEntitiesByClass(CollapsedCoreEntity.class, user.getBoundingBox().expand(10), entity -> true).size();
            if (coreCount < MAX_CORES) {
                Vec3d spawnPos = user.getPos().add(user.getRotationVector().multiply(2));
                CollapsedCoreEntity core = new CollapsedCoreEntity(EntityType.EXPERIENCE_ORB, world);
                core.setPosition(spawnPos);
                world.spawnEntity(core);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
