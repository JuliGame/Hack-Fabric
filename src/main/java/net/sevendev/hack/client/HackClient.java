package net.sevendev.hack.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.sevendev.hack.Hacks.EspFeature;

public class HackClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        WorldRenderEvents.LAST.register(EspFeature::render);
    }
}
