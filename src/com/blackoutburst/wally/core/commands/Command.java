package com.blackoutburst.wally.core.commands;

import javax.annotation.Nonnull;

public class Command {

    private final PermissionLevel permissionLevel;

    private final CommandExecutable executable;

    private final String[] labels;

    public Command(@Nonnull PermissionLevel permissionLevel, @Nonnull CommandExecutable executable, @Nonnull String... labels) {
        this.permissionLevel = permissionLevel;
        this.executable = executable;
        this.labels = labels;
    }

    /**
     * Get the command permission level
     *
     * @return the command permission level
     */
    public @Nonnull PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    /**
     * Get the command executable
     *
     * @return the command executable
     */
    public @Nonnull CommandExecutable getExecutable() {
        return executable;
    }

    /**
     * Get the command labels
     *
     * @return the command labels
     */
    public @Nonnull String[] getLabels() {
        return labels;
    }
}
