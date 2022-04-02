package com.blackoutburst.wally.core.events;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import javax.annotation.Nonnull;

public class ButtonInteraction {

    /**
     * Check when someone interacts with a button
     *
     * @param event The reaction event
     */
    protected static void run(@Nonnull ButtonInteractionEvent event) {
        if (event.getMember() == null) return;
    }
}
