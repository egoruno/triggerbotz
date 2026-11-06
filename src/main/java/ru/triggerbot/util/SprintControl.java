package ru.triggerbot.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class SprintControl {

    private final MinecraftClient client = MinecraftClient.getInstance();
    private boolean serverSprint;
    private int sprintResetTicks;
    private boolean hasReset;

    public MinecraftClient getClient() {
        return client;
    }

    public boolean isServerSprint() {
        return serverSprint;
    }

    public void setSprintResetTicks(int ticks) {
        this.sprintResetTicks = ticks;
    }

    public void onPacket(ClientCommandC2SPacket packet) {
        switch (packet.getMode()) {
            case START_SPRINTING -> serverSprint = true;
            case STOP_SPRINTING -> serverSprint = false;
            default -> {}
        }
    }

    public void onTick() {
        if (client.player == null) return;

        if (sprintResetTicks > 0) {
            if (!hasReset) {
                hasReset = true;
            }
            client.player.setSprinting(false);
            sprintResetTicks--;
        } else if (hasReset) {
            client.options.sprintKey.setPressed(true);
            client.player.setSprinting(true);
            hasReset = false;
        }
    }
}
