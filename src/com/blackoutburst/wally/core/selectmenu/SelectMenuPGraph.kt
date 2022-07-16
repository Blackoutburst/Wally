package com.blackoutburst.wally.core.selectmenu

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.MessageUtils.sendPGraph
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent

class SelectMenuPGraph : SelectMenuExecutable() {

    override fun onChange(menuUser: WallyUser, event: SelectMenuInteractionEvent) {
        val type = event.selectedOptions[0].value.split(":").toTypedArray()[0]
        val uuid = event.selectedOptions[0].value.split(":").toTypedArray()[1]
        val data = DatabaseUtils.getLocalData(uuid, DatabaseUtils.DataType.PRACTICE)

        event.deferEdit().queue()
        sendPGraph(menuUser, data!!, event.message, type, event.selectedOptions[0].label, true)
    }
}