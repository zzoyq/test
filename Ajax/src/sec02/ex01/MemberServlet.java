package sec02.ex01;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//클라이언트의 요청을 받는 서버페이지(톰캣서버가 실행하는 페이지)
public class MemberServlet extends HttpServlet{
	
//	doGet()메소드 -> 클라이언트가 GET방식으로 요청이 들어오면 호출되는 콜백 메소드
//	doPost()메소드 -> 클라이언트가 POST방식으로 요청이 들어오면 호출되는 콜백 메소드
	
	@Override
	protected void doPost(HttpServletRequest request,
			              HttpServletResponse response) 
			            		  throws ServletException, IOException {
		//한글처리
		request.setCharacterEncoding("UTF-8");
		//클라이언트의 웹브라우저로 출력후 ~ ajax3.html에 전송할 데이터 형식을 지정
		response.setContentType("text/html;charset=utf-8");
		
		/*클라이언트가 서블릿으로 요청하는 값  */
		//사용자가 ajax3.html에서 입력한 아이디를 얻는다
		String id = request.getParameter("id");
		
		//사용자가 입력한 아이디를 이용해 DB작업 하기 위한 객체 생성
		MemberDAO memberDAO = new MemberDAO();
		//ID중복 체크여부를 확인 하기 위해 MemberDAO객체의 overIappedID(String id)메소드 호출시
		//사용자가 입력한 아이디를 인수로 전달 함
		boolean overlappedID = memberDAO.overlappedID(id);
		
		//클라이언트의 웹브라우저로 출력할 스트림 통로 생성
		PrintWriter out = response.getWriter();
		
		//ID중복 여부 결과를 ajax3.html페이지에 메세지로 전송함
		if(overlappedID == true){
			out.print("not_usable");
		}else{
			out.print("usable");
		}	
	}
}






