package com.blackoutburst.wally.core.events

import com.blackoutburst.wally.core.WallyUser.Companion.getUser
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.buttons.ButtonManager

object ButtonInteraction {

    /**
     * Check when someone interacts with a button
     *
     * @param event The reaction event
     */
    internal fun run(event: ButtonInteractionEvent) {
        if (event.member == null) return
        if (event.button.id == null) return
        val ownerID = event.button.id!!.split(":").toTypedArray()[0]

        if (event.member!!.id != ownerID) {
            event.reply(ConfigManager.loadValue("replies", "unauthorized button")).setEphemeral(true).queue()
            Wally.logger.log(LogLevel.INFO, event.member!!.effectiveName + " couldn't use the button [" + event.button.label + "]")
            return
        }

        val member = event.member ?: return
        val buttonUser = getUser(member)
        ButtonManager.runAction(buttonUser, event)
    }
}