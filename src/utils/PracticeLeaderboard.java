package utils;

import java.util.Collections;
import java.util.List;

import comparators.PlayerComparatorL;
import comparators.PlayerComparatorLQF;
import comparators.PlayerComparatorPF;
import comparators.PlayerComparatorPQ;
import comparators.PlayerComparatorWQ;

public class PracticeLeaderboard {
	
	enum Type {
		Q,
		F,
		WQ,
		L,
		T
	}

	private List<PracticeLeaderboardPlayer> players;
	private Type type;

	public PracticeLeaderboard(List<PracticeLeaderboardPlayer> players, String type) {
		this.players = players;
		this.type = getTypeFromString(type);
	}

	public void sort() {
		switch (type) {
			case Q : Collections.sort(this.players, new PlayerComparatorPQ()); break;
			case F : Collections.sort(this.players, new PlayerComparatorPF()); break;
			case WQ : Collections.sort(this.players, new PlayerComparatorWQ()); break;
			case L : Collections.sort(this.players, new PlayerComparatorL()); break;
			case T : Collections.sort(this.players, new PlayerComparatorLQF()); break;
			default : Collections.sort(this.players, new PlayerComparatorPQ()); break;
		}
	}

	public int getPlayerStat(int index) {
		PracticeLeaderboardPlayer player = players.get(index);
		switch (this.type) {
			case Q : return (player.qualification);
			case F : return (player.finals);
			case WQ : return (player.wideQualification);
			case L : return (player.lobby);
			case T : return (player.total);
			default: return (player.qualification);
		}
	}

	/**
	 * Get canvas name from leader board type
	 * @param type
	 * @return
	 */
	public String getCanvasName() {
		switch (this.type) {
			case Q: return ("Qualification Leaderboard");
			case F: return ("Finals Leaderboard");
			case WQ: return ("Wide Quali Leaderboard");
			case L: return ("Lobby Leaderboard");
			case T: return ("L/Q/F Leaderboard");
			default: return ("Qualification Leaderboard");
		}
	}

	public List<PracticeLeaderboardPlayer> getPlayers() {
		return players;
	}

	private static Type getTypeFromString(String c) {
		switch(c) {
			case "q" : return (Type.Q);
			case "f" : return (Type.F);
			case "wq" : return (Type.WQ);
			case "l" : return (Type.L);
			case "t" : return (Type.T);
			default : return (Type.Q);
		}
	}
}
