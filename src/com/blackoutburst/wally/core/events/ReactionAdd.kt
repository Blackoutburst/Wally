package com.blackoutburst.wally.core.events

import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.roles.RolesManager.addRole
import com.blackoutburst.wally.core.roles.RolesManager.getGenderRoleName
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent

object ReactionAdd {

    fun run(event: MessageReactionAddEvent) {
        if (event.member == null) return
        if (event.messageId == ConfigManager.loadValue("messages", "gender message")) {
            addRole(getGenderRoleName(event.emoji.toString()), event.member!!)
        }
    }
}