package userInterface;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import socketPackage.Communicator;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
@SuppressWarnings("serial")
public class Search extends JPanel {
	private Communicator com;
	private GameList gl;
	JTextField txtSearch;
	JButton btnMain;
	JButton btnSearch;
	
	public void searchGameReco(String word)
	{
		String[] search = com.searchGame(word);
		if (search == null)
			search = new String[0];
		
		/*
		 * for(int i = 0; i < search.length; i++) System.out.println(search[i]);
		 */
		
		try
		{
			this.remove(gl);
		}
		catch(NullPointerException e)
		{
			
		}
		
		gl = new GameList(search);
		gl.setBounds(0, 77, 800, 613);
		gl.setVisible(true);
		this.add(gl);	
		
		repaint();
	}
	
	/**
	 * Create the panel.
	 */
	public Search() {

		com = Communicator.getInstance();
		this.setBounds(0, 0, 1189, 711);
		this.setLayout(null);
		
		txtSearch = new JTextField();
		txtSearch.setColumns(10);
		txtSearch.setBounds(12, 10, 834, 23);
		add(txtSearch);
		
		btnSearch = new JButton("\uAC8C\uC784 \uAC80\uC0C9");
		btnSearch.setBounds(856, 10, 99, 23);
		add(btnSearch);
		
		btnMain = new JButton("\uBA54\uC778 \uD654\uBA74");
		btnMain.setBounds(967, 10, 99, 23);
		add(btnMain);
		
		JButton btnExit = new JButton("\uC885\uB8CC");
		btnExit.setBounds(1078, 10, 99, 23);
		add(btnExit);
		
		JButton btnLeft = new JButton("<");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gl.goLeft();
			}
		});
		btnLeft.setBounds(12, 43, 50, 23);
		add(btnLeft);
		
		JLabel lblSearch = new JLabel("\uAC80\uC0C9 \uAC8C\uC784 \uBAA9\uB85D");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		lblSearch.setBounds(74, 43, 680, 25);
		add(lblSearch);
		
		JButton btnRight = new JButton(">");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gl.goRight();
			}
		});
		btnRight.setBounds(766, 43, 50, 23);
		add(btnRight);

		this.setVisible(false);
	}
}
