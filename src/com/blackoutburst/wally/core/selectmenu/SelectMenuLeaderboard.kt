package com.blackoutburst.wally.core.selectmenu

import com.blackoutburst.wally.utils.LeaderboardUtils.getLeaderboardFromIndex
import com.blackoutburst.wally.utils.MessageUtils.sendLeaderboard
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent

class SelectMenuLeaderboard : SelectMenuExecutable() {

    override fun onChange(menuUser: WallyUser, event: SelectMenuInteractionEvent) {
        val index = event.selectedOptions[0].value.split(":").toTypedArray()[0].toInt()
        val page = event.selectedOptions[0].value.split(":").toTypedArray()[1].toInt()
        val leaderboard = getLeaderboardFromIndex(index)

        event.deferEdit().queue()
        sendLeaderboard(menuUser, event.message, leaderboard, page, true)
    }
}