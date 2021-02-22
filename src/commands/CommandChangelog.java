package commands;

import java.io.BufferedReader;
import java.io.FileReader;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

public class CommandChangelog extends CommandExecutable {

	public CommandChangelog(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("changelog.txt"));
			String changelog = "";
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				changelog += line + "\n";
			}
			reader.close();
			MessageSender.message(command, changelog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (true);
	}
}
