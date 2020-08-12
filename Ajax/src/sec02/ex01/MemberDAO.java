package sec02.ex01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//DB와 연결하여 DB작업할 클래스 
public class MemberDAO {
	
	//DB작업 재료 준비 
	private DataSource dataFactory; //커넥션풀 저장할 변수 
	private Connection con; //커넥션 객체를 저장할 변수 
	private PreparedStatement pstmt; //SQL문을 DB로 전송하여 실행할 객체를 저장할 변수
	private ResultSet rs;//조회한 정보를 임시로 저장할 용도의 객체가 저장될 변수 
	
	
	//생성자 : 커넥션풀 얻기 
	public MemberDAO(){
		try{
			 Context ctx = new InitialContext(); //하나의 웹프로젝트의 정보를 지니고 있는 객체
			 //커넥션풀 얻기 
			 dataFactory = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
		}catch(Exception e){
			System.out.println("DataSource커넥션풀 얻기 실패 : " + e);
		}
	}
	//사용자가 입력한 ID를 서블릿으로 부터 전달받아..
	//SELECT구문에 조건식의 값으로 설정 한후  입력한 ID에 해당하는 회원정보를 조회하여
	//그결과를 true 또는 false로 반환함.
	public boolean overlappedID(String id){
					
		//아이디 중복 또는 중복아님을 저장할 변수
		boolean result = false;
		
		try{
			//DB연결을 위한 DataSource커넥션풀로 부터 커넥션 하나 빌려오기
			con = dataFactory.getConnection();
			//오라클의 decode()함수를 이용하여 서블릿에서 전달된 ID에 해당하는 데이터를 검색하여
			//true 또는 false를 반환 하는데...
			//검색한 갯수가 1이면  true를 반환, 존재하지 않으면 false를 문자열로 반환하여 조회함
			String query = "select decode(count(*),1,'true','false') as result from t_member";
				   query += " where id=?";
				   
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();
			rs.next();
			//문자열 "true" 또는 "false"를 boolean자료형으로 변환하여 저장
			result =  Boolean.parseBoolean(rs.getString("result"));
			
		}catch(Exception err){
			System.out.println("overlappedID메소드 내부에서 SQL실행 오류 : " + err);
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(rs != null) rs.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}










