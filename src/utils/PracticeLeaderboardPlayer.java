package utils;

public class PracticeLeaderboardPlayer {
	
	public int qualification;
	public int finals;
	public int wideQualification;
	public int wideFinals;
	public int lobby;
	public int total;
	public float overall;
	public String name;
	public String discord;
	public String uuid;
	
	public PracticeLeaderboardPlayer(int qualification, int finals, int wideQualification, int lobby, int wideFinals, int total, float overall, String name, String discord, String uuid) {
		this.qualification = qualification;
		this.finals = finals;
		this.wideQualification = wideQualification;
		this.lobby = lobby;
		this.wideFinals = wideFinals;
		this.total = total;
		this.overall = overall;
		this.name = name;
		this.discord = discord;
		this.uuid = uuid;
	}

	public PracticeLeaderboardPlayer(String data) {
		this.uuid = PracticeStats.getUUID(data);
		this.name = PracticeStats.getName(data);
		this.qualification = PracticeStats.getQualificationToInt(data);
		this.finals = PracticeStats.getFinalsToInt(data);
		this.wideQualification = PracticeStats.getWideQualificationToInt(data);
		this.wideFinals = PracticeStats.getWideFinalsToInt(data);
		this.lobby = PracticeStats.getLobbyToInt(data);
		this.total = PracticeStats.getTotalToInt(data);
	}
}
