package userInterface;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameList extends JPanel {

	GamePanel[] gp;
	int page = 0;
	
	/**
	 * Create the panel.
	 */
	public GameList(String[] msg) {

		this.setBounds(0, 0, 800, 613);
		this.setLayout(null);
		
		gp = new GamePanel[msg.length];
		
		for(int i = 0; i < msg.length; i++)
		{
			gp[i] = new GamePanel(msg[i]);
			this.add(gp[i]);
		}
		
		for(int i = 0; i < msg.length; i++)
		{
			int t = i % 5;
			gp[i].setBounds(12, 10 + (120 * t), 800, 107);
			gp[i].setVisible(false);
		}
		
		for(int i = 0; i < (msg.length >= 5 ? 5 : msg.length); i++)
		{
			gp[i].setVisible(true);
		}
	}
	
	public void goLeft()
	{
		if(page > 0)
		{
			for(int i = (5 * page); i < (5 * (page + 1) > gp.length ? gp.length : 5 * (page + 1)); i++)
				gp[i].setVisible(false);

			page--;

			for(int i = (5 * page); i < 5 * (page + 1); i++)
				gp[i + (5 * page)].setVisible(true);
		}
	}
	
	public void goRight()
	{
		if(page < (gp.length - 1) / 5)
		{
			for(int i = (5 * page); i < 5 * (page + 1); i++)
				gp[i].setVisible(false);
			
			page++;
			
			for(int i = (5 * page); i < (5 * (page + 1) > gp.length ? gp.length : 5 * (page + 1)); i++)
				gp[i].setVisible(true);
		}		
	}
}
