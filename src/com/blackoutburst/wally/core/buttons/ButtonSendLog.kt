package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.logger.LogLevel
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import java.io.File

class ButtonSendLog : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        Wally.logger.log(LogLevel.WARN,"Log file uploaded")
        event.channel.sendFile(File(Wally.logger.currentLogFile)).queue()
        event.message.delete().queue()
    }
}