package ru.triggerbot.util;

public class AttackCritical {

    private final SprintControl sprintControl = new SprintControl();

    public AttackCritical() {
        // SprintControl регистрируется сам через tick
    }

    public void preCritical() {
        if (sprintControl.getClient().player != null && sprintControl.getClient().player.isSprinting()) {
            sprintControl.setSprintResetTicks(1);

            sprintControl.getClient().options.sprintKey.setPressed(false);
            sprintControl.getClient().player.setSprinting(false);
        }
    }

    public boolean isSprinting() {
        return sprintControl.isServerSprint() &&
                sprintControl.getClient().player != null &&
                !sprintControl.getClient().player.isGliding() &&
                !sprintControl.getClient().player.isTouchingWater();
    }

    public SprintControl getSprintControl() {
        return sprintControl;
    }
}
