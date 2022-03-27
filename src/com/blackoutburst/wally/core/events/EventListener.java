package com.blackoutburst.wally.core.events;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class EventListener extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        Ready.run(event);
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        if (event.getMember().getUser().isBot()) return;
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        if (event.getMember() == null) return;
        if (event.getMember().getUser().isBot()) return;
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        if (event.getMember() == null) return;
        if (event.getMember().getUser().isBot()) return;
        ReactionAdd.run(event);
    }

    @Override
    public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event) {
        if (event.getMember() == null) return;
        if (event.getMember().getUser().isBot()) return;
        ReactionRemove.run(event);
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getMember() == null) return;
        if (event.getMember().getUser().isBot()) return;
    }
}
