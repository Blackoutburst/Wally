package commands;

import core.Command;
import core.CommandExecutable;

public class CommandPoll extends CommandExecutable {

	public CommandPoll(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage(this));
		
		StringBuilder msg = new StringBuilder();
		
		for (String arg : command.getArgs()) {
			msg.append(arg).append(" ");
		}
		
		command.getChannel().sendMessage(msg.toString()).queue((message) -> {
			message.addReaction("U+1F44D").queue();
			message.addReaction("U+1F44E").queue();
		});
		command.getMessage().delete().complete();
		return (true);
	}
}
