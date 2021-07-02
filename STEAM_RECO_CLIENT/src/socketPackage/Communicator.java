package socketPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Communicator 
{
	private static Communicator com;
    private Socket sock;
	private PrintWriter pw;
	private BufferedReader in;
	private static int idNum = 0;
	
	private Communicator()
	{
		try
		{
			sock = new Socket("godzero.iptime.org", 6000);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static Communicator getInstance()
	{
		if(com == null) com = new Communicator();
		return com;
	}

	private void sendMsg(String msg)
	{
		pw.println(msg);
		pw.flush();
	}

	private String rcvMsg()
	{
		try 
		{
			String line = in.readLine();
			return line;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String signin(String id, String pw) // ȸ������ ��û�ϴ� �޼ҵ�. �Ű������� id�� password �Ѱ���.
	{
		String line = ""; // �����κ����� �Է� �޾ƿ� ����
		sendMsg("signinchk//" + id); // �ϴ� ID �ߺ� �˻� ��û
		line = rcvMsg(); // ���� ���� �޾ƿ�
		if(line.equals("EXIST")) return line; // ���� �ߺ��Ǵ� ID ����� "EXIST" return
		
		sendMsg("signinreq//" + id + "__" + pw); // �ߺ��Ǵ� ID ������� ȸ������ ��û
		line = rcvMsg(); // ���� ���� �޾ƿ�.
		return line; // ���� ���� return. SIGN IN OK�� ����, ERR�� ����.
	}
	
	public String login(String id, String pw) // �α��� ��û�ϴ� �޼ҵ�. �Ű������� id�� password �Ѱ���.
	{
		String line = ""; // �����κ����� �Է� �޾ƿ� ����
		sendMsg("login//" + id + "__" + pw); // �α��� ��û
		line = rcvMsg();  // ���� ���� �޾ƿ�
		if(line.equals("INCORRECT ID OR PASSWD")) return "FAIL"; // ID�� ��й�ȣ Ʋ���� FAIL return
		
		idNum = Integer.parseInt(line); // ���� �� �Ϸù�ȣ�� ���µ� Communicator�� ����
		
		return "OK"; // �α��� �����Ͽ����� �˸�. return OK
	}
	
	public String[] gameReco() // ��õ ���� ��û �޼ҵ�.
	{
		String line = ""; // �����κ����� �Է� �޾ƿ� ����
		sendMsg(idNum + "+game"); // ��õ ���� ��û
		line = rcvMsg(); // ���� ���� �޾ƿ�
		if(!line.contains("game")) return null; // game�̶�� �ܾ ������ null return
		
		String[] tmp = line.split("//")[1].split("__"); // �����ڷ� ����
		
		return tmp; // ����. ������ String array.
	}
	
	public String[] friendReco()
	{
		String line = ""; // �����κ����� �Է� �޾ƿ� ����
		sendMsg(idNum + "+friend"); // ��õ ģ�� ��û
		line = rcvMsg(); // ���� ���� �޾ƿ�
		if(!line.contains("friend")) return null; // friend��� �ܾ ������ null return
		
		String[] tmp = line.split("//")[1].split("__"); // �����ڷ� ����
		
		return tmp; // ����. ������ String array.
	}

	public String[] achieveReco()
	{
		String line = ""; // �����κ����� �Է� �޾ƿ� ����
		sendMsg(idNum + "+achieve"); // ��õ ���� ��û
		line = rcvMsg(); // ���� ���� �޾ƿ�
		if(!line.contains("achieve")) return null; // achieve��� �ܾ ������ null return
		
		String[] tmp = line.split("//")[1].split("__"); // �����ڷ� ����
		
		return tmp; // ����. ������ String array.
		// ��� �� Ư����. �� �׸���� (title, name, description)���� �̷���� �迭��.
	}

	public String[] searchGame(String word)
	{
		String line = ""; // �����κ����� �Է� �޾ƿ� ����
		sendMsg(idNum + "+search//" + word); // ���� �˻� ��û
		line = rcvMsg(); // ���� ���� �޾ƿ�
		if(!line.contains("search")) return null; // search�̶�� �ܾ ������ null return
		
		try
		{
			String[] tmp = line.split("//")[1].split("__"); // �����ڷ� ����
			return tmp; // ����. ������ String array.
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public String registReview(String appid, String time)
	{
		String line = ""; // �����κ����� �Է� �޾ƿ� ����
		sendMsg(idNum + "+review//" + appid + "," + time); // ���� ��� ��û
		line = rcvMsg(); // ���� ���� �޾ƿ�
		
		switch(line)
		{
		case "ERR":
			return "���並 ����� �� �����ϴ�.";
		case "OK":
			return "����� �Ϸ�Ǿ����ϴ�.";
		case "DUPLI":
			return "�̹� ��õ�� �����Դϴ�.";
		}
		
		return line;// OK�� ����, ERR�� ����.
	}
}
