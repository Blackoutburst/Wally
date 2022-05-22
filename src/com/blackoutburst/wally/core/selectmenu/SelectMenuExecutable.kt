package com.blackoutburst.wally.core.selectmenu

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent

abstract class SelectMenuExecutable {
    abstract fun onChange(menuUser: WallyUser, event: SelectMenuInteractionEvent)
}