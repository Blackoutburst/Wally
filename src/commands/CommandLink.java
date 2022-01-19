package commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import core.*;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONObject;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import utils.*;

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
			welcomeEmbed(data, uuid, command.getArgs()[discord]);
		} catch (Exception e) {
			e.printStackTrace();
			MessageSender.message(command, "There was an error while trying to link this user");
		}
		GeneralUtils.addToLeaderBoard(uuid, data, command);
	}

	private void welcomeEmbed(String data, String uuid, String discordid) {
		final EmbedBuilder embed = new EmbedBuilder();

		final long time = System.currentTimeMillis();
		final String url = "https://crafatar.com/renders/body/" + uuid + "?overlay&size=512&time=" + time;
		final String avatarUrl = Objects.requireNonNull(Bot.server.getMemberById(discordid)).getUser().getAvatarUrl();
		final String name = Objects.requireNonNull(Bot.server.getMemberById(discordid)).getEffectiveName();
		final String playerName = API.getName(data);
		final int W = API.getWinsToInt(data);
		final int R = API.getWallsToInt(data);
		final int Q = API.getQualificationToInt(data);
		final int F = API.getFinalsToInt(data);

		embed.setAuthor(name + " | " + playerName, url, avatarUrl);
		embed.setThumbnail(url);
		embed.setFooter("Discord ID: " + discordid + "\nUUID: " + uuid);

		embed.setTitle(name+" was linked!");
		embed.setDescription("They have:");
		embed.addField("`"+W+"` wins", "",false);
		embed.addField("`"+R+"` walls", "",false);
		embed.addField("`"+Q+"` Q", "",false);
		embed.addField("`"+F+"` F", "",false);
		embed.addField("`"+(Q+F)+"` Q/F", "",false);
		embed.setColor(RolesManager.getRoleColor(Math.max(Q, F)));

		Bot.server.getTextChannelById(ConfigManager.getString("trackerChannel")).sendMessage(embed.build()).complete();
	}
}
