package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.utils.EmbedUtils.miscCommandsPage1
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.*

class ButtonMiscCommands : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        event.deferEdit().queue()
        event.channel.editMessageEmbedsById(event.messageId, miscCommandsPage1()).setActionRow(
            Button.success(buttonUser.member.id + ":" + UUID.randomUUID(), "Help menu"),
            Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID() + ":MISC HELP","Next page")
        ).queue()
    }
}