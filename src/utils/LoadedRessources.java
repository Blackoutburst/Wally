package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadedRessources {

	public static BufferedImage HMEMBER = null;
	public static BufferedImage H50 = null;
	public static BufferedImage H100 = null;
	public static BufferedImage H150 = null;
	public static BufferedImage H200 = null;
	public static BufferedImage H250 = null;
	public static BufferedImage H300 = null;
	public static BufferedImage H350 = null;
	public static BufferedImage H400 = null;
	public static BufferedImage H450 = null;
	public static BufferedImage H500 = null;
	
	public static BufferedImage HMEMBERR = null;
	public static BufferedImage H50R = null;
	public static BufferedImage H100R = null;
	public static BufferedImage H150R = null;
	public static BufferedImage H200R = null;
	public static BufferedImage H250R = null;
	public static BufferedImage H300R = null;
	public static BufferedImage H350R = null;
	public static BufferedImage H400R = null;
	public static BufferedImage H450R = null;
	public static BufferedImage H500R = null;
	
	public static BufferedImage BACKGROUND = null;
	public static BufferedImage BACKGROUND_LARGE = null;
	public static BufferedImage BACKGROUND_XL = null;
	public static BufferedImage BACKGROUND_ENDL = null;
	
	public static BufferedImage PHEADER = null;
	
	public static BufferedImage Q = null;
	public static BufferedImage F = null;
	public static BufferedImage QF = null;
	public static BufferedImage WINS = null;
	public static BufferedImage WALLS = null;
	public static BufferedImage WQ = null;
	public static BufferedImage L = null;
	public static BufferedImage RANK = null;
	
	public static BufferedImage BLACKOUT = null;
	public static BufferedImage VENOM = null;
	
	public static void loadRessources() throws IOException {
		HMEMBER = ImageIO.read(new File("res/member.png"));
		H50 = ImageIO.read(new File("res/50.png"));
		H100 = ImageIO.read(new File("res/100.png"));
		H150 = ImageIO.read(new File("res/150.png"));
		H200 = ImageIO.read(new File("res/200.png"));
		H250 = ImageIO.read(new File("res/250.png"));
		H300 = ImageIO.read(new File("res/300.png"));
		H350 = ImageIO.read(new File("res/350.png"));
		H400 = ImageIO.read(new File("res/400.png"));
		H450 = ImageIO.read(new File("res/450.png"));
		H500 = ImageIO.read(new File("res/500.png"));
		
		HMEMBERR = ImageIO.read(new File("res/memberR.png"));
		H50R = ImageIO.read(new File("res/50R.png"));
		H100R = ImageIO.read(new File("res/100R.png"));
		H150R = ImageIO.read(new File("res/150R.png"));
		H200R = ImageIO.read(new File("res/200R.png"));
		H250R = ImageIO.read(new File("res/250R.png"));
		H300R = ImageIO.read(new File("res/300R.png"));
		H350R = ImageIO.read(new File("res/350R.png"));
		H400R = ImageIO.read(new File("res/400R.png"));
		H450R = ImageIO.read(new File("res/450R.png"));
		H500R = ImageIO.read(new File("res/500R.png"));
		
		BACKGROUND = ImageIO.read(new File("res/background.png"));
		BACKGROUND_LARGE = ImageIO.read(new File("res/backgroundLarge.png"));
		BACKGROUND_XL = ImageIO.read(new File("res/backgroundXL.png"));
		BACKGROUND_ENDL = ImageIO.read(new File("res/backgroundEndL.png"));
		
		PHEADER = ImageIO.read(new File("res/practiceHeader.png"));
		
		Q = ImageIO.read(new File("res/q.png"));
		F = ImageIO.read(new File("res/f.png"));
		QF = ImageIO.read(new File("res/qf.png"));
		WINS = ImageIO.read(new File("res/win.png"));
		WALLS = ImageIO.read(new File("res/wall.png"));
		WQ = ImageIO.read(new File("res/wq.png"));
		L = ImageIO.read(new File("res/l.png"));
		RANK = ImageIO.read(new File("res/rank.png"));
		
		BLACKOUT = ImageIO.read(new File("res/blackout.png"));
		VENOM = ImageIO.read(new File("res/venom.png"));
	}
	
}
