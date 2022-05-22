package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import com.blackoutburst.wally.core.Wally
import java.io.File

class ButtonSendLog : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        event.channel.sendFile(File(Wally.logger.currentLogFile)).queue()
        event.message.delete().queue()
    }
}