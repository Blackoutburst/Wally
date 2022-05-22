package com.blackoutburst.wally.core.selectmenu

import com.blackoutburst.wally.utils.EmbedUtils.generateSkin
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent

class SelectMenuSkin : SelectMenuExecutable() {

    override fun onChange(menuUser: WallyUser, event: SelectMenuInteractionEvent) {
        val uuid = event.selectedOptions[0].value
        val playerName = event.selectedOptions[0].label
        val embed = generateSkin(uuid, playerName)

        event.editMessageEmbeds(embed.build()).queue()
    }
}