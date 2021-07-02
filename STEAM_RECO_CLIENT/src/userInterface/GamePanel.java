package userInterface;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public GamePanel(String msg) 
	{
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new NewWindow(msg);
			}
		});
		// num,Title,Genre...
		
		this.setBounds(0, 0, 800, 107);
		setLayout(null);
		
		String[] tmp = msg.split(",");
		String genre = "";
		for (int i = 2; i < tmp.length; i++)
			genre += (tmp[i] + ", ");
		
		if(genre.length() > 0)
			genre = genre.substring(0, genre.length() - 2);
		
		
		GameImage gi = new GameImage(tmp[0], 2);
		gi.setBounds(0, 0, 230, 107);
		this.add(gi);
		
		JLabel lblTitle = new JLabel("<html>" + tmp[1]);
		lblTitle.setFont(new Font("±¼¸²", Font.BOLD, 18));
		lblTitle.setBounds(242, 20, 546, 35);
		add(lblTitle);
		
		JLabel lblGenre = new JLabel("<html>" + genre);
		lblGenre.setFont(new Font("±¼¸²", Font.BOLD, 14));
		lblGenre.setBounds(242, 55, 546, 42);
		add(lblGenre);
	}

}
