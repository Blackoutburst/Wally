package com.blackoutburst.wally.core.commands;

public enum PermissionLevel {
    BANNED(-1),
    MEMBER(0),
    PATREON(1),
    STAFF(2),
    ADMIN(3);

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
