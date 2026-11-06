package ru.triggerbot.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;


public class PlayerInteractionHelper {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean isBoxInBlock(Box box, Block block) {
        if (mc.world == null) return false;

        int minX = (int) Math.floor(box.minX);
        int minY = (int) Math.floor(box.minY);
        int minZ = (int) Math.floor(box.minZ);
        int maxX = (int) Math.floor(box.maxX);
        int maxY = (int) Math.floor(box.maxY);
        int maxZ = (int) Math.floor(box.maxZ);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = mc.world.getBlockState(pos);
                    if (state.isOf(block)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean canChangeIntoPose(EntityPose pose, Vec3d pos) {
        if (mc.player == null || mc.world == null) return false;

        // Упрощённая проверка — в оригинале была более сложная
        Box box = mc.player.getBoundingBox();
        return mc.world.isSpaceEmpty(mc.player, box.contract(1.0E-7));
    }
}

