package com.blackoutburst.wally.core.events

import com.blackoutburst.wally.core.WallyUser.Companion.getUser
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.selectmenu.SelectMenuManager

object SelectMenuInteraction {
    /**
     * Check when someone interacts with a button
     *
     * @param event The reaction event
     */
    fun run(event: SelectMenuInteractionEvent) {
        if (event.member == null) return
        if (event.selectMenu.id == null) return

        val ownerID = event.selectMenu.id!!.split(":").toTypedArray()[0]

        if (event.member!!.id != ownerID) {
            event.reply(ConfigManager.loadValue("replies", "unauthorized menu")).setEphemeral(true).queue()
            Wally.logger.log(LogLevel.INFO, event.member!!.effectiveName +" couldn't use the menu [" + event.selectMenu.id + "]")
            return
        }

        val member = event.member ?: return
        val menuUser = getUser(member)
        SelectMenuManager.runAction(menuUser, event)
    }
}