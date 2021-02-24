package commands;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import core.Command;
import core.CommandExecutable;
import core.Request;
import utils.API;
import utils.Canvas;
import utils.MessageSender;
import utils.Stats;
import utils.Utils;

public class CommandCompare extends CommandExecutable {
	
	enum Type {
		WINS,
		WALLS,
		Q,
		F,
		T
	}
	
	public CommandCompare(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 1) return (badUsage());
		
		String player1 = null;
		String player2 = null;
		
		if (command.getArgs().length == 1) {
			if (Utils.isLinkedDiscord(command.getSender().getId())) {
				player1 = Request.getPlayerStatsUUID(Utils.getUUIDfromDiscord(command.getSender().getId()));
				
				player2 = Request.getPlayerStats(command.getArgs()[0]);
				if (player2 == null) return (unknownPlayer(command.getArgs()[0]));
				if (API.getPlayer(player2) == null) return (neverJoined(command.getArgs()[0]));
			} else {
				return (badUsage());
			}
		} else {
			player1 = Request.getPlayerStats(command.getArgs()[0]);
			if (player1 == null) return (unknownPlayer(command.getArgs()[0]));
			if (API.getPlayer(player1) == null) return (neverJoined(command.getArgs()[0]));
			
			player2 = Request.getPlayerStats(command.getArgs()[1]);
			if (player2 == null) return (unknownPlayer(command.getArgs()[1]));
			if (API.getPlayer(player2) == null) return (neverJoined(command.getArgs()[1]));
		}
		
		Canvas image = new Canvas(600, 400);

