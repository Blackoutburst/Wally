package commands;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CommandPreChangelog extends CommandExecutable {

	public CommandPreChangelog(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (!new File("changelog.txt").exists()) return (missingFile(this));

		try {
			BufferedReader reader = new BufferedReader(new FileReader("changelog.txt"));
			StringBuilder changelog = new StringBuilder();
			String line;
			
			while ((line = reader.readLine()) != null) {
				changelog.append(line).append("\n");
			}
			reader.close();
			MessageSender.message(command, changelog.toString());
			if (command.getMessage() != null)
				command.getMessage().delete().complete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (true);
	}
}
