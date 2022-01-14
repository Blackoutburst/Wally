package utils;

import java.util.List;

public class PracticeLeaderboard {
	
	enum Type {
		Q,
		F,
		WQ,
		WF,
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
			case WF : return (player.wideFinals);
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
			case WQ: return ("Wide Q Leaderboard");
			case WF: return ("Wide F Leaderboard");
			case L: return ("Lobby Leaderboard");
			case T: return ("Total Score Leaderboard");
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
			case "wf" : return (Type.WF);
			case "l" : return (Type.L);
			case "t" : return (Type.T);
			case "o" : return (Type.O);
			default : return (Type.Q);
		}
	}
}
