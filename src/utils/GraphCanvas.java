package utils;

import java.awt.Color;

public class GraphCanvas {
	/**
	 * Create practice graph image
	 * @param image
	 * @param data
	 */
	public static void createPGraphImage(Canvas image, String name, String data, String type) {
		image.drawImage("res/graphBackground.png", 0, 0, 1000, 520);

		int average = 0;
		
		for (int i = 0; i < 100; i++) {
			average += PracticeAPI.getQualificationHistory(data)[i];
		}
		average = average / 100;
		
		
		if (type.equals("Qualification"))
			image.drawGraphLine(PracticeAPI.getQualificationHistory(data));
		if (type.equals("Finals"))
			image.drawGraphLine(PracticeAPI.getFinalsHistory(data));
		if (type.equals("Wide Qualification"))
			image.drawGraphLine(PracticeAPI.getWideQualificationHistory(data));
		if (type.equals("Lobby"))
			image.drawGraphLine(PracticeAPI.getLobbyHistory(data));
		
		image.drawStringCenter(name + " | "+ type+" - latest 100 games", 500, 30, 24, Color.white);
		image.drawStringCenter("Average Score: " + average, 500, 64, 24, Canvas.getLineColor(average));
		
		image.save("pgraph.png");
	}
}
