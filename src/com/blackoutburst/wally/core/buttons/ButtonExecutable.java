package com.blackoutburst.wally.core.buttons;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;

public abstract class ButtonExecutable {

    protected abstract boolean onClick(@Nonnull ButtonInteractionEvent event);

}
