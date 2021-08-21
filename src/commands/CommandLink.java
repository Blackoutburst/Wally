package commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

import core.Command;
import core.CommandExecutable;
import core.Request;
import core.RolesManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import utils.API;
import utils.MessageSender;
import utils.GeneralUtils;

public class CommandLink extends CommandExecutable {

	public CommandLink(Command command, boolean admin, String errorMessage) {
		super(command, admin, errorMessage);
	}

	@Override
	protected boolean execute() {
		if (command.getArgs().length < 1) return (badUsage(this));

		int discord = 0;
		int ign = 1;
		
		// Check if arg 1 is the same size as a discord
		// uuid which is too long for a minecraft ign
		if(command.getArgs()[1].length() == 18) {
			discord = 1;
			ign = 0;
		}
		
		try {
			command.getMessage().getGuild().getMemberById(command.getArgs()[discord]);
		} catch(Exception e) {
			return (badUsage(this));
		}
		
		if (command.getMessage().getGuild().getMemberById(command.getArgs()[discord]) == null) return (unknownMember(this, command.getArgs()[discord]));
		String data = Request.getPlayerStats(command.getArgs()[ign]);
		if (data == null) return (unknownPlayer(this, command.getArgs()[ign]));
		if (API.getPlayer(data) == null) return (neverJoined(this, command.getArgs()[ign]));
		
		generateFiles(data, ign, discord);
		setRole(data, discord);
		
		return (true);
	}
	
	/**
	 * Set user role from his score
	 * @param data
	 */
	private void setRole(String data, int discord) {
		Guild guild = command.getMessage().getGuild();
		Member member = command.getMessage().getGuild().getMemberById(command.getArgs()[discord]);
		int Q = API.getQualificationToInt(data);
		int F = API.getFinalsToInt(data);
		
		new RolesManager().addClubRole(guild, member, Q, F, command);
	}
	
	/**
	 * Generate new user files
	 * @param data
	 */
	private void generateFiles(String data, int ign, int discord) {
		String uuid = Request.getPlayerUUID(command.getArgs()[ign]);
		JSONObject obj = new JSONObject();
		new File("linked player/" + uuid).mkdir();
		
		obj.put("name", API.getName(data));
		obj.put("subtitle", "");
		obj.put("wins", API.getWins(data));
		obj.put("walls", API.getWalls(data));
		obj.put("qualification", API.getQualification(data));
		obj.put("finals", API.getFinals(data));
		obj.put("discordid", command.getArgs()[discord]);
		obj.put("uuid", uuid);
		
		try {
			Files.write(Paths.get("linked player/" + uuid + "/data.json"), obj.toString(4).getBytes());
			MessageSender.messageJSON(command, "link");
		} catch (Exception e) {
			e.printStackTrace();
			MessageSender.message(command, "There was an error while trying to link this user");
		}
		GeneralUtils.addToLeaderBoard(uuid, data, command);
	}
}
