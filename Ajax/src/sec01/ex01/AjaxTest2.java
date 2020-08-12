package sec01.ex01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.awt.RepaintArea;

//ajax2.html에서 서블릿클래스를 요청한 주소를 웹브라우저를 거쳐 전송 받는다.
//요청한 주소 ->  http://localhost:8080:/Ajax/ajaxTest2
//요청한 주소중.. 컨텍스트 패스 뒤에 입력한 ajaxTest2라는 주소와
//@WebServlet어노테이션 기호안에 작성한  /ajaxTest2라는 주소가 매칭되어
//현재 아래의 AjaxTest2라는 이름의 서블릿클래스를 GET방식 또는 POST전송 방식으로 요청 하여 받는다
@WebServlet("/ajaxTest2")
public class AjaxTest2 extends HttpServlet {
	
//doGet, doPost메소드 오버라이딩 
	
//클라이언트가  GET방식으로 요청이 들어오면 호출되는 콜백메소드 
	//호출될때 요청값들을 저장하고 있는 request내장객체 영역을 매개변수로 전달 받는다.
	//또한 요청한 클라이언트의 웹브라우저로 출력(응답)할  response객체를 매개변수로 전달 받는다.
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req, resp);
	}

//클라이언트가 POST전송 방식으로 요청을 하면 호출되는 콜백 메소드 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandle(req, resp);
	}

	protected void doHandle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//한글처리
		req.setCharacterEncoding("UTF-8");
		//클라이언트의 웹브라우저로 내보낼(응답할,출력할) 데이터형식을 respose객체에 헤더영역에 설정
		resp.setContentType("text/html;charset=utf-8");
		//클라이언트의 웹브라우저로 출력할 출력스트림 통로 생성
		PrintWriter out = resp.getWriter();
		String result=""; //응답할 데이터를 저장
		
		// <![CDATA[........]]>
		//xml데이터를 작성할떄   '<' , '>'  '&'를 사용해야 하는 경우가 있는데...
		//xml에서 그냥 사용할경우 태그로 인식하는 경우가 종종 있다.
		//태그가 아니라  실제로 연산의 기호에 필요한 코드라고  웹브라우저에게 알려 줘야 하므로
		//<![CDATA[ 값  ]]>
		
		result="<main><book>"+
		         "<title><![CDATA[자바의 정석]]></title>" +
		         "<writer><![CDATA[저자  | 남궁성]]></writer>" +                             
		         "<image><![CDATA[http://localhost:8080/Ajax/image/image1.jpg]]></image>"+
		      "</book>"+
		      "<book>"+
		         "<title><![CDATA[JSP & Servlet]]></title>" +
		         "<writer><![CDATA[저자 | 오정원]]></writer>" +                 
		        "<image><![CDATA[http://localhost:8080/Ajax/image/image2.jpg]]></image>"+
		      "</book></main>";
		
		out.print(result); //클라이언트의 웹브라우저를 거쳐서!!!!-> ajax2.html페이지의 
						   // $.ajax메소드 내부로 응답데이터를 전송합니다.
	}
}









