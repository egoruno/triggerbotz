package ru.triggerbot.helper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathAngle {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    /**
     * Простая проверка, видит ли игрок entity в радиусе distance (raycast-like).
     * Сохранена логика оригинала насколько возможно.
     */
    public static boolean rayTrace(float distance, float partialTicks, Entity entity) {
        if (mc.player == null || entity == null) return false;

        Vec3d eyePos = mc.player.getCameraPosVec(partialTicks);
        Vec3d look = mc.player.getRotationVec(partialTicks);
        Vec3d end = eyePos.add(look.x * distance, look.y * distance, look.z * distance);

        Box box = entity.getBoundingBox().expand(0.1);
        return box.raycast(eyePos, end).isPresent() || box.contains(eyePos);
    }
}
