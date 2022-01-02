package commands;

import java.awt.Color;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import core.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class CommandPing extends CommandExecutable {

	public CommandPing(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
        final long time = System.currentTimeMillis();
        final Runtime rt = Runtime.getRuntime();
        final long memTotal = rt.totalMemory();
        final long memMax = rt.maxMemory();
        final long memLeft = rt.freeMemory();
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        final DecimalFormat formatter = (DecimalFormat) nf;
        final long memUsed = memTotal - memLeft;
        final String mbTotal = formatter.format(memTotal / 1048576);
        final String mbMax = formatter.format(memMax / 1048576);
        final String mbUsed =  formatter.format(memUsed / 1048576);
        final RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
        final String uptime = formatter.format(mxBean.getUptime() / 60000);
        final String iconURL = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
        final String wallyURL = "https://github.com/Blackoutburst/Wally";
		final boolean trackerThread = Tracker.trackerThread.isAlive();
		final boolean pTrackerThread = PTracker.ptrackerThread.isAlive();
		final boolean lbThread = LeaderboardUpdater.lbupdaterThread.isAlive();

        final MessageEmbed tmp = new EmbedBuilder()
                .setAuthor("Wally status", wallyURL, iconURL)
                .setColor(new Color(71, 165, 255))
                .setDescription("Pong!").build();
		
		command.getChannel().sendMessage(tmp).queue(response -> {
			
	           final MessageEmbed embed = new EmbedBuilder()
	                    .setAuthor("Wally status", wallyURL, iconURL)
	                    .setColor(new Color(71, 165, 255))
	                    .addField("Used memory", "`"+mbUsed+"`MiB", true)
	                    .addField("Total memory", "`"+mbTotal+"`MiB", true)
	                    .addField("Max memory", "`"+mbMax+"`MiB", true)
	                    .addField("Ping", "`"+(System.currentTimeMillis() - time)+"`ms", true)
	                    .addField("Uptime", "`"+uptime+"` minutes", true)
					   .addField("Tacker", "alive: `"+trackerThread+"`", false)
					   .addField("PTracker", "alive: `"+pTrackerThread+"`", true)
					   .addField("Updater", "alive: `"+lbThread+"`", true).build();
			
			response.editMessage(embed).queue();
		});
		return (true);
	}
}
