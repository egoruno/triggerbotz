package ru.triggerbot.helper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;

public class TargetFinder {
    private final MinecraftClient client = MinecraftClient.getInstance();

    public Entity getCrossTarget(TargetType... targetTypes) {
        if (client.crosshairTarget instanceof EntityHitResult hitResult) {
            Entity entity = hitResult.getEntity();

            if (hasAccessTarget(entity, targetTypes)) return entity;
        }

        return null;
    }

    public boolean hasAccessTarget(Entity entity, TargetType... targetTypes) {
        for (TargetType type : targetTypes) {
            if (matches(entity, type)) {
                return true;
            }
        }
        return false;
    }

    private boolean matches(Entity entity, TargetType type) {
        return switch (type) {
            case PLAYER -> entity instanceof PlayerEntity && entity != client.player;
            case MOB -> entity instanceof MobEntity;
            case ANIMAL -> entity instanceof AnimalEntity;
            case STAND -> entity instanceof ArmorStandEntity;
        };
    }
}
