package commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;

import core.Bot;
import core.Command;
import core.CommandExecutable;
import utils.ConfigManager;
import utils.MessageSender;

public class CommandChangelog extends CommandExecutable {

	public CommandChangelog(Command command, boolean admin, String errorMessage) {
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
			Objects.requireNonNull(Bot.server.getTextChannelById(ConfigManager.getString("changelogChannel"))).sendMessage(changelog.toString()).complete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (true);
	}
}
