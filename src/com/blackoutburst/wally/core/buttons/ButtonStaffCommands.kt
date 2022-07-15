package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.utils.EmbedUtils.staffCommandsPage1
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.*

class ButtonStaffCommands : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        event.deferEdit().queue()
        event.channel.editMessageEmbedsById(event.messageId, staffCommandsPage1()).setActionRow(
            Button.success(buttonUser.member.id + ":" + UUID.randomUUID(), "Help menu"),
            Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID() + ":STAFF HELP","Next page")
        ).queue()
    }
}