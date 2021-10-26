package commands;

import java.io.File;

import org.json.JSONObject;

import core.Command;
import core.CommandExecutable;
import utils.GeneralUtils;
import utils.MessageSender;

public class CommandResetFontColor extends CommandExecutable {

	public CommandResetFontColor(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		File index = new File("linked player");
		String[]entries = index.list();
		
		for (String s: entries) {
			File playerFolder = new File(index.getPath(),s);
			JSONObject obj = GeneralUtils.readJson(playerFolder + "/data.json");
			
			if (obj.getString("discordid").equals(command.getSender().getId()) &&
				new File(playerFolder + "/fontColor").exists()) {
				new File(playerFolder + "/fontColor").delete();
				MessageSender.message(command, "Default font color restored");
				return (true);
			}
		}
		MessageSender.message(command, "Default font color restored");
		if (!GeneralUtils.isLinkedDiscord(command.getSender().getId()))
			MessageSender.messageJSONMention(command, "not linked");

		return (true);
	}
}
