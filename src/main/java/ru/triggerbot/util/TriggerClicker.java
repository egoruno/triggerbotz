package ru.triggerbot.util;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Hand;
import ru.triggerbot.helper.PlayerInteractionHelper;

public class TriggerClicker {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final CooldownClicker clicker = new CooldownClicker();
    private final AttackCritical attackCritical = new AttackCritical();
    private boolean onlyCritical, smartCritical;

    public void updateCriticalSettings(boolean onlyCritical, boolean smartCritical) {
        this.onlyCritical = onlyCritical;
        this.smartCritical = smartCritical;
    }

    public void predictCritical() {
        if (canAttack() || hasDistanceFix()) {
            attackCritical.preCritical();
        }
    }

    public void onAttackEntity(Entity entity) {
        if (canAttack() || hasDistanceFix()) {
            attackCritical.preCritical();
        }

        if (!canAttack()) return;

        if (!attackCritical.isSprinting()) {
            attackEntity(entity);
        }
    }

    public void resetClickTime() {
        clicker.resetClickTime();
    }

    private void attackEntity(Entity entity) {
        clicker.resetClickTime();

        if (client.interactionManager != null && client.player != null) {
            client.interactionManager.attackEntity(client.player, entity);
            client.player.swingHand(Hand.MAIN_HAND);
        }
    }

    private boolean canAttack() {
        if (!clicker.isCooldownPassed()) return false;

        boolean noRestrict = !hasMovementRestrictions();

        if (smartCritical && onlyCritical) {
            if (noRestrict) {
                return canCrit() || (client.player != null && client.player.isOnGround());
            } else {
                return true;
            }
        }

        if (onlyCritical && !hasMovementRestrictions()) {
            return canCrit();
        }
        return true;
    }

    private boolean hasMovementRestrictions() {
        if (client.player == null) return true;

        return client.player.hasStatusEffect(StatusEffects.BLINDNESS)
                || client.player.hasStatusEffect(StatusEffects.LEVITATION)
                || PlayerInteractionHelper.isBoxInBlock(client.player.getBoundingBox().expand(-1e-3), Blocks.COBWEB)
                || client.player.isSubmergedInWater()
                || client.player.isInLava()
                || client.player.isClimbing()
                || !PlayerInteractionHelper.canChangeIntoPose(EntityPose.STANDING, client.player.getPos())
                || client.player.getAbilities().flying;
    }

    private boolean canCrit() {
        return client.player != null &&
                !client.player.isOnGround() &&
                client.player.fallDistance > 0;
    }

    private boolean hasDistanceFix() {
        return client.player != null &&
                !client.player.isOnGround() &&
                client.player.fallDistance < 0.0001;
    }

    public AttackCritical getAttackCritical() {
        return attackCritical;
    }
}
