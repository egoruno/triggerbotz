package ru.triggerbot;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import ru.triggerbot.module.TriggerBot;

public class TriggerBotMod implements ClientModInitializer {

    public static final String MOD_ID = "triggerbot";
    public static KeyBinding toggleKey;
    public static final TriggerBot triggerBot = new TriggerBot();

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.triggerbot.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.triggerbot"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                triggerBot.toggle();
                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal("TriggerBot: " + (triggerBot.isEnabled() ? "§aON" : "§cOFF")),
                            true
                    );
                }
            }

            if (triggerBot.isEnabled()) {
                triggerBot.onTick();
            }

            // SprintControl tick всегда (нужен для reset)
            triggerBot.getClicker().getAttackCritical().getSprintControl().onTick();
        });
    }
}

