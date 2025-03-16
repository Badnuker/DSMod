package net.badnuker.dsmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class CollapsedCoreEntity extends ExperienceOrbEntity {
    private int ticksLived = 0;
    private LivingEntity target;

    public CollapsedCoreEntity(EntityType<? extends ExperienceOrbEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        ticksLived++;

        if (ticksLived >= 40) { // 2 seconds (20 ticks per second)
            if (target == null) {
                target = findNearestHostile();
            }
            if (target != null) {
                double dx = target.getX() - this.getX();
                double dy = target.getY() - this.getY();
                double dz = target.getZ() - this.getZ();
                this.setVelocity(dx * 0.1, dy * 0.1, dz * 0.1);
            }
        }

        // 检测碰撞
        if (target != null && this.getBoundingBox().intersects(target.getBoundingBox())) {
            target.damage(this.getDamageSources().magic(), 4.0f); // 造成伤害
            this.discard(); // 销毁实体
        }

        if (ticksLived >= 400) {
            this.discard();
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
    }

    private LivingEntity findNearestHostile() {
        World world = this.getWorld();
        if (world == null) {
            return null;
        }

        // 获取附近的实体
        List<LivingEntity> nearbyEntities = world.getEntitiesByClass(
                LivingEntity.class, // 目标实体类型
                this.getBoundingBox().expand(10), // 搜索范围
                entity -> entity instanceof LivingEntity && !(entity instanceof PlayerEntity) // 过滤条件
        );

        // 如果没有找到符合条件的实体，返回 null
        if (nearbyEntities.isEmpty()) {
            return null;
        }

        // 找到最近的实体
        LivingEntity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;
        Vec3d currentPos = this.getPos();

        for (LivingEntity entity : nearbyEntities) {
            double distance = currentPos.squaredDistanceTo(entity.getPos());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestEntity = entity;
            }
        }

        return closestEntity;
    }
}