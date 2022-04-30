package com.blackoutburst.wally.core.selectmenu;


import javax.annotation.Nonnull;

public class SelectMenu {

    private final SelectMenuExecutable executable;

    private final String[] labels;

    public SelectMenu(@Nonnull SelectMenuExecutable executable, @Nonnull String... labels) {
        this.executable = executable;
        this.labels = labels;
    }

    /**
     * Get the button executable
     *
     * @return the button executable
     */
    public @Nonnull SelectMenuExecutable getExecutable() {
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
