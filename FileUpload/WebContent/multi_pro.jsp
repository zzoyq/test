<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<%--
			multi.jsp의 2번 form태그에서 요청한 데이터 + 업로드할 파일은???
			request내장객체 영역에 유지 되고 있다.
			request내장 객체 영역에 저장된 값 가져오기
		 --%>
		 <%
		 	//한글처리
		 	request.setCharacterEncoding("UTF-8");
		 
		 	//업로드할 실제 서버의 경로 얻기
		 	String realFolder = getServletContext().getRealPath("/upload");
		 	
		 	//업로드 할수 있는 최대 용량 100MB
		 	int max = 100 * 1024 * 1024;
		 	
		 	//실제 파일 업로드를 담당하는 MultipartRequest객체 생성시 업로드할 정보를 전달 하여
		 	//다중 파일 업로드를 진행함
		 	MultipartRequest multi = 
		 	new MultipartRequest(request,
		 						 realFolder,
		 						 max,"UTF-8",new DefaultFileRenamePolicy());
		 
		 	//입력했던 텍스트값들 얻기
		 	String name = multi.getParameter("name");
		 	String addr = multi.getParameter("addr");
		 	String note = multi.getParameter("note");
		 	
		 	//서버에 실제로 업로드된 파일 명을 저장할 컬렉션프레임워크의  ArrayList생성
		 	ArrayList saveFiles = new ArrayList();
		 	
		 	//클라이언트가 업로드한 파일의 원본명을 하나씩저장할 ArrayList생성
		 	ArrayList originFiles = new ArrayList();
		 	
		 	//파일 업로드 당시 선택했던 <input>태그들의 name속성값들을 모두~ 
		 	//Enumeration반복기객체에담아 반환
		 	Enumeration e = multi.getFileNames();
		 
		 	while(e.hasMoreElements()){
		 		//파일 업로드 당시 선택했던 <input>태그들의 name속성의 값들을 하나씩 차례로 얻는다.
		 		String filename = (String)e.nextElement();
		 		
		 		//서버에 실제로 업로드된 파일명을 하나씩 하나씩 얻어  ArrayList에 저장
		 		saveFiles.add(multi.getFilesystemName(filename));
		 		
		 		//클라이언트가 업로드한 파일의 원본이름을 하나씩 하나씩 얻어 ArrayList에 저장
		 		originFiles.add(multi.getOriginalFileName(filename));
		 		
		 	}
		 	
		 %>

		<ul>
			<li>이름 : <%=name %></li>
			<li>주소 : <%=addr %> </li>
			<li>하고 싶은말 : <%=note %></li>
		</ul>
		
		<hr/>
		
		파일리스트 <br>
		<ul>
			<%  //서버에 실제로 업로드된 파일명의 갯수만큼 반복
				for(int i=0;  i<saveFiles.size();   i++){
			%>		
				<li>
					<a href="#"><%=originFiles.get(i) %></a>
				</li>
			<%		
				}
			%>
		</ul>
		
		


</body>
</html>





