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

    public @Nonnull PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public @Nonnull
    CommandExecutable getExecutable() {
        return executable;
    }

    public @Nonnull String[] getLabels() {
        return labels;
    }
}
