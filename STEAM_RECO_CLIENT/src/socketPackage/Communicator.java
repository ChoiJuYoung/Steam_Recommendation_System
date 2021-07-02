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
	
	public String signin(String id, String pw) // 회원가입 요청하는 메소드. 매개변수로 id와 password 넘겨줌.
	{
		String line = ""; // 서버로부터의 입력 받아올 변수
		sendMsg("signinchk//" + id); // 일단 ID 중복 검사 요청
		line = rcvMsg(); // 서버 응답 받아옴
		if(line.equals("EXIST")) return line; // 만약 중복되는 ID 존재시 "EXIST" return
		
		sendMsg("signinreq//" + id + "__" + pw); // 중복되는 ID 미존재시 회원가입 요청
		line = rcvMsg(); // 서버 응답 받아옴.
		return line; // 서버 응답 return. SIGN IN OK면 성공, ERR면 실패.
	}
	
	public String login(String id, String pw) // 로그인 요청하는 메소드. 매개변수로 id와 password 넘겨줌.
	{
		String line = ""; // 서버로부터의 입력 받아올 변수
		sendMsg("login//" + id + "__" + pw); // 로그인 요청
		line = rcvMsg();  // 서버 응답 받아옴
		if(line.equals("INCORRECT ID OR PASSWD")) return "FAIL"; // ID나 비밀번호 틀리면 FAIL return
		
		idNum = Integer.parseInt(line); // 성공 시 일련번호가 오는데 Communicator에 저장
		
		return "OK"; // 로그인 성공하였음을 알림. return OK
	}
	
	public String[] gameReco() // 추천 게임 요청 메소드.
	{
		String line = ""; // 서버로부터의 입력 받아올 변수
		sendMsg(idNum + "+game"); // 추천 게임 요청
		line = rcvMsg(); // 서버 응답 받아옴
		if(!line.contains("game")) return null; // game이라는 단어가 없으면 null return
		
		String[] tmp = line.split("//")[1].split("__"); // 구분자로 구분
		
		return tmp; // 리턴. 형식은 String array.
	}
	
	public String[] friendReco()
	{
		String line = ""; // 서버로부터의 입력 받아올 변수
		sendMsg(idNum + "+friend"); // 추천 친구 요청
		line = rcvMsg(); // 서버 응답 받아옴
		if(!line.contains("friend")) return null; // friend라는 단어가 없으면 null return
		
		String[] tmp = line.split("//")[1].split("__"); // 구분자로 구분
		
		return tmp; // 리턴. 형식은 String array.
	}

	public String[] achieveReco()
	{
		String line = ""; // 서버로부터의 입력 받아올 변수
		sendMsg(idNum + "+achieve"); // 추천 업적 요청
		line = rcvMsg(); // 서버 응답 받아옴
		if(!line.contains("achieve")) return null; // achieve라는 단어가 없으면 null return
		
		String[] tmp = line.split("//")[1].split("__"); // 구분자로 구분
		
		return tmp; // 리턴. 형식은 String array.
		// 얘는 좀 특별함. 각 항목들이 (title, name, description)으로 이루어진 배열임.
	}

	public String[] searchGame(String word)
	{
		String line = ""; // 서버로부터의 입력 받아올 변수
		sendMsg(idNum + "+search//" + word); // 게임 검색 요청
		line = rcvMsg(); // 서버 응답 받아옴
		if(!line.contains("search")) return null; // search이라는 단어가 없으면 null return
		
		try
		{
			String[] tmp = line.split("//")[1].split("__"); // 구분자로 구분
			return tmp; // 리턴. 형식은 String array.
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public String registReview(String appid, String time)
	{
		String line = ""; // 서버로부터의 입력 받아올 변수
		sendMsg(idNum + "+review//" + appid + "," + time); // 리뷰 등록 요청
		line = rcvMsg(); // 서버 응답 받아옴
		
		switch(line)
		{
		case "ERR":
			return "리뷰를 등록할 수 없습니다.";
		case "OK":
			return "등록이 완료되었습니다.";
		case "DUPLI":
			return "이미 추천한 게임입니다.";
		}
		
		return line;// OK는 성공, ERR는 실패.
	}
}
