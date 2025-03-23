package net.badnuker.dsmod.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Switch extends Item {
    public Switch(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() == Blocks.IRON_DOOR) {
            boolean isOpen = state.get(DoorBlock.OPEN);
            world.setBlockState(pos, state.with(DoorBlock.OPEN, !isOpen), 3);

            return ActionResult.success(world.isClient());
        }
        return super.useOnBlock(context);
    }
}
