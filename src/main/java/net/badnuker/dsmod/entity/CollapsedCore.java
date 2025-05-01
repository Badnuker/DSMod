package net.badnuker.dsmod.entity;

import net.badnuker.dsmod.DefinitelyStupidMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class CollapsedCore extends Entity {
    private PlayerEntity owner;
    private LivingEntity target;
    private int coreAge;

    public CollapsedCore(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (owner == null) {
            this.discard();
            return;
        }

        World world = this.getWorld();
        coreAge++;

        if (coreAge % 40 == 1) {
            String output = String.format("当前实体位置：%.1f,%.1f,%.1f | owner：%s | targeting：%s",
                    this.getX(), this.getY(), this.getZ(),
                    owner == null ? "不存在" : owner.getName(),
                    target == null ? "No" : "Yes"
            );
            DefinitelyStupidMod.LOGGER.info(output);
            if (target != null) {
                output = String.format("锁定目标 %s | 位置：%.1f,%.1f,%.1f",
                        target.getType().getUntranslatedName(),
                        target.getX(),
                        target.getY(),
                        target.getZ()
                );
                DefinitelyStupidMod.LOGGER.info(output);
            }
        }

        List<Entity> entities = world.getOtherEntities(this, new Box(this.getBlockPos()).expand(1),
                entity -> entity instanceof LivingEntity && entity != owner && !entity.isTeammate(owner));

        if (!entities.isEmpty()) {
            Entity t = entities.get(0);
            if (t instanceof LivingEntity livingTarget) {
                livingTarget.damage(world.getDamageSources().magic(), 5.0f);
                this.discard();
                return;
            }
        }

        if (coreAge > 20) {
            findTarget();
            if (target != null && target.isAlive()) {
                moveToLivingEntity(target);
            } else if (this.distanceTo(owner) < 1) {
                orbitOwner();
            } else {
                moveToLivingEntity(owner);
            }
        }


        if (coreAge > 400) {
            this.discard();
        }
    }

    public static TypedActionResult<ItemStack> spawn(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            CollapsedCore core = new CollapsedCore(ModEntities.COLLAPSED_CORE, world);
            core.owner = user;
            core.setPos(user.getX(), user.getY(), user.getZ());
            world.spawnEntity(core);
        }
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }

    private void orbitOwner() {
        Vec3d orbitPos = owner.getPos().add(Math.cos(coreAge * 0.1) * 2, 1, Math.sin(coreAge * 0.1) * 2);
        this.setPosition(orbitPos);
    }

    private void findTarget() {
        if (owner == null) {
            return;
        }
        World world = this.getWorld();
        target = world.getClosestEntity(
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
    }

    private void moveToLivingEntity(LivingEntity entity) {
        Vec3d vec3d = new Vec3d(
                entity.getX() - this.getX(),
                entity.getY() + (double) entity.getStandingEyeHeight() / (double) 2.0F - this.getY(),
                entity.getZ() - this.getZ()
        ).normalize().multiply(0.3);

        this.setVelocity(vec3d);
        this.move(MovementType.SELF, this.getVelocity());
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return super.createSpawnPacket();
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