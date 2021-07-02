package userInterface;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import socketPackage.Communicator;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Main extends JPanel {

	private Communicator com;
	private GameList gl;
	JButton btnLogoff;
	JButton btnSearch;
	public JTextField txtSearch;
	
	private JTable tbl_achieve;
	private JTable tlb_friend;	
	DefaultTableModel achieve_model, friend_model;
	String[] achieve_desc;
	
	private void setGameReco()
	{
		String[] game = com.gameReco();
		if (game == null)
			game = new String[0];
		
		try
		{
			this.remove(gl);
		}
		catch (NullPointerException e)
		{
			
		}
		
		gl = new GameList(game);
		gl.setBounds(0, 77, 800, 613);
		gl.setVisible(true);
		this.add(gl);		
		
		repaint();
	}
	
	public void setAchieveTable()
	{
		String[] achieve = com.achieveReco();
		if(achieve == null)
			return;
		
		achieve_desc = new String[achieve.length];
		
		while(achieve_model.getRowCount() > 0)
			achieve_model.removeRow(0);
		
		for(int i = 0; i < achieve.length; i++)
		{
			String[] tmp = achieve[i].split(",");
			Object[] obj = { tmp[0], tmp[1] };
			achieve_model.addRow(obj);
			if(tmp[2] == "") tmp[2] = "NONE";  
			achieve_desc[i] = tmp[2];
		}
	}
	
	public void setFriendTable()
	{
		String[] friend = com.friendReco();
		if(friend == null)
			return;
		
		while(friend_model.getRowCount() > 0)
			friend_model.removeRow(0);
		
		for(int i = 0; i < friend.length; i++)
		{
			Object[] obj = { friend[i] };
			friend_model.addRow(obj);
		}		
	}
	
	public void login_OK()
	{
		setGameReco();
		setAchieveTable();
		setFriendTable();
	}
	
	
	
	/**
	 * Create the panel.
	 */
	public Main() {
		String achieve_column[] = { "GAME TITLE", "ACHIEVEMENT NAME" };
		String friend_column[] = { "NICKNAME" };		
		
		com = Communicator.getInstance();
		achieve_model = new DefaultTableModel(achieve_column, 0);
		friend_model = new DefaultTableModel(friend_column, 0);

		this.setBounds(0, 0, 1189, 711);
		this.setLayout(null);
		this.setVisible(false);
		
		// 추천 게임 테이블




		btnLogoff = new JButton("\uB85C\uADF8\uC624\uD504"); // 로그오프 버튼

		btnLogoff.setBounds(967, 10, 99, 23);
		this.add(btnLogoff);

		btnSearch = new JButton("\uAC8C\uC784 \uAC80\uC0C9"); // Search 패널로 넘어가는 버튼
		btnSearch.setBounds(856, 10, 99, 23);
		this.add(btnSearch);

		JButton btnExit = new JButton("\uC885\uB8CC"); // 클라이언트 종료 버튼
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(1078, 10, 99, 23);
		this.add(btnExit);

		JLabel lblFriend = new JLabel("\uCD94\uCC9C \uCE5C\uAD6C \uBAA9\uB85D");
		lblFriend.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblFriend.setHorizontalAlignment(SwingConstants.CENTER);
		lblFriend.setBounds(828, 372, 325, 23);
		this.add(lblFriend);

		JLabel lblAchieve = new JLabel("\uCD94\uCC9C \uC5C5\uC801 \uBAA9\uB85D");
		lblAchieve.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblAchieve.setHorizontalAlignment(SwingConstants.CENTER);
		lblAchieve.setBounds(828, 44, 325, 23);
		this.add(lblAchieve);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(12, 10, 834, 23);
		add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblGame = new JLabel("\uCD94\uCC9C \uAC8C\uC784 \uBAA9\uB85D");
		lblGame.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lblGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblGame.setBounds(74, 44, 680, 25);
		add(lblGame);
		
		JButton btnLeft = new JButton("<");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gl.goLeft();
			}
		});
		btnLeft.setBounds(12, 44, 50, 23);
		add(btnLeft);
		
		JButton btnRight = new JButton(">");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gl.goRight();
			}
		});
		btnRight.setBounds(766, 44, 50, 23);
		add(btnRight);
		
		tbl_achieve = new JTable(achieve_model);
		tbl_achieve.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, achieve_desc[tbl_achieve.getSelectedRow()]);
			}
		});
		tbl_achieve.setBounds(828, 77, 349, 285);
		add(tbl_achieve);
		
		tlb_friend = new JTable(friend_model);
		tlb_friend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		tlb_friend.setBounds(828, 405, 349, 285);
		add(tlb_friend);
		
		this.setVisible(false);
	}
}
