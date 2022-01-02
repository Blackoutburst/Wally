package commands;

import core.Command;
import core.CommandExecutable;
import core.Tracker;
import utils.MessageSender;

public class CommandForceTracker extends CommandExecutable {

	public CommandForceTracker(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		Tracker.forced = !Tracker.forced;
		MessageSender.message(command, "Tracker forced: **" + Tracker.forced + "**");
		return (true);
	}
}
