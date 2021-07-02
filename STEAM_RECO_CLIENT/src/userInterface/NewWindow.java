package userInterface;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import socketPackage.Communicator;

public class NewWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtPlayTime;
	private Communicator com;
	// 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
	public NewWindow(String msg) {
		setTitle("게임 상세정보");
		com = Communicator.getInstance();

		JPanel NewWindowContainer = new JPanel();
		setContentPane(NewWindowContainer);

		setSize(1200, 240);
		setResizable(false);
		setVisible(true);
		getContentPane().setLayout(null);
		
		String[] tmp = msg.split(",");
		String genre = "";
		for (int i = 2; i < tmp.length; i++)
			genre += (tmp[i] + ", ");
		if(genre.length() > 0)
			genre = genre.substring(0, genre.length() - 2);
		
		GameImage gi = new GameImage(tmp[0], 1);
		gi.setBounds(0, 0, 460, 215);
		getContentPane().add(gi);
		
		JLabel lblTitle = new JLabel("<html>" + tmp[1]);
		lblTitle.setFont(new Font("굴림", Font.BOLD, 18));
		lblTitle.setBounds(472, 36, 710, 49);
		NewWindowContainer.add(lblTitle);
		
		JLabel lblGenre = new JLabel("<html>" + genre);
		lblGenre.setFont(new Font("굴림", Font.BOLD, 14));
		lblGenre.setBounds(472, 95, 710, 73);
		NewWindowContainer.add(lblGenre);
		
		JButton btnReco = new JButton("RECOMMEND THIS GAME!");
		btnReco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					Double.parseDouble(txtPlayTime.getText());
					
					JOptionPane.showMessageDialog(null, com.registReview(tmp[0], txtPlayTime.getText()));
				}
				catch (Exception e2)
				{
					JOptionPane.showMessageDialog(null,  "잘못된 시간 입력입니다.");
				}
			}
		});
		btnReco.setBounds(848, 178, 200, 23);
		NewWindowContainer.add(btnReco);
		
		JLabel lblHowMuch = new JLabel("HOW MUCH DIDYOU PLAY? (HOUR) :");
		lblHowMuch.setFont(new Font("굴림", Font.BOLD, 13));
		lblHowMuch.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHowMuch.setBounds(472, 182, 236, 15);
		NewWindowContainer.add(lblHowMuch);
		
		txtPlayTime = new JTextField();
		txtPlayTime.setText("0.0");
		txtPlayTime.setBounds(720, 180, 116, 18);
		NewWindowContainer.add(txtPlayTime);
		txtPlayTime.setColumns(10);
	}
}
