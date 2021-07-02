package userInterface;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameImage extends JPanel {

	
	String num = "440";
	int times;
	/**
	 * Create the panel.
	 */
	public GameImage(String num, int times) {
		this.num = num;
		this.times = times;
		this.setBounds(0, 0, 460 / times, 215 / times);
		this.setLayout(null);
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Image gameImg;		
		
		try {
			gameImg = ImageIO.read(new URL("https://steamcdn-a.akamaihd.net/steam/apps/" + num + "/header.jpg"));

			
			int _width = gameImg.getWidth(null);
			int _height = gameImg.getHeight(null);
			
			_width /= times;
			_height /= times;
			
			gameImg = gameImg.getScaledInstance(_width, _height, Image.SCALE_SMOOTH);
			
			g.drawImage(gameImg, 0, 0, this);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
