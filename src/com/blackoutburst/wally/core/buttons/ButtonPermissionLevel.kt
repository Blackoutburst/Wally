package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.PermissionLevel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.DatabaseUtils.generateWallyProfile
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserFromUUID
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.*
import java.util.concurrent.TimeUnit

class ButtonPermissionLevel : ButtonExecutable() {
    
    private fun updateUserPermissionLevel(event: ButtonInteractionEvent, discordID: String, level: PermissionLevel): Boolean {
        val file = "database/linked users/$discordID/data.json"
        val data = getWallyUserData(discordID)

        if (data == null) {
            event.reply(ConfigManager.loadMessage( "not linked or corrupted")).setEphemeral(true).queue()
            return false
        }

        data.permissionLevel = level
        generateWallyProfile(data, file)
        return true
    }

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        val editedUserID = event.button.id!!.split(":").toTypedArray()[1]
        val id = buttonUser.member.id + ":" + editedUserID + ":"
        val level = PermissionLevel.valueOf(event.button.label)
        val data = getWallyUserData(editedUserID)
        val member = getWallyUserFromUUID(data!!.mainAccountUUID)

        if (data == null) {
            event.reply(ConfigManager.loadMessage( "not linked or corrupted")).setEphemeral(true)
                .queue()
            return
        }

        event.message.editMessage(ConfigManager.loadMessage( "select permission level")
            .replace("%user%", member!!.member.effectiveName)).setActionRow(
                if (level == PermissionLevel.MEMBER) Button.primary(id + UUID.randomUUID(),"MEMBER") else Button.secondary(id + UUID.randomUUID(), "MEMBER"),
                if (level == PermissionLevel.STAFF) Button.primary(id + UUID.randomUUID(),"STAFF" ) else Button.secondary(id + UUID.randomUUID(), "STAFF"),
                if (level == PermissionLevel.ADMIN) Button.primary(id + UUID.randomUUID(),"ADMIN") else Button.secondary(id + UUID.randomUUID(), "ADMIN")
            ).queue { r: Message ->
                r.delete().queueAfter(ConfigManager.loadValue("settings", "answer lifetime").toLong(), TimeUnit.SECONDS)
            }

        if (updateUserPermissionLevel(event, editedUserID, level)) {
            event.reply(ConfigManager.loadMessage( "user permission updated")).setEphemeral(true).queue()
            Wally.loadUser()
        }
    }
}