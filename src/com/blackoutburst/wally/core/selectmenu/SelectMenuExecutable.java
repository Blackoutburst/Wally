package com.blackoutburst.wally.core.selectmenu;

import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import javax.annotation.Nonnull;

public abstract class SelectMenuExecutable {

    protected abstract void onChange(@Nonnull SelectMenuInteractionEvent event);

}
