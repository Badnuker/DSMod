package net.badnuker.dsmod.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Brush extends Item {
    public Brush(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        world.breakBlock(pos, false); // 破坏方块但不生成掉落物
        return ActionResult.SUCCESS;
    }
}
