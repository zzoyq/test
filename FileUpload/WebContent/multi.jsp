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
		스토리 설명:
		여러개의 파일을 업로드 할수 있도록 여러개의 <input type="file">태그를 만드는데..
		이왕이면 딱 정해진 개수를 만드는 것 보다.. 사용자가 업로드할 파일개수를 정해서 입력받아
		<input>태그의 개수를 동적으로 늘려서 파일을 업로드 해보자.
	 --%>
	 
	<%
		//1번 form태그에서 요청한 데이터 한글처리
		request.setCharacterEncoding("UTF-8");
	%>
	
	<%!
		//1번 form태그의 input태그의 value=""에서 요청하는 메소드 만들기
		public String getParam(HttpServletRequest request, String param){
		 	//매개변수 reques로 전달 받은??????	
			//request내장객체 영역에 입력받은 정보가 존재 하면?
		     if(request.getParameter(param) != null){
		    	 
		    	 return request.getParameter(param);
		     
		     }else{//1번 form에서 입력 받은 정보가 없으면  ""공백을 리턴 함
		    		
		    	 return "";
		     }		
	    }//getParam메소드 끝
	%>
	<%--1번 form태그 : 업로드할 파일수를 입력받는 곳 --%>	
	<form action="multi.jsp" method="post">
		이름 : <input type="text" name="name" value='<%=getParam(request, "name")%>'/> <br>
		주소 : <input type="text" name="addr" value='<%=getParam(request,"addr")%>'/> <br>
		하고싶은말: <br>
		<textarea  rows="3" cols="70" name="note"><%=getParam(request, "note")%></textarea> <br>
		업로드할 파일수: <input type="text" name="add" size="2"/>
		<input type="submit" value="확인"/>
	</form>
	
	<%
		//업로드하기 위해 입력한 파일 개수를 저장할 변수
		int filecnt = 0;
	
		//1번 form태그 내부의  <input>태그의 name속성값이 add인 요청값이 있으면?
		if(request.getParameter("add")  != null){
			//입력 받은 파일수 저장
			filecnt = Integer.parseInt( request.getParameter("add") );
		}
	
	%>
	<%--
		 2번 form
		 -> 1번 form태그 내부에서 입력한 업로드할 파일수를 전달 받아..
		    업로드할 파일 개수 만큼 for문을 이용하여 <input type="file">태그를 만들어서
		   2번 form태그를 이용하여  multi_pro.jsp페이지로  다중파일 업로드 요청함.
	 --%>
	 <form action="multi_pro.jsp" enctype="multipart/form-data" method="post">
	 
	 	<input type="hidden" name="name" value='<%=getParam(request, "name")%>'>
	 	<input type="hidden" name="addr" value='<%=getParam(request, "addr")%>'>
	  	<input type="hidden" name="note" value='<%=getParam(request, "note")%>'>
	  
	 	<%
	 		//for문을 이용하여 입력받은 파일 개수 만큼  <input type="file">인 태그를 만들어 준다.
	 		for(int i=0; i<filecnt; i++){
	 	%>					<%-- 파일업로드시 input태그의 name속성값을 모두 달리 해 주어야함 --%>
	 		 <%=i+1%>번째 파일 선택 : <input type="file" name="upFile<%=i%>"/> <br>
	 	<%	
	 		}
	 	%>
	 	 	<input type="submit" value="업로드 요청"/>
	 </form>
	
	


</body>
</html>




