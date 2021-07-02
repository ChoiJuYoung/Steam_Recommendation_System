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

		textSU_ID = new TextField(); // ���̵� �Է�
		textSU_ID.setBounds(441, 304, 317, 21);
		this.add(textSU_ID);
		textSU_ID.setColumns(10);

		textSU_pass = new TextField(); // ��й�ȣ �Է�
		textSU_pass.setBounds(441, 345, 317, 21);
		this.add(textSU_pass);
		textSU_pass.setColumns(10);

		JLabel labelSU_ID = new JLabel("\uACC4\uC815 \uC774\uB984");
		labelSU_ID.setFont(new Font("���� ���", Font.PLAIN, 12));
		labelSU_ID.setHorizontalAlignment(SwingConstants.CENTER);
		labelSU_ID.setBounds(354, 299, 83, 31);
		this.add(labelSU_ID);

		textSU_pass.setEchoChar('*');

		JLabel labelSU_Password1 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		labelSU_Password1.setFont(new Font("���� ���", Font.PLAIN, 12));
		labelSU_Password1.setHorizontalAlignment(SwingConstants.CENTER);
		labelSU_Password1.setBounds(354, 340, 83, 31);
		this.add(labelSU_Password1);

		btnSU_SignIn = new JButton("\uD68C\uC6D0 \uAC00\uC785"); // this ��ư
		btnSU_SignIn.setBounds(354, 443, 200, 21);
		this.add(btnSU_SignIn);

		btnSU_Cancel = new JButton("\uCDE8\uC18C");
		btnSU_Cancel.setBounds(564, 443, 200, 21);
		this.add(btnSU_Cancel);

		JLabel labelSU_��������� = new JLabel("\uACC4\uC815 \uB9CC\uB4E4\uAE30");
		labelSU_���������.setHorizontalAlignment(SwingConstants.CENTER);
		labelSU_���������.setFont(new Font("���� ���", Font.BOLD, 30)); //�۾�ü �ʹ� ������ �������� ���Ͽ���
		labelSU_���������.setBounds(355, 240, 403, 40);
		this.add(labelSU_���������);
		
		this.setVisible(false);
	}

}
