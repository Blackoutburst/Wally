package com.blackoutburst.wally.core.commands;

public enum PermissionLevel {
    MEMBER(0),
    STAFF(1),
    ADMIN(2);

    private final int weight;

    PermissionLevel(int weight) {
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
