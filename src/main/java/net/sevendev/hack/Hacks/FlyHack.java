package net.sevendev.hack.Hacks;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FlyHack {

    public static int FlyToggleKey = GLFW.GLFW_KEY_F;
    public static void register() {
        KeyBinding flyToggleKey = new KeyBinding("key.toggle_fly", InputUtil.Type.KEYSYM, FlyToggleKey, "category.your_mod");
        net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding(flyToggleKey);

        FlyHack flyHack = new FlyHack();
        ClientTickEvents.END_CLIENT_TICK.register(flyHack::onClientTick);
    }

    private static boolean isFlying = false;

    private static int startFlyDelay = 0;
    private void onClientTick(MinecraftClient client) {
        if (client.player == null) {
            return;
        }

        if (isFlyToggleKeyPressed()) {
            isFlying = !isFlying;
        }

        if (!isFlying)
            startFlyDelay = 0;

        if (startFlyDelay < 5)
            startFlyDelay++;

        boolean shouldFly = isFlying && (startFlyDelay < 0 || client.player.isSpectator() || client.player.isCreative());

        if (startFlyDelay >= 5) {
            startFlyDelay = -25;
        }


        if (shouldFly) {
            client.player.getAbilities().allowFlying = true;
            client.player.getAbilities().flying = true;
        } else {
            client.player.getAbilities().allowFlying = false;
            client.player.getAbilities().flying = false;
        }
    }

    private static boolean debounce = false;
    private static boolean isFlyToggleKeyPressed() {
        boolean isPressed = GLFW.glfwGetKey(MinecraftClient.getInstance().getWindow().getHandle(), FlyToggleKey) == GLFW.GLFW_PRESS;
        if (isPressed && !debounce) {
            debounce = true;
            return true;
        } else if (!isPressed) {
            debounce = false;
        }
        return false;
    }

}
