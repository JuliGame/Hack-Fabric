package net.sevendev.hack.Mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.sevendev.hack.Hacks.KillAura;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerAttackEventMixin {
    @Inject(method = "attack", at = @At("HEAD"))
    private void onTryAttack(Entity target, CallbackInfo ci) {
        if (KillAura.instance.isValid(target)) {
            if (KillAura.instance.target == target)
                return;

            KillAura.instance.target = (LivingEntity) target;
            MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("Nuevo target: " + target.getName().getString()));
        }

    }

}
