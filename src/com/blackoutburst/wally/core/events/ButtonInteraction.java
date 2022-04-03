package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.buttons.ButtonManager;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;

public class ButtonInteraction {

    /**
     * Check when someone interacts with a button
     *
     * @param event The reaction event
     */
    protected static void run(@Nonnull ButtonInteractionEvent event) {
        if (event.getMember() == null) return;
        if (event.getButton().getId() == null) return;

        final String ownerID = event.getButton().getId().split(":")[0];
        final MessageChannel messageChannel = event.getChannel();

        if (!event.getMember().getId().equals(ownerID)) {
            messageChannel.sendMessage("<@"+event.getMember().getId()+">"+
                    ConfigManager.getInstance().loadValue("replies", "unauthorized button")).queue();
            Wally.getInstance().logger.log(LogLevel.INFO, event.getMember().getEffectiveName()+
                    " couldn't use the button ["+event.getButton().getLabel()+"]");
            return;
        }

        ButtonManager.getInstance().runAction(event);
    }
}
