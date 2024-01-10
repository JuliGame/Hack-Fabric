package net.sevendev.hack.Mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.sevendev.hack.Hacks.KillAura;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LivingEntity.class)
public abstract class LivingEntityGlowMixing {
    @Overwrite
    public boolean isGlowing() {
        LivingEntity me = (LivingEntity) (Object) this;
//        if (me.equals(KillAura.instance.target))
//            return true;

        // call original method
        return me.hasStatusEffect(StatusEffects.GLOWING);
    }
}