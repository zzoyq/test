<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%-- 클라이언트페이지 ajax1.html페이지에서 Ajax기술로 메세지를 보내면 
	         처리를 하는 서버페이지(JSP)입니다. 
	         
		 웹브라우저를 거쳐  속성이름인 param으로  데이터를 보내면 
		 request객체의 getParameter()를 이용해
		 요청한 데이터를 가져옵니다.
		 
		 그리고 ajax1.jsp서버페이지에서 응답할 데이터를 마련해서 out내장객체의 print()메소드를 통해
		 웹브라우저로 응답할 데이터를 내보내어 -> 웹브라우저를 거쳐 -> 요청한 페이지인? ajax1.html로 전송함
	--%>
	<%
		//한글처리
		request.setCharacterEncoding("UTF-8");
		//ajax1.html에서 요청한 값 얻기 
		String param = request.getParameter("param"); //Hello,jQuery
		
		//요청값을 얻어서 응답할 값 마련
		String result =  param + " 프레임워크 Ajax입니다.";
		
		//out.print()
		out.print(result);
	%>
	

</body>
</html>



