package commands;

import java.awt.Color;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.API;
import utils.Canvas;
import utils.MessageSender;
import utils.Stats;
import utils.Utils;

public class CommandStats extends CommandExecutable {

	public CommandStats(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		String data = null;
		
		String uuid = null;
		
		if (command.getArgs().length == 0) {
			if (Utils.isLinkedDiscord(command.getSender().getId())) {
				data = Request.getPlayerStatsUUID(Utils.getUUIDfromDiscord(command.getSender().getId()));
				uuid = Utils.getUUIDfromDiscord(command.getSender().getId());
			} else {
				return (badUsage());
			}
		} else {
			data = Request.getPlayerStats(command.getArgs()[0]);
			if (data == null) return (unknownPlayer(command.getArgs()[0]));
			if (API.getPlayer(data) == null) return (unknownPlayer(command.getArgs()[0]));
			uuid = API.getUUID(data);
		}
		
		Canvas image = new Canvas(600, 400);
		
		if (command.getArgs().length == 0) {
			image.drawCustomBackground(Utils.getCustomBackground(Utils.getIGNfromDiscord(command.getSender().getId())), 0, 0, 600, 400);
			image.drawStringCenter(Stats.getSubTitle(Utils.getIGNfromDiscord(command.getSender().getId())), 300, 70, 26, Color.white);
		} else {
			image.drawCustomBackground(Utils.getCustomBackground(command.getArgs()[0]), 0, 0, 600, 400);
			image.drawStringCenter(Stats.getSubTitle(command.getArgs()[0]), 300, 70, 26, Color.white);
		}
		
		createImage(image, data);
		MessageSender.sendFile(command, "stats.png");
		Utils.addToLeaderBoard(uuid, data, command);
		return (true);
	}
	
	/**
	 * Create stats image
	 * @param image
	 * @param data
	 */
	private void createImage(Canvas image, String data) {
		image.drawImage("res/win.png", 100, 105, 24, 24);
		image.drawImage("res/wall.png", 100, 155, 24, 24);
		image.drawImage("res/q.png", 100, 205, 24, 24);
		image.drawImage("res/f.png", 100, 255, 24, 24);
		image.drawImage("res/total.png", 100, 305, 24, 24);
		
		
		if (API.getName(data).equals("Blackoutburst")) {
			image.drawImage("res/blackout.png", 200, 10, 200, 53);
		} else {
			image.drawStringCenter(API.getName(data), 300, 40, 32, Color.white);
		}
		
		image.drawStringLeft("Wins: " + API.getWins(data) + Utils.getLBPos(API.getName(data), 'w'), 150, 125, 24, Color.white);
		image.drawStringLeft("Walls cleared: " + API.getWalls(data) + Utils.getLBPos(API.getName(data), 'r'), 150, 175, 24, Color.white);
		image.drawStringLeft("Best qualification score: " + API.getQualification(data) + Utils.getLBPos(API.getName(data), 'q'), 150, 225, 24, Color.white);
		image.drawStringLeft("Best final score: " + API.getFinals(data) + Utils.getLBPos(API.getName(data), 'f'), 150, 275, 24, Color.white);
		image.drawStringLeft("Q/F total: " + API.getTotal(data) + Utils.getLBPos(API.getName(data), 't'), 150, 325, 24, Color.white);
		image.save("stats.png");
	}
}
