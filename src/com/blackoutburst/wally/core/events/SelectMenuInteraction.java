package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.buttons.ButtonManager;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.selectmenu.SelectMenu;
import com.blackoutburst.wally.core.selectmenu.SelectMenuManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

import javax.annotation.Nonnull;

public class SelectMenuInteraction {

    /**
     * Check when someone interacts with a button
     *
     * @param event The reaction event
     */
    protected static void run(@Nonnull SelectMenuInteractionEvent event) {
        if (event.getMember() == null) return;
        if (event.getSelectMenu().getId() == null) return;

        final String ownerID = event.getSelectMenu().getId().split(":")[0];

        if (!event.getMember().getId().equals(ownerID)) {
            event.reply(ConfigManager.getInstance().loadValue("replies", "unauthorized menu")).setEphemeral(true).queue();
            Wally.getInstance().logger.log(LogLevel.INFO, event.getMember().getEffectiveName()+
                    " couldn't use the menu ["+event.getSelectMenu().getId()+"]");
            return;
        }

        SelectMenuManager.getInstance().runAction(event);
    }
}
