package userInterface;

import java.awt.Font;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class SignIn extends JPanel {

	TextField textSU_pass;
	TextField textSU_ID;
	JButton btnSU_SignIn;
	JButton btnSU_Cancel;
	/**
	 * Create the panel.
	 */
	public SignIn() {

		this.setBounds(0, 0, 1189, 711);
		this.setLayout(null);

		textSU_ID = new TextField(); // 아이디 입력
		textSU_ID.setBounds(441, 304, 317, 21);
		this.add(textSU_ID);
		textSU_ID.setColumns(10);

		textSU_pass = new TextField(); // 비밀번호 입력
		textSU_pass.setBounds(441, 345, 317, 21);
		this.add(textSU_pass);
		textSU_pass.setColumns(10);

		JLabel labelSU_ID = new JLabel("\uACC4\uC815 \uC774\uB984");
		labelSU_ID.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		labelSU_ID.setHorizontalAlignment(SwingConstants.CENTER);
		labelSU_ID.setBounds(354, 299, 83, 31);
		this.add(labelSU_ID);

		textSU_pass.setEchoChar('*');

		JLabel labelSU_Password1 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		labelSU_Password1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		labelSU_Password1.setHorizontalAlignment(SwingConstants.CENTER);
		labelSU_Password1.setBounds(354, 340, 83, 31);
		this.add(labelSU_Password1);

		btnSU_SignIn = new JButton("\uD68C\uC6D0 \uAC00\uC785"); // this 버튼
		btnSU_SignIn.setBounds(354, 443, 200, 21);
		this.add(btnSU_SignIn);

		btnSU_Cancel = new JButton("\uCDE8\uC18C");
		btnSU_Cancel.setBounds(564, 443, 200, 21);
		this.add(btnSU_Cancel);

		JLabel labelSU_계정만들기 = new JLabel("\uACC4\uC815 \uB9CC\uB4E4\uAE30");
		labelSU_계정만들기.setHorizontalAlignment(SwingConstants.CENTER);
		labelSU_계정만들기.setFont(new Font("맑은 고딕", Font.BOLD, 30)); //글씨체 너무 예뻐서 포기하지 못하였음
		labelSU_계정만들기.setBounds(355, 240, 403, 40);
		this.add(labelSU_계정만들기);
		
		this.setVisible(false);
	}

}
