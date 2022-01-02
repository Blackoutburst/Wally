package utils;

import java.util.List;

public class PracticeLeaderboard {
	
	enum Type {
		Q,
		F,
		WQ,
		L,
		T,
		O
	}

	private final List<PracticeLeaderboardPlayer> players;
	private final Type type;

	public PracticeLeaderboard(List<PracticeLeaderboardPlayer> players, String type) {
		this.players = players;
		this.type = getTypeFromString(type);
	}

	public float getPlayerStat(int index) {
		PracticeLeaderboardPlayer player = players.get(index);
		switch (this.type) {
			case F : return (player.finals);
			case WQ : return (player.wideQualification);
			case L : return (player.lobby);
			case T : return (player.total);
			case O : return (player.overall);
			default: return (player.qualification);
		}
	}

	/**
	 * Get canvas name from leader board type
	 */
	public String getCanvasName() {
		switch (this.type) {
			case F: return ("Finals Leaderboard");
			case WQ: return ("Wide Quali Leaderboard");
			case L: return ("Lobby Leaderboard");
			case T: return ("L/Q/F/WQ Leaderboard");
			case O: return ("Overall Rank Leaderboard");
			default: return ("Qualification Leaderboard");
		}
	}

	public List<PracticeLeaderboardPlayer> getPlayers() {
		return players;
	}

	private static Type getTypeFromString(String c) {
		switch(c) {
			case "f" : return (Type.F);
			case "wq" : return (Type.WQ);
			case "l" : return (Type.L);
			case "t" : return (Type.T);
			case "o" : return (Type.O);
			default : return (Type.Q);
		}
	}
}
