package utils;

public class LeaderboardPlayer {
	
	public int wins;
	public int walls;
	public int qualification;
	public int finals;
	public int total;
	public String name;
	
	public LeaderboardPlayer(int wins, int walls, int qualification, int finals, int total, String name) {
		this.wins = wins;
		this.walls = walls;
		this.qualification = qualification;
		this.finals = finals;
		this.total = total;
		this.name = name;
	}
}
