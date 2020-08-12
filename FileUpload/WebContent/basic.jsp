
<%@page import="java.io.File"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//파일 업로드를 직접 담당 하는 cos.jar파일 내부에 있는 MultipartRequest클래스!!
	
	//1. 현재 실행중인 웹프로젝트에 대한 정보를 지니고 있는 Context객체 얻기
	ServletContext  ctx = getServletContext();
	

	//2.업로드 되는 실제 경로를 반환 해준다.
	//요청 주소 : http://localhost:8080/FileUpload/basic.jsp
	//리턴 값 : D:\workspace_jsp6\FileUpload\WebContent\ upload 
	String realPath =  ctx.getRealPath("upload");
	
	//3.업로드할수 있는 최대 용량 사이즈 지정 100MB
	int max = 100 * 1024 * 1024;

	//4. 실제 파일 업로드를 위해  
	//   MultipartRequest객체 생성시 생성자를 호출하면서 업로드할 정보를 전달하여 업로드
	//  인수1) form태그에서 가져온 인자값을 얻어오기 위해 request객체를 전달
    //  인수2) 업로드될 파일의 위치를 전달
	//  인수3) 업로드될 최대 용량 사이즈 지정 100MB 전달
    //  인수4) 업로드할 파일명이 한글이면 
			//한글이깨지면서 업로드 되는 것을 방지 하기 위해 인코딩 방식을 UTF-8로 설정
	//  인수5) 업로드 되는 경로에 똑같은 이름의 파일이 업로드 되는 것을 방지 하기위해
    //        똑같은 이름의 파일업로드시.. 파일명뒤에 1을 자동으로 붙여 서 파일명을 변경시켜 주는
    //        객체를 전달~
    MultipartRequest multi = 
    new MultipartRequest(request,realPath,max,"UTF-8",new DefaultFileRenamePolicy());

	//basic.html에서 입력한 사용자, 제목 얻기 
	String user = multi.getParameter("user");
	String title = multi.getParameter("title");
	out.println(user + "," + title);
	
	out.println("<hr/>");
	
	//form태그에서 파일업로드를 하기 위해
	//<input type="file" name="upFile1">태그
	//<input type="file" name="upFile2">태그
	//<input type="file" name="upFile3">태그
	//를 선택후  파일업로드를 요청 했다.	
	//위 <input>태그들의 name속성값들을 얻어 Enumeration반복기 역할을 하는 객체에 저장후 
	//Enumeration반복기 객체 자체를? 반환
	Enumeration e = multi.getFileNames(); 
	
	while(e.hasMoreElements()){ //위의 <input>태그의 name속성값들이 
								//Enumeration반복기객체에 저장되어 있는 동안 반복
		//파일 업로드를 하기 위해 선택했던 <input>태그의 name속성값을 Enumeration반복기 내부에서 꺼내옴
		String name = (String)e.nextElement();
		
		out.println("클라이언트가 업로드한 파일의 원본이름 : " 
					+ multi.getOriginalFileName(name) + "<br>" );
		
		out.println("서버에 실제로 업로드한 파일 명 얻기 : "
					+ multi.getFilesystemName(name) + "<br>" );
		
		out.println("업로드한 파일 타입 : " + multi.getContentType(name) + "<br>" );
		
		//서버에 업로드된 파일에 접근하기 위해 File객체 얻기
		File f = multi.getFile(name);
		
		out.print("업로드한 파일의 크기 : " + f.length() + "byte<br>");
		
	}
	
%>






