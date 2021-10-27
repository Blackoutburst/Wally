package utils;

public class LeaderboardPlayer {
	
	public int wins;
	public int walls;
	public int qualification;
	public int finals;
	public int total;
	public float overall;
	public String name;
	public String discord;
	public String uuid;
	
	public LeaderboardPlayer(int wins, int walls, int qualification, int finals, int total, float overall, String name, String discord, String uuid) {
		this.wins = wins;
		this.walls = walls;
		this.qualification = qualification;
		this.finals = finals;
		this.total = total;
		this.overall = overall;
		this.name = name;
		this.discord = discord;
		this.uuid = uuid;
	}

	public LeaderboardPlayer(String data) {
		this.uuid = Stats.getUUID(data);
		this.name = Stats.getName(data);
		this.wins = Stats.getWinsToInt(data);
		this.walls = Stats.getWallsToInt(data);
		this.qualification = Stats.getQualificationToInt(data);
		this.finals = Stats.getFinalsToInt(data);
		this.total = Stats.getTotalToInt(data);
		try {this.discord = Stats.getDiscordId(data);} catch(Exception e) {}
	}
}
