package com.blackoutburst.wally.core.buttons;

import javax.annotation.Nonnull;

public class Button {

    private final ButtonExecutable executable;

    private final String label;

    public Button(@Nonnull ButtonExecutable executable, @Nonnull String label) {
        this.executable = executable;
        this.label = label;
    }

    /**
     * Get the button executable
     *
     * @return the button executable
     */
    public @Nonnull ButtonExecutable getExecutable() {
        return executable;
    }

    /**
     * Get the button label
     *
     * @return the button label
     */
    public @Nonnull String getLabel() {
        return label;
    }

}
