package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.utils.EmbedUtils.adminCommandsPage2
import com.blackoutburst.wally.utils.EmbedUtils.miscCommandsPage2
import com.blackoutburst.wally.utils.EmbedUtils.staffCommandsPage2
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.*

class ButtonCommandsNextPage : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        val type = event.button.id!!.split(":").last()
        event.deferEdit().queue()
        if (type == "ADMIN HELP") {
            event.channel.editMessageEmbedsById(event.messageId, adminCommandsPage2()).setActionRow(
                Button.success(buttonUser.member.id + ":" + UUID.randomUUID(), "Help menu"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID() + ":ADMIN HELP","Previous page")
            ).queue()
        } else if (type == "STAFF HELP") {
            event.channel.editMessageEmbedsById(event.messageId, staffCommandsPage2()).setActionRow(
                Button.success(buttonUser.member.id + ":" + UUID.randomUUID(), "Help menu"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID() + ":STAFF HELP","Previous page")
            ).queue()
        } else if (type == "MISC HELP") {
            event.channel.editMessageEmbedsById(event.messageId, miscCommandsPage2()).setActionRow(
                Button.success(buttonUser.member.id + ":" + UUID.randomUUID(), "Help menu"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID() + ":MISC HELP","Previous page")
            ).queue()
        }
    }
}