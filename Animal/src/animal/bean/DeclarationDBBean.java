package animal.bean;

import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DeclarationDBBean {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	private static DeclarationDBBean  instance = new DeclarationDBBean();
	
	public static DeclarationDBBean  getinstance() {
		return instance;
	}
	
	private DeclarationDBBean() {
		try {
			String dbURL = "jdbc:mysql://203.249.22.34:3306/web?autoReconnect=true&useSSL=false";
			String dbID = "jy";
			String dbPW = "1365";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPW);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//신고접수 번호 
	public int getNext() {
		String SQL="SELECT declaration_num FROM declaration ORDER BY declaration_num DESC";
		
			try {
				pstmt=conn.prepareStatement(SQL);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					return rs.getInt(1)+1;
				}
				return 1;//현재가 첫번째 게시글인 경우
			}catch(Exception e) {
				e.printStackTrace();	
				}
			return -1;
		}
	
	//DB에 입력 글쓰기
		public int declaration(DeclarationDataBean declaration) {
			String SQL="INSERT INTO declaration VALUES (?, ?, ?, ?)";
			try {
				PreparedStatement pstmt=conn.prepareStatement(SQL);
				pstmt.setInt(1, declaration.getBoard_num());
				pstmt.setString(2, declaration.getUser_id());
				pstmt.setString(3, declaration.getDeclaration_content());
				pstmt.setInt(4, getNext());
				return pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1;
		}
		
		//이미 신고한 사람인지 check 하나의 게시글을 여러번 신고할 수 없음
		public int check_id(DeclarationDataBean declaration) {
			String SQL="SELECT * FROM declaration WHERE user_id = ? AND board_num = ?";
			try {
				PreparedStatement pstmt=conn.prepareStatement(SQL);
				pstmt.setString(1, declaration.getUser_id());
				pstmt.setInt(2, declaration.getBoard_num());
				rs = pstmt.executeQuery();
				if(rs.next())
					return 1;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1;	
		}
		
		//모든 신고 목록을 보여준다.(은진이가 사용하는부분)
		public ArrayList<DeclarationDataBean> declaration_getList(){
			String SQL="SELECT * FROM declaration ";
			ArrayList<DeclarationDataBean> list = new ArrayList<DeclarationDataBean>();
			
			try {
					PreparedStatement pstmt=conn.prepareStatement(SQL);
					rs=pstmt.executeQuery();

				while(rs.next()) {
					DeclarationDataBean declaration = new DeclarationDataBean();
					declaration.setBoard_num(rs.getInt(1));
					declaration.setUser_id(rs.getString(2));
					declaration.setDeclaration_content(rs.getString(3));
					declaration.setDeclaration_num(rs.getInt(4));
					list.add(declaration);
				}
				return list;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
