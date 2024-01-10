package net.sevendev.hack;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.sevendev.hack.Hacks.EspFeature;
import net.sevendev.hack.Hacks.FlyHack;
import net.sevendev.hack.Hacks.KillAura;

public class Hack implements ModInitializer {
    @Override
    public void onInitialize() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("xray")
            .executes(context -> {
                        EspFeature.BigUpdate(MinecraftClient.getInstance());
                        return 1;
                    }
            )));



        EspFeature.register("chest");
        KillAura.register();
        FlyHack.register();
    }

}
