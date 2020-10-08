package core;


import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import commands.Compare;
import commands.Config;
import commands.Help;
import commands.Link;
import commands.Linked;
import commands.Pack;
import commands.Ping;
import commands.Say;
import commands.SetTracker;
import commands.Stats;
import commands.Unlink;
import main.Main;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot extends ListenerAdapter {
	private JDABuilder bot;
	
	public static int request = 0;
	
	/**
	 * Log the bot and set the activity
	 * @param token
	 * @param activity
	 * @throws LoginException
	 * @throws InterruptedException 
	 * @author Blackoutburst
	 */
	public void login(String token, String activity) throws LoginException, InterruptedException {
		bot = JDABuilder.createDefault(token);
		bot.setActivity(Activity.playing(activity));
		bot.addEventListeners(new Bot());
		bot.setChunkingFilter(ChunkingFilter.ALL);
		bot.setMemberCachePolicy(MemberCachePolicy.ALL);
		bot.enableIntents(GatewayIntent.GUILD_MEMBERS);
		bot.build();
		
		Runnable runnable = new Runnable() {
		    public void run() {
		        request = 0;
		    }
		};
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(runnable , 0, 1, TimeUnit.MINUTES);
		
	}
	
	@Override
    public void onGenericEvent(GenericEvent event)
    {
        if (event instanceof ReadyEvent) {
        	Tracker tracker = new Tracker();
        	tracker.server = event.getJDA().getGuildById(Main.serverID);
        	try {
        		tracker.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		if (event.getMember().getUser().isBot()) return;
		event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("Members", false).get(0)).complete();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getMember().getUser().isBot()) return;
		if (!event.isFromType(ChannelType.PRIVATE)) {
			if (event.getMessage().getContentDisplay().startsWith("!help"))
				Help.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!pack"))
				Pack.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!stats"))
				Stats.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!ping"))
				Ping.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!compare"))
				Compare.display(event);
			if (event.getMessage().getContentDisplay().startsWith("!getconfig"))
				Config.get(event);
			if (event.getMessage().getContentDisplay().startsWith("!update"))
				Config.update(event);
			if (event.getMessage().getContentDisplay().startsWith("!settracker"))
				SetTracker.set(event);
			if (event.getMessage().getContentDisplay().startsWith("!showtracker"))
				SetTracker.show(event);
			if (event.getMessage().getContentDisplay().startsWith("!say"))
				Say.talk(event);
			if (event.getMessage().getContentDisplay().startsWith("!link") && !event.getMessage().getContentDisplay().contains("!linked"))
				Link.link(event);
			if (event.getMessage().getContentDisplay().startsWith("!unlink"))
				Unlink.unlink(event);
			if (event.getMessage().getContentDisplay().startsWith("!linked"))
				Linked.display(event);
		}
    }
}
