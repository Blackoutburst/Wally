package utils;

import comparators.*;

import java.util.List;

public class Leaderboard {
	
	enum Type {
		W,
		R,
		Q,
		F,
		T,
		O
	}

	private final List<LeaderboardPlayer> players;
	private final Type type;

	public Leaderboard(List<LeaderboardPlayer> players, char type) {
		this.players = players;
		this.type = getTypeFromChar(type);
	}

	public void sort() {
		switch (type) {
			case R : this.players.sort(new PlayerComparatorRounds()); break;
			case Q : this.players.sort(new PlayerComparatorQ()); break;
			case F : this.players.sort(new PlayerComparatorF()); break;
			case T : this.players.sort(new PlayerComparatorTotal()); break;
			case O : this.players.sort(new PlayerComparatorOverall()); break;
			default : this.players.sort(new PlayerComparatorWins()); break;
		}
	}

	public float getPlayerStat(int index) {
		LeaderboardPlayer player = players.get(index);
		switch (this.type) {
			case R : return (player.walls);
			case Q : return (player.qualification);
			case F : return (player.finals);
			case T : return (player.total);
			case O: return (player.overall);
			default: return (player.wins);
		}
	}

	/**
	 * Get canvas name from leader board type
	 */
	public String getCanvasName() {
		switch (this.type) {
			case R: return ("Walls cleared Leaderboard");
			case Q: return ("Qualification Leaderboard");
			case F: return ("Finals Leaderboard");
			case T: return ("Q/F Total Leaderboard");
			case O: return ("Overall Rank Leaderboard");
			default: return ("Wins Leaderboard");
		}
	}

	public List<LeaderboardPlayer> getPlayers() {
		return players;
	}

	private static Type getTypeFromChar(char c) {
		switch(c) {
			case 'r' : return (Type.R);
			case 'q' : return (Type.Q);
			case 'f' : return (Type.F);
			case 't' : return (Type.T);
			case 'o' : return (Type.O);
			default : return (Type.W);
		}
	}
}
