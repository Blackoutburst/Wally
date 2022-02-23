package utils;

import org.checkerframework.checker.units.qual.C;

import java.awt.Color;
import java.util.Objects;

public class GraphCanvas {
	/**
	 * Create practice graph image
	 */
	public static void createPGraphImage(Canvas image, String data, String type, int games) {
		image.loadFont("res/font.ttf");

		image.drawQuad(0, 0, 1000, 600, new Color(0, 0, 0, 75));

		for (int i = 0; i < 10; i ++) {
			if (i != 9) {
				image.drawQuad(((i+1) * 100), 0, 1, 592, new Color(255, 255, 255, 70));
				image.drawQuad(((i+1) * 100) - 2, 592, 4, 8, new Color(255, 255, 255, 128));
			}
			image.drawQuad(0, ((i+1) * 54), 1000, 1, Canvas.getLineColorTransparent(500 - (50 * i)));
		}
		image.drawEmptyQuad(0, 0, 999, 599, Color.WHITE);

		if (type.equals("Qualification")) {
			image.drawGraphLine(PracticeAPI.getQualificationHistory(data), games);
		}
		if (type.equals("Finals")) {
			image.drawGraphLine(PracticeAPI.getFinalsHistory(data), games);
		}
		if (type.equals("Wide Qualification")) {
			image.drawGraphLine(PracticeAPI.getWideQualificationHistory(data), games);
		}
		if (type.equals("Lobby")) {
			image.drawGraphLine(PracticeAPI.getLobbyHistory(data), games);
		}
		if (type.equals("Wide Finals")) {
			image.drawGraphLine(PracticeAPI.getWideFinalsHistory(data), games);
		}

		for (int i = 0; i < 10; i ++) {
			image.drawStringLeft(String.valueOf(((i) * games) / 10), 1000 - ((i) * 100)+5, 590, 16, Color.white);
			image.drawStringLeft(String.valueOf(500 - (50 * i)), 5, ((i+1) * 54) - 5, 16, Canvas.getLineColor(500 - (50 * i)));
		}

		image.save("pgraph.png");
	}
}
