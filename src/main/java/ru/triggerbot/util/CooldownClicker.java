package ru.triggerbot.util;

import net.minecraft.client.MinecraftClient;

public class CooldownClicker {

    private long lastClickTime = System.currentTimeMillis();
    private final MinecraftClient client = MinecraftClient.getInstance();

    public boolean isCooldownPassed() {
        if (leftClickPassed() < 230) return false;

        return client.player != null && client.player.getAttackCooldownProgress(1.0F) >= 0.92F;
    }

    private long leftClickPassed() {
        return System.currentTimeMillis() - lastClickTime;
    }

    public void resetClickTime() {
        lastClickTime = System.currentTimeMillis();
    }
}
