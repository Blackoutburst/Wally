package utils;

import core.Patreon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Canvas {
	
	private final BufferedImage bufferedImage;
	private final Graphics2D g;
	private static Font font = null;
	
	public int width;
	public int height;
	
	public static void init() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("res/font.ttf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Canvas(int width, int height) {
			this.width = width;
			this.height = height;
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			g = bufferedImage.createGraphics();
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	/**
	 * Save image to file
	 */
	public void save(String fileName) {
		File file = new File(fileName);
		
		try {
			ImageIO.write(bufferedImage, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draw image
	 */
	public void drawImageFromMemory(BufferedImage image, int x, int y, int width, int height) {
		g.drawImage(image, x, y, width, height, null);
	}
	
	/**
	 * Draw image
	 */
	public void drawImage(String file, int x, int y, int width, int height) {
		try {
			BufferedImage image = ImageIO.read(new File(file));
			
			g.drawImage(image, x, y, width, height, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Draw graph line
	 */
	public void drawGraphLine(int[] data, int games) {
		g.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		
		for (int i = 0; i < (games-1); i++) {
				Line2D line = new Line2D.Float(1000 - ((float)(i) * 1000/games), 520 - (data[i] * 0.86f + 2), 1000 - (((float)(i)+1) * 1000/games), 520 - (data[i+1] * 0.86f + 2));
				
				GradientPaint gradient = new GradientPaint(1000 - ((float)(i) * 1000/games), 520 - (data[i] * 0.86f  + 2), getLineColor(data[i]),
				1000 - (((float)(i)+1) * 1000/games), 520 - (data[i+1] * 0.86f + 2), getLineColor(data[i+1]));
				
				g.setPaint(gradient);
				g.draw(line);
				
				if (i == games-2) {
					line = new Line2D.Float(1000 - (((float)(i)+1) * 1000/games), 520 - (data[i+1] * 0.86f + 2), 1000 - (((float)(i)+2) * 1000/games), 520 - (data[i+1] * 0.86f + 2));
					
					gradient = new GradientPaint(1000 - (((float)(i)+1) * 1000/games), 520 - (data[i+1] * 0.86f  + 2), getLineColor(data[i+1]),
					1000 - (((float)(i)+2) * 1000/games), 520 - (data[i+1] * 0.86f + 2), getLineColor(data[i+1]));
					
					g.setPaint(gradient);
					g.draw(line);
				}
				
		}
	}
	
	/**
	 * Draw a string aligned to the left with specified parameters
	 */
	public void drawStringLeft(String string, int x, int y, float size, Color color) {
		font = font.deriveFont(size);
		g.setFont(font);
		g.setColor(color);
		g.drawString(string, x, y);
	}
	
	/**
	 * Draw a string centered with specified parameters
	 */
	public void drawStringCenter(String string, int x, int y, float size, Color color) {
		font = font.deriveFont(size);
		g.setFont(font);
		g.setColor(color);
		g.drawString(string, x - g.getFontMetrics().stringWidth(string)/2, y);
	}
	
	/**
	 * Draw a string aligned to the right with specified parameters
	 */
	public void drawStringRight(String string, int x, int y, float size, Color color) {
		font = font.deriveFont(size);
		g.setFont(font);
		g.setColor(color);
		g.drawString(string, x - g.getFontMetrics().stringWidth(string), y);
	}
	
	/**
	 * Draw custom background and make it darker
	 */
	public void drawCustomBackground(String file, int x, int y, int width, int height) {
		try {
			BufferedImage image = ImageIO.read(new File(file));
			
			g.drawImage(image, x, y, width, height, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load custom font file
	 */
	public void loadFont(String file) {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(file));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return header color
	 */
	public static BufferedImage getHeader(int score, String uuid) {
		boolean patreon = Patreon.isPatreon(uuid);
		BufferedImage file = LoadedRessources.HMEMBER;
		
		if (score >= 50) file =  LoadedRessources.H50;
		if (score >= 100) file = LoadedRessources.H100;
		if (score >= 150) file = LoadedRessources.H150;
		if (score >= 200) file = LoadedRessources.H200;
		if (score >= 250) file = LoadedRessources.H250;
		if (score >= 300) file = patreon ? LoadedRessources.H300P : LoadedRessources.H300;
		if (score >= 350) file = LoadedRessources.H350;
		if (score >= 400) file = LoadedRessources.H400;
		if (score >= 450) file = LoadedRessources.H450;
		if (score >= 500) file = LoadedRessources.H500;
		return file;
	}
	
	/**
	 * Return header color
	 */
	public static BufferedImage getHeaderRight(int score, String uuid) {
		boolean patreon = Patreon.isPatreon(uuid);
		BufferedImage file = LoadedRessources.HMEMBERR;
		
		if (score >= 50) file = LoadedRessources.H50R;
		if (score >= 100) file = LoadedRessources.H100R;
		if (score >= 150) file = LoadedRessources.H150R;
		if (score >= 200) file = LoadedRessources.H200R;
		if (score >= 250) file = LoadedRessources.H250R;
		if (score >= 300) file = patreon ? LoadedRessources.H300PR : LoadedRessources.H300R;
		if (score >= 350) file = LoadedRessources.H350R;
		if (score >= 400) file = LoadedRessources.H400R;
		if (score >= 450) file = LoadedRessources.H450R;
		if (score >= 500) file = LoadedRessources.H500R;
		return file;
	}
	
	/**
	 * Return line color
	 */
	public static Color getLineColor(int score) {
		Color color = new Color(170, 170, 170);
		
		if (score >= 50) color = new Color(204, 222, 25);
		if (score >= 100) color = new Color(214, 147, 35);
		if (score >= 150) color = new Color(230, 110, 41);
		if (score >= 200) color = new Color(224, 17, 85);
		if (score >= 250) color = new Color(187, 0, 221);
		if (score >= 300) color = new Color(140, 84, 254);
		if (score >= 350) color = new Color(0, 102, 204);
		if (score >= 400) color = new Color(0, 176, 176);
		if (score >= 450) color = new Color(0, 212, 146);
		if (score >= 500) color = new Color(0, 200, 85);
		return color;
	}
}
