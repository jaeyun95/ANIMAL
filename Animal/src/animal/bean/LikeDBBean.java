package animal.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LikeDBBean {
	private Connection conn = null;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static LikeDBBean instance = new LikeDBBean();
	
	public static LikeDBBean getinstance() {
		return instance;
	}
	
	private LikeDBBean() {
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
	
	
	//like_num을 구해주는 메소드
	public int getNext() {
		String SQL="SELECT like_num FROM user_like ORDER BY like_num DESC";
		try {
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 1;//처음일 경우
		}catch(Exception e) {
			e.printStackTrace();	
		}
		return -1;
	}
	
	//좋아요 추가
	public int add(LikeDataBean like) {
		String SQL="INSERT INTO user_like VALUES (?, ?, ?)";
			try {
				PreparedStatement pstmt=conn.prepareStatement(SQL);
				pstmt.setInt(1, like.getBoard_num());
				pstmt.setInt(2, getNext());
				pstmt.setString(3, like.getUser_id());
				return pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1;
		}
	
	//좋아요 삭제
	public int delete(LikeDataBean like) {
		String SQL="DELETE FROM user_like WHERE user_id = ? AND board_num = ?";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, like.getUser_id());
			pstmt.setInt(2, like.getBoard_num());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//이미 좋아요 한 사람인지 check 하나의 게시글을 여러번 좋아요 할 수 없음
	public int check_id(LikeDataBean like) {
		String SQL="SELECT * FROM user_like WHERE user_id = ? AND board_num = ?";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, like.getUser_id());
			pstmt.setInt(2, like.getBoard_num());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return -1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			return 1;	
	}
}

