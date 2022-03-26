package com.blackoutburst.wally.core;

import com.blackoutburst.wally.core.events.Ready;
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
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
    }

    @Override
    public void onMessageReactionRemove(@Nonnull MessageReactionRemoveEvent event) {
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    }
}
