package com.blackoutburst.wally.core.buttons;

import javax.annotation.Nonnull;

public class Button {

    private final ButtonExecutable executable;

    private final String[] labels;

    public Button(@Nonnull ButtonExecutable executable, @Nonnull String... labels) {
        this.executable = executable;
        this.labels = labels;
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
    public @Nonnull String[] getLabels() {
        return labels;
    }

}
