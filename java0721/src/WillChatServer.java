import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WillChatServer extends Thread {

	// GUI
	JFrame frm;
	JTextField tf;
	JTextArea ta;

	// 통신
	// 서버 소켓 생성
	ServerSocket serverSocket;
	// 클라이언트 통신을 위한 소켓
	Socket socket;

	// 데이터 입출력 스트림
	DataInputStream dis;
	DataOutputStream dos;

	// 통신 지속여부 판단
	boolean stopSignal;

	public WillChatServer() {
		launchFrame();
	}

	// 화면 구현
	public void launchFrame() {
		frm = new JFrame("채팅 서버");
		frm.setBounds(200, 200, 500, 300);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 메세지 입력창
		ta = new JTextArea();
		ta.setBackground(Color.LIGHT_GRAY);
		ta.setEditable(false);
		frm.add(ta, BorderLayout.CENTER);

		// 메세지 전송창
		tf = new JTextField();
		frm.add(tf, BorderLayout.SOUTH);
		tf.addActionListener(listener);
		

		frm.setVisible(true);

		// 입력창 포커스
		tf.requestFocus();

		// 통신 서버의 역활 실행
		service();

	}// GUI

	// 서버의 역활(통신 소켓)
	public void service() {
		ta.append("서비스 동작을 위한 서버 준비중.....\n");
		try {
			// 서버 소켓 생성 (5000) -> 서버 대기중.... 메시지 출력
			serverSocket = new ServerSocket(5000);
			ta.append("서버 소켓 개방: 클라이언트와 통신 준비 완료.....\n");

			// 연결 대기상태
			socket = serverSocket.accept();
			ta.append("클라이언트 접속 완료 : " + socket.getInetAddress() + "\n");

			// 데이터 입출력을 위한 스트림 생성
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			// 데이터 입출력 처리를 멀티 쓰레딩으로 처리
			this.start();
			System.out.println("멀티 쓰레드 작동중 (클라이언트 데이터 수신중...)");

			// 접속한 클라이언트계저에 인사 메세지 출력
			dos.writeUTF(" 서버 : 채팅 서버에 접속을 환영합니다! \n" + " 궁금하신 사항을 입력하시면 빠른시간 내에 응답 드리겠습니다!");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}// 통신

	// 이벤트 처리 중첩클래스(InnerClass)
	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				// 사용자 메세지를 입력받아서 클라이언트로 전달
				String msg = tf.getText();
				ta.append("서버 입력 : " + msg + "\n");

				// 사용자 exit 입력시 종료
				// 그외 나머지 클라이언트로 전달
				if (msg.trim().equals("exit")) {
					
					tf.setText("");
					
					// 클라이언트에 서버 종료 메세지를 전달
					dos.writeUTF(" 서버가 종료 됩니다! ");
					
					JOptionPane.showMessageDialog(frm, "서버 종료!");
					
					System.exit(0);

				} else {// "exit" 아닌 모든 데이터를 클라이언트 로 전달
					dos.writeUTF("서버 : " + msg);

					tf.setText("");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}; // 이벤트 처리

	// run()
	@Override
	public void run() {
		// 멀티쓰레딩 처리 : 클라이언트 메세지를 수신하면서 동시에 서버 메세지를 전달
		try {
			while (!stopSignal) {
				// 클라이언트 메세지
				ta.append(socket.getInetAddress() + ":" + dis.readUTF() + "\n");
			}

			dis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	// run()

	public static void main(String[] args) {
		new WillChatServer();
	}

}
