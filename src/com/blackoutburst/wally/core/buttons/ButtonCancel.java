package com.blackoutburst.wally.core.buttons;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;

public class ButtonCancel extends ButtonExecutable {

    @Override
    protected void onClick(@Nonnull ButtonInteractionEvent event) {
        event.getMessage().delete().queue();
    }
}
