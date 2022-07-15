package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.utils.EmbedUtils.adminCommandsPage1
import com.blackoutburst.wally.utils.EmbedUtils.miscCommandsPage1
import com.blackoutburst.wally.utils.EmbedUtils.staffCommandsPage1
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.*

class ButtonCommandsPreviousPage : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        val type = event.button.id!!.split(":").last()
        event.deferEdit().queue()
        if (type == "ADMIN HELP") {
            event.channel.editMessageEmbedsById(event.messageId, adminCommandsPage1()).setActionRow(
                Button.success(buttonUser.member.id + ":" + UUID.randomUUID(), "Help menu"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID() + ":ADMIN HELP","Next page")
            ).queue()
        } else if (type == "STAFF HELP") {
            event.channel.editMessageEmbedsById(event.messageId, staffCommandsPage1()).setActionRow(
                Button.success(buttonUser.member.id + ":" + UUID.randomUUID(), "Help menu"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID() + ":STAFF HELP","Next page")
            ).queue()
        } else if (type == "MISC HELP") {
            event.channel.editMessageEmbedsById(event.messageId, miscCommandsPage1()).setActionRow(
                Button.success(buttonUser.member.id + ":" + UUID.randomUUID(), "Help menu"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID() + ":MISC HELP","Next page")
            ).queue()
        }
    }
}