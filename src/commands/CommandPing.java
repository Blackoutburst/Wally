package commands;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import core.Command;
import core.CommandExecutable;
import core.LeaderboardUpdater;
import core.Tracker;

public class CommandPing extends CommandExecutable {

	public CommandPing(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		long time = System.currentTimeMillis();
		Runtime rt = Runtime.getRuntime();
		long memTotal = rt.totalMemory();
		long memMax = rt.maxMemory();
		long memLeft = rt.freeMemory();
		long memUsed = memTotal - memLeft;
		long mbTotal = memTotal / 1048576;
		long mbMax = memMax / 1048576;
		double mbUsed = (double) memUsed / 1048576;
		RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
		long uptime = mxBean.getUptime() / 60000;
		String mainThread = Thread.currentThread().isAlive() ? "alive" : "down";
		String trackerThread = Tracker.trackerThread.isAlive() ? "alive" : "down";
		String lbThread = LeaderboardUpdater.lbupdaterThread.isAlive() ? "alive" : "down";
		String trackerForced = Tracker.forced ? "true" : "false"; 
		
		String formatString = "Ping: **%d** ms\nMemory: **%.2f / %d / %d** MiB\nUptime: **%d** minutes\nThreads: Main **"+mainThread+
				"** / Tracker **"+
				trackerThread+
				"** / LBUpdater **"+
				lbThread+"**\nTracker forced: "+
				"**"+trackerForced+"**";
		
		command.getChannel().sendMessage("Pong!").queue(response -> {
			response.editMessageFormat(formatString, System.currentTimeMillis() - time, mbUsed, mbTotal, mbMax, uptime).queue();
		});
		return (true);
	}
}
