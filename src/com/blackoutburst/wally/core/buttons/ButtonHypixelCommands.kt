package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.utils.EmbedUtils.hypixelCommands
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent

class ButtonHypixelCommands : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        event.deferEdit().queue()
        event.channel.editMessageEmbedsById(event.messageId, hypixelCommands()).queue()
    }
}