package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.core.commands.CommandExecutable;

import javax.annotation.Nonnull;

public class Button {

    private final ButtonExecutable executable;

    private final String label;

    public Button(@Nonnull ButtonExecutable executable, @Nonnull String label) {
        this.executable = executable;
        this.label = label;
    }

    public @Nonnull ButtonExecutable getExecutable() {
        return executable;
    }

    public @Nonnull String getLabel() {
        return label;
    }

}
