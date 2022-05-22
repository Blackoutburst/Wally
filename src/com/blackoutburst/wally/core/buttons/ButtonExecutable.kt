package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent

abstract class ButtonExecutable {

    abstract fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent)
}