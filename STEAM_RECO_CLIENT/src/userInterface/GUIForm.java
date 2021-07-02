package userInterface;

import socketPackage.Communicator;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class GUIForm extends JFrame {
	static Communicator com;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIForm frame = new GUIForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIForm() {
		com = Communicator.getInstance();
		
		setTitle("\uAC8C\uC784 \uD074\uB77C\uC774\uC5B8\uD2B8");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Login login = new Login();
		Main main = new Main(); 
		SignIn signin = new SignIn();
		Search search = new Search();
		
		contentPane.add(login);
		contentPane.add(main);
		contentPane.add(signin);
		contentPane.add(search);

		login.btnLI_SignUp.addActionListener(new ActionListener() { // ȸ������ȭ������ �Ѿ�� ��ư
			public void actionPerformed(ActionEvent e) {
				signin.setVisible(true);
				login.setVisible(false);
			}
		});

		login.btnLI_Login.addActionListener(new ActionListener() { // �α��� ��ư ��������~

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = login.textLI_ID.getText();
				String pw = login.textLI_Password.getText();

				String result = com.login(id, pw);

				if (result.equals("FAIL")) {
					JOptionPane.showMessageDialog(null, "�α����� �����Ͽ����ϴ�.");
					return;
				} else {
					main.setVisible(true);
					login.setVisible(false);
				}
				
				main.login_OK();
			}
		});
		
		main.btnLogoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.setVisible(false);
				login.setVisible(true);
			}
		});

		main.btnSearch.addActionListener(new ActionListener() { // �˻�ȭ������ �Ѿ�� ��ư
			public void actionPerformed(ActionEvent e) {
				search.searchGameReco(main.txtSearch.getText());
				search.txtSearch.setText(main.txtSearch.getText());
				
				main.setVisible(false);
				search.setVisible(true);
			}
		});
		
		signin.btnSU_SignIn.addActionListener(new ActionListener() { // ȸ������ & ����

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String id = signin.textSU_ID.getText();
					String pw = signin.textSU_pass.getText();
					String result = com.signin(id, pw);

					if (result.equals("EXIST"))
						JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ID�Դϴ�.");
					else if (result.equals("ERR"))
						JOptionPane.showMessageDialog(null, "�߸��� IDȤ�� password�Դϴ�.");
					else {
						JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
						signin.setVisible(false);
						login.setVisible(true);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "ȸ�����Կ� �����Ͽ����ϴ�.");
				}
			}
		});

		signin.btnSU_Cancel.addActionListener(new ActionListener() { // �α���ȭ������ �Ѿ�� ��ư

			@Override
			public void actionPerformed(ActionEvent e) {
				signin.setVisible(false);
				login.setVisible(true);
			}
		});
		
		search.btnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search.setVisible(false);
				main.setVisible(true);
			}
		});
		
		search.btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search.searchGameReco(search.txtSearch.getText());
				main.txtSearch.setText(search.txtSearch.getText());
			}
		});
		
	}
}
