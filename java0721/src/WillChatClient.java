import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WillChatClient extends Thread {

	// GUI
	JFrame frm;
	JTextArea ta;
	JTextField tf;

	String IP = "";

	// 소켓 통신
	Socket socket;

	DataInputStream dis;
	DataOutputStream dos;

	boolean stopSignal;

	public WillChatClient() {
		launchFrame();
	}

	// 화면 구현
	public void launchFrame() {
		frm = new JFrame("채팅 클라이언트(1:1)");
		frm.setBounds(200, 200, 500, 300);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 메세지 입력창
		ta = new JTextArea();
		ta.setBackground(Color.ORANGE);
		ta.setEditable(false);
		frm.add(ta, BorderLayout.CENTER);

		// 메세지 전송창
		tf = new JTextField();
		frm.add(tf, BorderLayout.SOUTH);
		// 입력창 이벤트 처리  - 내부클래스(중첩클래스) 
		tf.addActionListener(listener);
		

		frm.setVisible(true);

		// 접속할 서버의 IP 입력 - JOptionPane
		IP = JOptionPane.showInputDialog(frm, "접속할 서버 IP 주소를 입력하시오.");

		// IP주소가 null 일경우 - 종료(프로세스 종료)
		if (IP == null)
			System.exit(0);

		// IP주소가 "" 일경우 - "localhost" 주소 지정
		IP = IP.trim(); // 공백제거
		if (IP.equals(""))
			IP = "localhost";

		// 입력창 포커스
		tf.requestFocus();

		// 통신 서버의 역활 실행
		service();

	}// GUI

	public void service() {
		// 클라이언트 서비스

		try {
			// * 해당 IP 주소에 해당하는 서버로 접속(+포트)
			// 소켓 객체 생성
			socket = new Socket(IP, 5000);

			ta.append(IP + "서버 접속 완료!!!\n");

			// 데이터 입출력을 위한 스트림 생성
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			// 멀티쓰레딩 처리
			this.start();
			System.out.println("멀티 쓰레드 작동중 (서버 데이터 수신중...)");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // 통신
	
	// 이벤트 처리 중첩클래스(InnerClass)
	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				// 사용자 메세지를 입력받아서 서버로 전달
				String msg = tf.getText();
				ta.append("클라이언트 입력 : "+msg+"\n");
				
				// 사용자 exit 입력시  종료
				// 그외 나머지 서버로 전달 
				if(msg.trim().equals("exit")){
					// 입력창 비우기
					tf.setText("");
					
					// 대화상자 사용 종료
					int result =
					JOptionPane.showConfirmDialog(frm, "프로그램을 종료하시겠습니까?");
					
					if(result == JOptionPane.YES_OPTION){
						// 예 버튼을 눌렀을때 
						stopSignal = true;
						
						// 종료 메세지 확인
						ta.append("클라이언트 : 서버와의 연결을 종료합니다! \n");
						
						// 자원 반환 (연결 해제)
						dos.close();
						socket.close();
						
					}
					
					
				}else{// "exit" 아닌 모든 데이터를 서버로 전달
					dos.writeUTF("클라이언트 : "+msg);
					
					tf.setText("");					
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
			
		}
	}; // 이벤트 처리
	
	

	@Override
	public void run() {
		// 서버랑 동일한 동작(계속해서 서버의 메세지를 입력)
		try {
			while (!stopSignal) {
				// 클라이언트 메세지
				ta.append(socket.getInetAddress() + ":" + dis.readUTF() + "\n");
			}

			dis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}// run()

	public static void main(String[] args) {
		new WillChatClient();

	}

}
