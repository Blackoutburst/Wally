package com.blackoutburst.wally.core.buttons;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class ButtonCancel extends ButtonExecutable{

    @Override
    protected void onClick(@NotNull ButtonInteractionEvent event) {
        event.getMessage().delete().queue();
    }
}
