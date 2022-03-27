package com.blackoutburst.wally.core.commands;

public class Command {

    private final PermissionLevel permissionLevel;

    private final CommandExecutable executable;

    private final String[] labels;

    public Command(PermissionLevel permissionLevel, CommandExecutable executable, String... labels) {
        this.permissionLevel = permissionLevel;
        this.executable = executable;
        this.labels = labels;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public CommandExecutable getExecutable() {
        return executable;
    }

    public String[] getLabels() {
        return labels;
    }
}
