package core;

import javax.security.auth.login.LoginException;

import event.Generic;
import event.GuildMemberJoin;
import event.GuildMessageReactionAdd;
import event.GuildMessageReactionRemove;
import event.MessageReceived;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot extends ListenerAdapter {
	
	public static Guild server;
	
	public Bot(String token, String activity) throws LoginException {
		JDABuilder bot = JDABuilder.createDefault(token);
		
		bot.setActivity(Activity.playing(activity));
		bot.addEventListeners(this);
		bot.setChunkingFilter(ChunkingFilter.ALL);
		bot.setMemberCachePolicy(MemberCachePolicy.ALL);
		bot.enableIntents(GatewayIntent.GUILD_MEMBERS);
		bot.enableIntents(GatewayIntent.GUILD_PRESENCES);
		bot.build();
	}
	
	@Override
	public void onGenericEvent(GenericEvent event) {
		if (event instanceof ReadyEvent) {
			new Generic().run(event);
			new Tracker();
			new LeaderboardUpdater();
		}
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		new GuildMemberJoin().run(event);
	}
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		new GuildMessageReactionAdd().run(event);
	}
	
	@Override
	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
		new GuildMessageReactionRemove().run(event);
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		new MessageReceived().run(event);
	}
}
