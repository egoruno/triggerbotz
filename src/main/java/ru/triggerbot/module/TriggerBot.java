package ru.triggerbot.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import ru.triggerbot.helper.MathAngle;
import ru.triggerbot.helper.TargetFinder;
import ru.triggerbot.helper.TargetType;
import ru.triggerbot.util.TriggerClicker;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class TriggerBot {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final TargetFinder finder = new TargetFinder();
    private final TriggerClicker clicker = new TriggerClicker();

    private boolean enabled = false;

    // Только игроки
    private final Set<TargetType> selectedTargets = EnumSet.of(
            TargetType.PLAYER
    );

    private boolean onlyCritical = true;
    private boolean smartCritical = false;

    public void toggle() {
        enabled = !enabled;
        if (!enabled) {
            clicker.resetClickTime();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        // Predict critical (как в оригинале)
        for (Entity entity : mc.world.getEntities()) {
            if (MathAngle.rayTrace(3.2F, 0, entity)) {
                if (finder.hasAccessTarget(entity, getSelectedTypes())) {
                    clicker.predictCritical();
                }
            }
        }

        clicker.updateCriticalSettings(onlyCritical, smartCritical);

        Entity entity = finder.getCrossTarget(getSelectedTypes());

        if (entity != null) {
            clicker.onAttackEntity(entity);
        }
    }

    private TargetType[] getSelectedTypes() {
        return selectedTargets.toArray(new TargetType[0]);
    }

    // Можно потом добавить команды/конфиг для изменения настроек
    public void setOnlyCritical(boolean value) {
        this.onlyCritical = value;
    }

    public void setSmartCritical(boolean value) {
        this.smartCritical = value;
    }

    public TriggerClicker getClicker() {
        return clicker;
    }
}

