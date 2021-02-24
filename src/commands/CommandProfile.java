package commands;

import core.Command;
import core.CommandExecutable;
import utils.MessageSender;

public class CommandProfile extends CommandExecutable {

	public CommandProfile(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		MessageSender.messageMention(command, "This command is disabled for now sorry"); 
		
		return (true);
		
		/*
		String data = null;
		
		if (command.getArgs().length == 0) {
			if (Utils.isLinkedDiscord(command.getSender().getId())) {
				data = Request.getPlayerStatsUUID(Utils.getUUIDfromDiscord(command.getSender().getId()));
			} else {
				return (badUsage());
			}
		} else {
			data = Request.getPlayerStats(command.getArgs()[0]);
			if (data == null) return (unknownPlayer(command.getArgs()[0]));
			if (API.getPlayer(data).equals(null)) return (neverJoined(command.getArgs()[0]));
		}
		
		Canvas image = new Canvas(600, 200);
		
		if (command.getArgs().length == 0) {
			image.drawCustomBackground(Utils.getCustomBackground(Utils.getIGNfromDiscord(command.getSender().getId())), 0, 0, 600, 400);
			image.drawStringCenter(Stats.getSubTitle(Utils.getIGNfromDiscord(command.getSender().getId())), 300, 70, 26, Color.white);
			Request.getLevelBar(Utils.getIGNfromDiscord(command.getSender().getId()));
			Request.getAPBar(Utils.getIGNfromDiscord(command.getSender().getId()));
		} else {
			image.drawCustomBackground(Utils.getCustomBackground(command.getArgs()[0]), 0, 0, 600, 400);
			image.drawStringCenter(Stats.getSubTitle(command.getArgs()[0]), 300, 70, 26, Color.white);
			Request.getLevelBar(command.getArgs()[0]);
			Request.getAPBar(command.getArgs()[0]);

		}
		
		createImage(image, data);
		MessageSender.sendFile(command, "profile.png");
		return (true);
		*/
	}
	
	/*
	 * Create profile image
	 * @param image
	 * @param data
	
	private void createImage(Canvas image, String data) {
		image.drawImage("level.png", 20, 80, 560, 25);
		image.drawImage("ap.png", 20, 140, 560, 25);
		
		if (API.getName(data).equals("Blackoutburst")) {
			image.drawImage("res/blackout.png", 200, 10, 200, 53);
		} else {
			image.drawStringCenter(API.getName(data), 300, 40, 32, Color.white);
		}
		image.save("profile.png");
	}*/
}
