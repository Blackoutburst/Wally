package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONObject;

import core.Command;
import core.CommandExecutable;
import core.Request;
import core.RolesManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import utils.API;
import utils.MessageSender;
import utils.Utils;

public class CommandLink extends CommandExecutable {

	public CommandLink(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 1) return (badUsage(this));

		int discord = 0;
		int ign = 1;
		
		try {
			Long.valueOf(command.getArgs()[0]);
		} catch(Exception e) {
			discord = 1;
			ign = 0;
		}
		
		try {
			command.getEvent().getGuild().getMemberById(command.getArgs()[discord]);
		} catch(Exception e) {
			return (badUsage(this));
		}
		
		if (command.getEvent().getGuild().getMemberById(command.getArgs()[discord]) == null) return (unknownMember(this, command.getArgs()[discord]));
		String data = Request.getPlayerStats(command.getArgs()[ign]);
		if (data == null) return (unknownPlayer(this, command.getArgs()[ign]));
		if (API.getPlayer(data) == null) return (neverJoined(this, command.getArgs()[ign]));
		
		generateFiles(data, ign);
		setRole(data, discord);
		
		MessageSender.messageJSON(command, "link");
		return (true);
	}
	
	/**
	 * Set user role from his score
	 * @param data
	 */
	private void setRole(String data, int discord) {
		Guild guild = command.getEvent().getGuild();
		Member member = command.getEvent().getGuild().getMemberById(command.getArgs()[discord]);
		int Q = API.getQualificationToInt(data);
		int F = API.getFinalsToInt(data);
		
		new RolesManager().addClubRole(guild, member, Q, F);
	}
	
	/**
	 * Generate new user files
	 * @param data
	 */
	private void generateFiles(String data, int ign) {
		String uuid = Request.getPlayerUUID(command.getArgs()[ign]);
		JSONObject obj = new JSONObject();
		new File("linked player/" + uuid).mkdir();
		
		obj.put("name", API.getName(data));
		obj.put("subtitle", "");
		obj.put("wins", API.getWins(data));
		obj.put("walls", API.getWalls(data));
		obj.put("qualification", API.getQualification(data));
		obj.put("finals", API.getFinals(data));
		obj.put("discordid", command.getArgs()[0]);
		obj.put("uuid", uuid);
		
		try {
			PrintWriter writer = new PrintWriter("linked player/" + uuid + "/data.json");
			writer.write(obj.toString(4));
			writer.close();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Utils.addToLeaderBoard(uuid, data, command);
	}
}
