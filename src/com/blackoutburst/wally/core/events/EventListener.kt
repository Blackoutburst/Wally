package com.blackoutburst.wally.core.events

import com.blackoutburst.wally.core.events.ButtonInteraction.run
import com.blackoutburst.wally.core.events.MessageReceived.run
import com.blackoutburst.wally.core.events.ReactionAdd.run
import com.blackoutburst.wally.core.events.ReactionRemove.run
import com.blackoutburst.wally.core.events.Ready.run
import com.blackoutburst.wally.core.events.SelectMenuInteraction.run
import com.blackoutburst.wally.core.roles.RolesManager.addRole
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EventListener : ListenerAdapter() {

    override fun onReady(event: ReadyEvent) {
        run(event)
    }

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        if (event.member.user.isBot) return
        //if (event.member.user.id == "696055080629043201") return
        addRole("Members", event.member)
    }

    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (event.member == null) return
        if (event.member!!.user.isBot) return
        //if (event.member!!.user.id == "696055080629043201") return
        run(event)
    }

    override fun onMessageReactionRemove(event: MessageReactionRemoveEvent) {
        if (event.member == null) return
        if (event.member!!.user.isBot) return
        //if (event.member!!.user.id == "696055080629043201") return
        run(event)
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        if (event.member == null) return
        //if (event.member!!.user.id == "696055080629043201") return
        run(event)
    }

    override fun onSelectMenuInteraction(event: SelectMenuInteractionEvent) {
        if (event.member == null) return
        //if (event.member!!.user.id == "696055080629043201") return
        run(event)
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.member == null) return
        if (event.member!!.user.isBot) return
        //if (event.member!!.user.id == "696055080629043201") return
        run(event)
    }
}