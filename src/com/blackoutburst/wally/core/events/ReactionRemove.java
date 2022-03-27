package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.roles.RolesManager;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import javax.annotation.Nonnull;

public class ReactionRemove {

    /**
     * Check when someone reacts to specific messages such as the gender message
     *
     * @param event The reaction event
     */
    protected static void run(@Nonnull MessageReactionRemoveEvent event) {
        if (event.getMember() == null) return;

        if (event.getMessageId().equals(ConfigManager.getInstance().loadValue("messages", "gender message"))) {
            RolesManager.removeRole(RolesManager.getGenderRoleName(event.getReactionEmote().toString()), event.getMember());
        }
    }
}
