package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Canvas {
	
	private BufferedImage background = null;
	private BufferedImage bufferedImage = null;
	private Graphics2D g = null;
	private Font font = null;
	
	public Canvas(int width, int height) {
		try {
			background = ImageIO.read(new File("res/background.png"));
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			g = bufferedImage.createGraphics();
			font = Font.createFont(Font.TRUETYPE_FONT, new File("res/font.ttf"));
			
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save image to file
	 * @param fileName
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
	 * @param file
	 * @param x
	 * @param y
	 * @param width
	 * @param height
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
	 * Draw a string aligned to the left with specified parameters
	 * @param string
	 * @param x
	 * @param y
	 * @param size
	 * @param color
	 */
	public void drawStringLeft(String string, int x, int y, float size, Color color) {
		font = font.deriveFont(size);
		g.setFont(font);
		g.setColor(color);
		g.drawString(string, x, y);
	}
	
	/**
	 * Draw a string centered with specified parameters
	 * @param string
	 * @param x
	 * @param y
	 * @param size
	 * @param color
	 */
	public void drawStringCenter(String string, int x, int y, float size, Color color) {
		font = font.deriveFont(size);
		g.setFont(font);
		g.setColor(color);
		g.drawString(string, x - g.getFontMetrics().stringWidth(string)/2, y);
	}
	
	/**
	 * Draw a string aligned to the right with specified parameters
	 * @param string
	 * @param x
	 * @param y
	 * @param size
	 * @param color
	 */
	public void drawStringRight(String string, int x, int y, float size, Color color) {
		font = font.deriveFont(size);
		g.setFont(font);
		g.setColor(color);
		g.drawString(string, x - g.getFontMetrics().stringWidth(string), y);
	}
	
	/**
	 * Draw background image and make it darker
	 */
	public void drawBackground() {
		g.drawImage(background, 0, 0, 600, 400, null);
		g.setColor(new Color(0, 0, 0, 0.4f));
		g.fillRect(0, 0, 600, 400);
	}
	
	/**
	 * Draw custom background and make it darker
	 * @param file
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawCustomBackground(String file, int x, int y, int width, int height) {
		try {
			BufferedImage image = ImageIO.read(new File(file));
			
			g.drawImage(image, x, y, width, height, null);
			g.setColor(new Color(0, 0, 0, 0.4f));
			g.fillRect(x, y, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set background image
	 * @param file
	 */
	public void setBackground(String file) {
		try {
			background = ImageIO.read(new File(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load custom font file
	 * @param file
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
	 * @param score
	 * @return
	 */
	public static String getHeader(int score) {
		String file = "res/member.png";
		
		if (score >= 50) file = "res/50.png";
		if (score >= 100) file = "res/100.png";
		if (score >= 150) file = "res/150.png";
		if (score >= 200) file = "res/200.png";
		if (score >= 250) file = "res/250.png";
		if (score >= 300) file = "res/300.png";
		if (score >= 350) file = "res/350.png";
		if (score >= 400) file = "res/400.png";
		if (score >= 450) file = "res/450.png";
		if (score >= 500) file = "res/500.png";
		return file;
	}
	
	/**
	 * Return header color
	 * @param score
	 * @return
	 */
	public static String getHeaderRight(int score) {
		String file = "res/memberR.png";
		
		if (score >= 50) file = "res/50R.png";
		if (score >= 100) file = "res/100R.png";
		if (score >= 150) file = "res/150R.png";
		if (score >= 200) file = "res/200R.png";
		if (score >= 250) file = "res/250R.png";
		if (score >= 300) file = "res/300R.png";
		if (score >= 350) file = "res/350R.png";
		if (score >= 400) file = "res/400R.png";
		if (score >= 450) file = "res/450R.png";
		if (score >= 500) file = "res/500R.png";
		return file;
	}
}
