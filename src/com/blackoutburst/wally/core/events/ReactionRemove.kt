package com.blackoutburst.wally.core.events

import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.roles.RolesManager.getGenderRoleName
import com.blackoutburst.wally.core.roles.RolesManager.removeRole
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent

object ReactionRemove {

    fun run(event: MessageReactionRemoveEvent) {
        if (event.member == null) return
        if (event.messageId == ConfigManager.loadValue("messages", "gender message")) {
            removeRole(getGenderRoleName(event.emoji.toString()), event.member!!)
        }
    }
}