package ru.triggerbot.helper;

public enum TargetType {
    PLAYER("Игроков"),
    MOB("Мобов"),
    ANIMAL("Животных"),
    STAND("Стойки для брони");

    private final String targetName;

    TargetType(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetName() {
        return targetName;
    }
}
