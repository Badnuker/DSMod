package net.badnuker.dsmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class CollapsedCore extends Entity {
    private PlayerEntity owner;
    private int coreAge;
    private boolean targeting;

    public CollapsedCore(World world, PlayerEntity owner) {
        super(ModEntities.COLLAPSED_CORE, world);
        this.owner = owner;
    }

    public CollapsedCore(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if (owner == null) {
            return;
        }

        coreAge++;

        Box box = this.getBoundingBox();
        List<Entity> entities = world.getOtherEntities(this, box,
                entity -> entity instanceof LivingEntity && entity != owner && !((LivingEntity) entity).isTeammate(owner));

        if (!entities.isEmpty()) {
            Entity target = entities.get(0);
            if (target instanceof LivingEntity livingTarget) {
                livingTarget.damage(world.getDamageSources().magic(), 5.0f);
                this.discard();
                coreAge = 0;
                return;
            }
        }

        if (coreAge > 20) {
            findTarget();
        }

        if (!targeting) {
            if (this.distanceTo(owner) < 1) {
                orbitOwner();
            } else {
                returnToOwner();
            }
        }

        if (coreAge > 200) {
            this.discard();
        }
    }

    private void orbitOwner() {
        Vec3d orbitPos = owner.getPos().add(Math.cos(coreAge * 0.1) * 2, 1, Math.sin(coreAge * 0.1) * 2);
        this.setPosition(orbitPos);
    }

    private void findTarget() {
        World world = this.getWorld();
        LivingEntity target = world.getClosestEntity(
                LivingEntity.class,
                TargetPredicate.createAttackable().setPredicate(e ->
                        e != owner && !e.isTeammate(owner)
                ),
                owner,
                owner.getX(),
                owner.getY(),
                owner.getZ(),
                new Box(owner.getBlockPos()).expand(20)
        );

        if (target != null) {
            this.setVelocity(target.getPos().subtract(this.getPos()).normalize().multiply(0.5));
            targeting = true;
        } else {
            targeting = false;
        }
    }

    private void returnToOwner() {
        Vec3d direction = owner.getPos().subtract(this.getPos()).normalize();
        this.setVelocity(direction.multiply(0.3));
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}