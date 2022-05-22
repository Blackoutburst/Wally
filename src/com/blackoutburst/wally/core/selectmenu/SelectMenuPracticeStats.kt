package com.blackoutburst.wally.core.selectmenu

import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserFromUUID
import com.blackoutburst.wally.utils.MessageUtils.sendPracticeStats
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import java.nio.file.Paths
import java.lang.Exception
import com.blackoutburst.wally.core.Wally
import java.nio.file.Files

class SelectMenuPracticeStats : SelectMenuExecutable() {
    
    override fun onChange(menuUser: WallyUser, event: SelectMenuInteractionEvent) {
        val uuid = event.selectedOptions[0].value
        val wallyUser = getWallyUserFromUUID(uuid)
        val memberId = wallyUser!!.member.id
        val filePath = Paths.get("database/linked users/$memberId/accounts/$uuid/practice.json")
        var data = ""

        try {
            data = String(Files.readAllBytes(filePath))
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e.stackTrace)
        }

        event.deferEdit().queue()
        sendPracticeStats(menuUser, event.message, data, uuid, true)
    }
}