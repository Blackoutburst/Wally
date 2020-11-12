package core;


import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import commands.Background;
import commands.Compare;
import commands.Config;
import commands.ForceTracker;
import commands.GetRole;
import commands.Help;
import commands.LeaderBoard;
import commands.Link;
import commands.Linked;
import commands.PBTester;
import commands.Pack;
import commands.Ping;
import commands.Profile;
import commands.Register;
import commands.Say;
import commands.SetTracker;
import commands.Stats;
import commands.ToggleLeaderboardInformation;
import commands.ToggleTrackerInformation;
import commands.Unlink;
import main.Main;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
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
		bot.enableIntents(GatewayIntent.GUILD_PRESENCES);
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
        	LeaderboardUpdate lead = new LeaderboardUpdate();
        	try {
        		tracker.start();
        		lead.start();
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
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		if (event.getMessageId().equals("773957012358168659")) {
			Member user = event.getGuild().getMember(event.getUser());
			Role role = event.getGuild().getRolesByName("Tournament Player", false).get(0);
			event.getGuild().addRoleToMember(user, role).complete();
			
		}
	}
	
	@Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
		if (event.getMessageId().equals("773957012358168659")) {
			Member user = event.getGuild().getMember(event.getUser());
			Role role = event.getGuild().getRolesByName("Tournament Player", false).get(0);
			event.getGuild().removeRoleFromMember(user, role).complete();
		}
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getMember().getUser().isBot()) return;
		if (!event.isFromType(ChannelType.PRIVATE)) {
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!help") || event.getMessage().getContentDisplay().split(" ")[0].equals("!h"))
				Help.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!pack"))
				Pack.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!stats") || event.getMessage().getContentDisplay().split(" ")[0].equals("!s"))
				Stats.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!ping"))
				Ping.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!compare") || event.getMessage().getContentDisplay().split(" ")[0].equals("!c"))
				Compare.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!getconfig"))
				Config.get(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!update"))
				Config.update(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!settracker"))
				SetTracker.set(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!showtracker"))
				SetTracker.show(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!say"))
				Say.talk(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!link"))
				Link.link(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!unlink"))
				Unlink.unlink(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!linked"))
				Linked.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!setbackground") || event.getMessage().getContentDisplay().split(" ")[0].equals("!setbg"))
				Background.set(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!resetbackground") || event.getMessage().getContentDisplay().split(" ")[0].equals("!rbg"))
				Background.reset(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!removebackground"))
				Background.remove(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!lead") || event.getMessage().getContentDisplay().split(" ")[0].equals("!lb") || event.getMessage().getContentDisplay().split(" ")[0].equals("!leaderboard"))
				LeaderBoard.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!forcepb"))
				PBTester.force(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!tli"))
				ToggleLeaderboardInformation.toggle(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!tti"))
				ToggleTrackerInformation.toggle(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!profile") || event.getMessage().getContentDisplay().split(" ")[0].equals("!p"))
				Profile.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!getrole"))
				GetRole.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!register"))
				Register.display(event);
			if (event.getMessage().getContentDisplay().split(" ")[0].equals("!forcetracker"))
				ForceTracker.toggle(event);
		}
    }
}
