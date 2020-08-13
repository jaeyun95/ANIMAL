package animal.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ScrapDBBean {
	private Connection conn = null;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static ScrapDBBean instance = new ScrapDBBean();
	
	public static ScrapDBBean getinstance() {
		return instance;
	}
	
	private ScrapDBBean() {
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
	
	public int getNext() {
		String SQL="SELECT scrap_num FROM scrap ORDER BY scrap_num DESC";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 1;//현재가 첫번째 카테고리인 경우
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//스크랩 추가
	public int add_scrap(ScrapDataBean scrap) {
		String SQL="INSERT INTO scrap VALUES (?, ?, ?)";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, scrap.getUser_id());
			pstmt.setInt(2, getNext());
			pstmt.setInt(3, scrap.getBoard_num());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//한 사람이 스크랩한 리스트를 보여줌
	public ArrayList<ScrapDataBean> scrap_list(String user_id){
		String SQL="SELECT * FROM scrap WHERE user_id = ?";
		ArrayList<ScrapDataBean> list = new ArrayList<ScrapDataBean>();
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, user_id);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				ScrapDataBean scrap = new ScrapDataBean();
				scrap.setUser_id(rs.getString("user_id"));
				scrap.setScrap_num(rs.getInt("scrap_num"));
				scrap.setBoard_num(rs.getInt("board_num"));
				list.add(scrap);
			}
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//스크랩 삭제
	public int delete(int board_num,String user_id) {
		String SQL="DELETE FROM scrap WHERE board_num = ? AND user_id = ?";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, board_num);
			pstmt.setString(2, user_id);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
