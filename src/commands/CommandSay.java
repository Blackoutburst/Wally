package commands;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

public class CommandSay extends CommandExecutable {

	public CommandSay(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));
		
		String msg = "";
		
		for (String arg : command.getArgs()) {
			msg += arg+" ";
		}
		
		MessageSender.message(command, msg);
		command.getEvent().getMessage().delete().complete();
		return (true);
	}
}
