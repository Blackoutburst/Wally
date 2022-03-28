package com.blackoutburst.wally.core.logger;

public enum LogLevel {
    INFO(0),
    WARN(1),
    ERROR(2),
    NONE(3);

    private final int weight;

    LogLevel(int weight) {
        this.weight = weight;
    }

    /**
     * Get the permission weight
     * @return the permission weight
     */
    public int getWeight() {
        return weight;
    }
}