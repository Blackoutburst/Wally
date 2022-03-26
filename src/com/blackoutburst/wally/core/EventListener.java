package com.blackoutburst.wally.core;

import com.sun.demo.jvmti.hprof.Tracker;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sun.net.www.content.text.Generic;

import javax.annotation.Nonnull;

public class EventListener extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
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
