package userInterface;

import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Login extends JPanel {
	
	JButton btnLI_Login;
	JButton btnLI_SignUp;
	TextField textLI_ID;
	TextField textLI_Password;
	
	/**
	 * Create the panel.
	 */
	public Login() {
		
		this.setBounds(0, 0, 1189, 711);
		
		this.setLayout(null);

		JLabel labelLI_ID = new JLabel("\uACC4\uC815 \uC774\uB984");
		labelLI_ID.setHorizontalAlignment(SwingConstants.CENTER);
		labelLI_ID.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		labelLI_ID.setBounds(354, 304, 83, 31);
		this.add(labelLI_ID);

		JLabel labelLI_Password = new JLabel("\uBE44\uBC00\uBC88\uD638");
		labelLI_Password.setHorizontalAlignment(SwingConstants.CENTER);
		labelLI_Password.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		labelLI_Password.setBounds(354, 345, 83, 31);
		this.add(labelLI_Password);

		textLI_ID = new TextField(); // this ID입력창
		textLI_ID.setColumns(10);
		textLI_ID.setBounds(441, 309, 317, 21);
		this.add(textLI_ID);

		textLI_Password = new TextField(); // this PW입력창
		textLI_Password.setBounds(441, 350, 317, 21);
		this.add(textLI_Password);
		textLI_Password.setEchoChar('*');

		btnLI_Login = new JButton("\uB85C\uADF8\uC778"); // this 버튼
		btnLI_Login.setBounds(354, 403, 200, 21);
		this.add(btnLI_Login);

		JButton btnLI_Cancel = new JButton("\uC885\uB8CC"); // this창 종료 버튼
		btnLI_Cancel.setBounds(564, 403, 200, 21);
		this.add(btnLI_Cancel);

		btnLI_Cancel.addActionListener(new ActionListener() { // 클라이언트 종료 버튼

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		JLabel labelLI_this = new JLabel("Game Client");
		labelLI_this.setHorizontalAlignment(SwingConstants.CENTER);
		labelLI_this.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		labelLI_this.setBounds(354, 240, 404, 40);
		this.add(labelLI_this);

		btnLI_SignUp = new JButton("\uC0C8 \uACC4\uC815 \uB9CC\uB4E4\uAE30"); // 회원가입 패널로 가는 버튼

		btnLI_SignUp.setBounds(546, 445, 218, 21);
		this.add(btnLI_SignUp);

		JLabel label_1 = new JLabel("\uACC4\uC815\uC774 \uC5C6\uC73C\uC2E0\uAC00\uC694?");
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_1.setBounds(428, 446, 164, 18);
		this.add(label_1);

		this.setVisible(true);
	}

}
