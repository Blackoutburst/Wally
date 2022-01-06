package utils;

import java.awt.Color;
import java.util.Objects;

public class GraphCanvas {
	/**
	 * Create practice graph image
	 */
	public static void createPGraphImage(Canvas image, String name, String data, String type, int games) {
		image.loadFont("res/font.ttf");
		image.drawImage("res/graphBackground.png", 0, 0, 1000, 520);

		for (int i = 0; i < 10; i ++) {
			if (i != 9) {
				image.drawImage("res/graphLine.png", ((i+1) * 100) - 1, 0, 2, 506);
				image.drawImage("res/graphTick.png", ((i+1) * 100) - 2, 512, 4, 8);
			}
			image.drawStringLeft(String.valueOf(((i) * games) / 10), 1000 - ((i) * 100)+5, 510, 16, Color.white);
		}		
		
		int average = 0;
		
		for (int i = 0; i < games; i++) {
			if (type.equals("Qualification"))
				average += Objects.requireNonNull(PracticeAPI.getQualificationHistory(data))[i];
			if (type.equals("Finals"))
				average += Objects.requireNonNull(PracticeAPI.getFinalsHistory(data))[i];
			if (type.equals("Wide Qualification"))
				average += Objects.requireNonNull(PracticeAPI.getWideQualificationHistory(data))[i];
			if (type.equals("Lobby"))
				average += Objects.requireNonNull(PracticeAPI.getLobbyHistory(data))[i];
		}
		average = average / games;

		if (type.equals("Qualification"))
			image.drawGraphLine(PracticeAPI.getQualificationHistory(data), games);
		if (type.equals("Finals"))
			image.drawGraphLine(PracticeAPI.getFinalsHistory(data), games);
		if (type.equals("Wide Qualification"))
			image.drawGraphLine(PracticeAPI.getWideQualificationHistory(data), games);
		if (type.equals("Lobby"))
			image.drawGraphLine(PracticeAPI.getLobbyHistory(data), games);
		
		image.drawStringCenter(name + " | "+ type+" - latest " + games + " games", 500, 30, 24, Color.white);
		image.drawStringCenter("Average Score: " + average, 500, 64, 24, Canvas.getLineColor(average));
		
		image.save("pgraph.png");
	}
}
