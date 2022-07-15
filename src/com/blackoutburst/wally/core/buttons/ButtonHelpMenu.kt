package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.PermissionLevel
import com.blackoutburst.wally.utils.EmbedUtils
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.*

class ButtonHelpMenu : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        event.deferEdit().queue()
        if (buttonUser.permissionLevel.weight >= PermissionLevel.ADMIN.weight) {
            event.channel.sendMessageEmbeds(EmbedUtils.helpEmbed(buttonUser)).setActionRow(
                Button.danger(buttonUser.member.id + ":" + UUID.randomUUID(),"Admin commands"),
                Button.primary(buttonUser.member.id + ":" + UUID.randomUUID(),"Staff commands"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID(),"Hypixel commands"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID(),"Practice commands"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID(),"Misc commands")
            ).queue()
        } else if (buttonUser.permissionLevel.weight >= PermissionLevel.STAFF.weight) {
            event.channel.sendMessageEmbeds(EmbedUtils.helpEmbed(buttonUser)).setActionRow(
                Button.primary(buttonUser.member.id + ":" + UUID.randomUUID(),"Staff commands"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID(),"Hypixel commands"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID(),"Practice commands"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID(),"Misc commands")
            ).queue()
        } else {
            event.channel.sendMessageEmbeds(EmbedUtils.helpEmbed(buttonUser)).setActionRow(
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID(),"Hypixel commands"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID(),"Practice commands"),
                Button.secondary(buttonUser.member.id + ":" + UUID.randomUUID(),"Misc commands")
            ).queue()
        }
    }
}