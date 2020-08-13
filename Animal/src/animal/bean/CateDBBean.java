package animal.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CateDBBean {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private static CateDBBean instance = new CateDBBean();

	public static CateDBBean getinstance() {
		return instance;
	}

	private CateDBBean() {
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

	public String getDate() {
		String SQL="SELECT NOW()";//현재 시간을 돌려준다.
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next())
				return rs.getString(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	//추가될 카테고리 번호
	public int getNext() {
		String SQL="SELECT cate_num FROM cate ORDER BY cate_num DESC";
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

	//대 카테고리 추가하는부분 ->한솔오빠가 쓰는부분
	public int add_cate(String cate_name) {
		String SQL="INSERT INTO cate VALUES (?, ?, ?)";
		try {
			//여기에 추가해야함
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, cate_name);
			pstmt.setString(3, getDate());
			//만약 대 카테고리가 아니라면 이부분 을 수정해줘야함!
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//현재 카테고리번호로 정보를 불러옴
	public CateDataBean getBoard(int cate_num) {
		String SQL="SELECT * FROM cate WHERE cate_num = ?";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, cate_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				CateDataBean catedatabean = new CateDataBean();
				catedatabean.setCate_num(rs.getInt(1));
				catedatabean.setCate_name(rs.getString(2));
				catedatabean.setCate_date(rs.getString(3));
				return catedatabean;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//카테고리 리스트를 출력
	public ArrayList<CateDataBean> getList(){
		String SQL="SELECT * FROM cate ORDER BY cate_num DESC";
		ArrayList<CateDataBean> list = new ArrayList<CateDataBean>();
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				CateDataBean catedatabean = new CateDataBean();
				catedatabean.setCate_num(rs.getInt(1));
				catedatabean.setCate_name(rs.getString(2));
				catedatabean.setCate_date(rs.getString(3));
				list.add(catedatabean);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public boolean deleteCategory(int cate_num) {
		String sql = "delete from cate where cate_num=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cate_num);
			pstmt.execute();
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	public CateDataBean getCate(int cate_num) {
		String sql = "select * from cate where cate_num = ?";
		CateDataBean catedatabean = new CateDataBean();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cate_num);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				catedatabean.setCate_num(rs.getInt(1));
				catedatabean.setCate_name(rs.getString(2));
				catedatabean.setCate_date(rs.getString(3));
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs !=null) rs.close();
				if(pstmt !=null) pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return catedatabean;
	}
}