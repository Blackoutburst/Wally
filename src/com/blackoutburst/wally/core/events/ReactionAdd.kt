package com.blackoutburst.wally.core.events

import com.blackoutburst.wally.core.roles.RolesManager.addRole
import com.blackoutburst.wally.core.roles.RolesManager.getGenderRoleName
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import com.blackoutburst.wally.core.config.ConfigManager

object ReactionAdd {
    /**
     * Check when someone reacts to specific messages such as the gender message
     *
     * @param event The reaction event
     */
    fun run(event: MessageReactionAddEvent) {
        if (event.member == null) return
        if (event.messageId == ConfigManager.loadValue("messages", "gender message")) {
            addRole(getGenderRoleName(event.reactionEmote.toString()), event.member!!)
        }
    }
}