		setBackground(image);
		setSubtitle(image);
		setPlayer1(image, player1, player2);
		setPlayer2(image, player2, player1);
		image.save("compare.png");
		MessageSender.sendFile(command, "compare.png");
		return (true);
	}
	
	/**
	 * Set player subtitle
	 * @param image
	 */
	private void setSubtitle(Canvas image) {
		if (command.getArgs().length == 1) {
			image.drawStringLeft(Stats.getSubTitle(Utils.getIGNfromDiscord(command.getSender().getId())), 10, 70, 26, Color.white);
			image.drawStringRight(Stats.getSubTitle(command.getArgs()[0]), 590, 70, 26, Color.white);
		} else {
			image.drawStringLeft(Stats.getSubTitle(command.getArgs()[0]), 10, 70, 26, Color.white);
			image.drawStringRight(Stats.getSubTitle(command.getArgs()[1]), 590, 70, 26, Color.white);
		}
	}
	
	/**
	 * Set canvas background
	 * @param image
	 */
	private void setBackground(Canvas image) {
		Canvas half = new Canvas(300, 400);
		
		if (command.getArgs().length == 1) {
			half.drawImage(Utils.getCustomBackground(Utils.getIGNfromDiscord(command.getSender().getId())), 0, 0, 600, 400);
			half.save("half.png");
			
			image.drawCustomBackground(Utils.getCustomBackground(command.getArgs()[0]), 0, 0, 600, 400);
			image.drawCustomBackground("half.png", 0, 0, 300, 400);
		} else {
			half.drawImage(Utils.getCustomBackground(command.getArgs()[0]), 0, 0, 600, 400);
			half.save("half.png");
			
			image.drawCustomBackground(Utils.getCustomBackground(command.getArgs()[1]), 0, 0, 600, 400);
			image.drawCustomBackground("half.png", 0, 0, 300, 400);
		}
	}
	
	/**
	 * Set player 1 information
	 * @param image
	 * @param player1
	 * @param player2
	 */
	private void setPlayer1(Canvas image, String p1, String p2) {
		String wins = "Wins: " + API.getWins(p1) + " (" + diff(p1, p2, Type.WINS) + ")";
		String walls = "Walls: " + API.getWalls(p1) + " (" + diff(p1, p2, Type.WALLS) + ")";
		String qualification = "Qualification: " + API.getQualification(p1) + " (" + diff(p1, p2, Type.Q) + ")";
		String finals = "Final: " + API.getFinals(p1) + " (" + diff(p1, p2, Type.F) + ")";
		String total = "Q/F Total: " + API.getTotal(p1) + " (" + diff(p1, p2, Type.T) + ")";
		
		image.drawImage("res/win.png", 10, 105, 24, 24);
		image.drawImage("res/wall.png", 10, 155, 24, 24);
		image.drawImage("res/q.png", 10, 205, 24, 24);
		image.drawImage("res/f.png", 10, 255, 24, 24);
		image.drawImage("res/total.png", 10, 305, 24, 24);
		
		
		if (API.getName(p1).equals("Blackoutburst")) {
			image.drawImage("res/blackout.png", 10, 10, 200, 53);
		} else {
			image.drawStringLeft(API.getName(p1), 10, 40, 32, Color.white);
		}
		image.drawStringLeft(wins, 50, 125, 24, stringColor(p1, p2, Type.WINS));
		image.drawStringLeft(walls, 50, 175, 24, stringColor(p1, p2, Type.WALLS));
		image.drawStringLeft(qualification, 50, 225, 24, stringColor(p1, p2, Type.Q));
		image.drawStringLeft(finals, 50, 275, 24, stringColor(p1, p2, Type.F));
		image.drawStringLeft(total, 50, 325, 24, stringColor(p1, p2, Type.T));
	}
	
	/**
	 * Set player 1 information
	 * @param image
	 * @param player1
	 * @param player2
	 */
	private void setPlayer2(Canvas image, String p1, String p2) {
		String wins = "(" + diff(p1, p2, Type.WINS) + ") "+ API.getWins(p1) + "  :Wins";
		String walls = "(" + diff(p1, p2, Type.WALLS) + ") " + API.getWalls(p1) + " :Walls";
		String qualification = "(" + diff(p1, p2, Type.Q) + ") " + API.getQualification(p1) + " :Qualification";
		String finals = "(" + diff(p1, p2, Type.F) + ") " + API.getFinals(p1) + " :Finals";
		String total = "(" + diff(p1, p2, Type.T) + ") " + API.getTotal(p1) + " :Q/F Total";
		
		image.drawImage("res/win.png", 566, 105, 24, 24);
		image.drawImage("res/wall.png", 566, 155, 24, 24);
		image.drawImage("res/q.png", 566, 205, 24, 24);
		image.drawImage("res/f.png", 566, 255, 24, 24);
		image.drawImage("res/total.png", 566, 305, 24, 24);
		
		if (API.getName(p1).equals("Blackoutburst")) {
			image.drawImage("res/blackout.png", 390, 10, 200, 53);
		} else {
			image.drawStringRight(API.getName(p1), 590, 40, 32, Color.white);
		}
		image.drawStringRight(wins, 550, 125, 24, stringColor(p1, p2, Type.WINS));
		image.drawStringRight(walls, 550, 175, 24, stringColor(p1, p2, Type.WALLS));
		image.drawStringRight(qualification, 550, 225, 24, stringColor(p1, p2, Type.Q));
		image.drawStringRight(finals, 550, 275, 24, stringColor(p1, p2, Type.F));
		image.drawStringRight(total, 550, 325, 24, stringColor(p1, p2, Type.T));
	}
	
	/**
	 * Get string color if value is positive negative or null
	 * @param p1
	 * @param p2
	 * @return
	 */
	private Color stringColor(String p1, String p2, Type t) {
		Color color = Color.white;
		
		color = diff(p1, p2, t).charAt(0) == '-' ? new Color(255, 140, 140): new Color(126, 255, 112);
		if (diff(p1, p2, t).charAt(0) == '0') color = Color.white;
		return (color);
	}
	
	/**
	 * Return difference between two value
	 * @param stat1
	 * @param stat2
	 * @return
	 */
	private String diff(String p1, String p2, Type type) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		DecimalFormat formatter = (DecimalFormat) nf;
		int d1 = 0;
		int d2 = 0;
		
		switch(type) {
			case WINS: 
				d1 = Integer.valueOf(API.getWinsToInt(p1));
				d2 = Integer.valueOf(API.getWinsToInt(p2));
			break;
			case WALLS:
				d1 = Integer.valueOf(API.getWallsToInt(p1));
				d2 = Integer.valueOf(API.getWallsToInt(p2));
			break;
			case Q: 
				d1 = Integer.valueOf(API.getQualificationToInt(p1));
				d2 = Integer.valueOf(API.getQualificationToInt(p2));
			break;
			case F: 
				d1 = Integer.valueOf(API.getFinalsToInt(p1));
				d2 = Integer.valueOf(API.getFinalsToInt(p2));
			break;
			case T: 
				d1 = Integer.valueOf(API.getTotalToInt(p1));
				d2 = Integer.valueOf(API.getTotalToInt(p2));
			break;
			default: 
				d1 = Integer.valueOf(API.getWinsToInt(p1));
				d2 = Integer.valueOf(API.getWinsToInt(p2));
			break;
		}
		return (formatter.format(d1 - d2));
	}
}
