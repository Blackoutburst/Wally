package commands;

import core.Command;
import core.CommandExecutable;

public class CommandPoll extends CommandExecutable {

	public CommandPoll(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length == 0) return (badUsage());
		
		String msg = "";
		
		for (String arg : command.getArgs()) {
			msg += arg+" ";
		}
		
		command.getEvent().getChannel().sendMessage(msg).queue((message) -> {
			message.addReaction("U+1F44D").queue();
			message.addReaction("U+1F44E").queue();
		});
		command.getEvent().getMessage().delete().complete();
		return (true);
	}
}
