package net.sevendev.hack.Hacks;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.sevendev.hack.utils.PacketUtil;
import net.sevendev.hack.utils.RotationUtil;

import java.util.Comparator;
import java.util.List;


public class KillAura {
    MinecraftClient mc = MinecraftClient.getInstance();

    public static KillAura instance;
    public static void register() {
        KillAura killAura = new KillAura();
        instance = killAura;

        // EventUpdate
        ClientTickEvents.END_WORLD_TICK.register(killAura::onClientTick);
    }


    public LivingEntity target = null;

    private void onClientTick(ClientWorld clientWorld) {

        // if right click is pressed
        if (!mc.options.useKey.isPressed())
            return;
        if (!(MinecraftClient.getInstance().player.getInventory().getMainHandStack().getName().toString().toLowerCase().contains("sword")
                || MinecraftClient.getInstance().player.getInventory().getMainHandStack().getName().toString().toLowerCase().contains("axe")))
            return;

//        if (target != null && !target.isAlive()) target = null;
//
//        if(target == null)
//            return;

        List<LivingEntity> targets = nearestTarget();
        if(targets.isEmpty()) return;
        LivingEntity target = targets.get(0);

        rotate(target);

        if(mc.player.getAttackCooldownProgress(0.5f) == 1) {
            mc.interactionManager.attackEntity(mc.player, target);
            if(mc.options.useKey.isPressed()) {
                // Downcast to Animations module for swinging
//                ((Animations) ModuleManager.INSTANCE.getModuleByClass(Animations.class)).swing();
                PacketUtil.sendPacket(new HandSwingC2SPacket(mc.player.getActiveHand()));
            }
            else mc.player.swingHand(mc.player.getActiveHand());
        }
    }

    private void rotate(Entity target) {
        float[] rotations = RotationUtil.getRotations(target);
        PacketUtil.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], mc.player.isOnGround()));
        mc.player.bodyYaw = rotations[0];
        mc.player.headYaw = rotations[0];
    }


    boolean rangePriority = false;
    float range = 4.5f;
    boolean attackPlayers = true;
    boolean attackMobs = true;
    boolean attackPassive = false;
    private List<LivingEntity> nearestTarget() {
        Comparator<LivingEntity> comparator = rangePriority ? Comparator.comparingDouble(entity -> entity.distanceTo(mc.player)) : Comparator.comparingDouble(LivingEntity::getHealth);

        return mc.world.getEntitiesByClass(LivingEntity.class, mc.player.getBoundingBox().expand(range), this::isValid)
                .stream().sorted(comparator).toList();
    }

    public boolean isValid(Entity entity) {
        if(entity == mc.player) return false;
        if(!entity.isAlive()) return false;
//        else if(entity instanceof PlayerEntity &&  !attackPlayers) return false;
//        else if(entity instanceof PassiveEntity && !attackPassive) return false;
//        else if(entity instanceof HostileEntity && !attackMobs) return false;
//        else if (!(entity instanceof LivingEntity)) return false;

        if (entity instanceof VillagerEntity) return true;
        else return false;
//        else return true;
    }

}
