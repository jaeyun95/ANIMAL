package animal.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDBBean {
	private Connection conn = null;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private static BoardDBBean instance = new BoardDBBean();

	public static BoardDBBean getinstance() {
		return instance;
	}

	private BoardDBBean() {
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


	//�ۼ��� �� ��ȣ ���ϱ�
	public int getNext_board() {
		String SQL="SELECT board_num FROM board ORDER BY board_num DESC";
		try {
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 1;//���簡 ù��° �Խñ��� ���
		}catch(Exception e) {
			e.printStackTrace();	
		}
		return -1;
	}

	//�ۼ��� �� ��ȣ ���ϱ�
	public int getNext_news() {
		String SQL="SELECT news_num FROM board ORDER BY board_num DESC";
		try {
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 1;//���簡 ù��° �Խñ��� ���
		}catch(Exception e) {
			e.printStackTrace();	
		}
		return -1;
	}

	//���� �ð��� ���ϴ� �޼ҵ�
	public String getDate() {
		String SQL="SELECT NOW()";
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
	//조회수
	public int count(int board_num,int board_hit) {
		String SQL="UPDATE board SET board_hit = ? WHERE board_num = ?";

		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, board_hit);
			pstmt.setInt(2, board_num);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//�����ǵ� �Խñ� ���
	public int news_write(BoardDataBean board) {
		String SQL = "INSERT INTO board (board_num,news_num,user_id,board_title,board_content,board_image,board_path,news_visible,board_date) "
				+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext_board());
			pstmt.setInt(2, getNext_news());
			pstmt.setString(3, board.getUser_id());
			pstmt.setString(4, board.getBoard_title());
			pstmt.setString(5, board.getBoard_content());
			pstmt.setString(6, board.getBoard_image());
			pstmt.setString(7, board.getBoard_path());
			pstmt.setInt(8, board.getNews_visible());
			pstmt.setString(9, getDate());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//�����ǵ� �Խñ� ����
	public ArrayList<BoardDataBean> news_getlist(String user_id){
		String SQL="SELECT * FROM board WHERE news_num > 0 AND user_id = ?";
		ArrayList<BoardDataBean> list = new ArrayList<BoardDataBean>();
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, user_id);
			rs=pstmt.executeQuery();

			while(rs.next()) {
				BoardDataBean board = new BoardDataBean();
				board.setBoard_num(rs.getInt("board_num"));
				board.setNews_num(rs.getInt("news_num"));
				board.setUser_id(rs.getString("user_id"));
				board.setBoard_title(rs.getString("board_title"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_image(rs.getString("board_image"));
				board.setBoard_path(rs.getString("board_path"));
				board.setBoard_date(rs.getString("board_date"));
				board.setBoard_like(rs.getInt("board_like"));
				board.setBoard_declaration(rs.getInt("board_declaration"));
				board.setNews_visible(rs.getInt("news_visible"));
				list.add(board);
			}
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//�����ǵ� �Խñ� �ҷ�����
	public BoardDataBean news_getboard(int board_num){
		String SQL="SELECT * FROM board WHERE board_num = ?";
		BoardDataBean board = new BoardDataBean();
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, board_num);
			rs=pstmt.executeQuery();

			while(rs.next()) {
				board.setBoard_num(rs.getInt("board_num"));
				board.setNews_num(rs.getInt("news_num"));
				board.setCate_num(rs.getInt("cate_num"));
				board.setUser_id(rs.getString("user_id"));
				board.setBoard_title(rs.getString("board_title"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_image(rs.getString("board_image"));
				board.setBoard_path(rs.getString("board_path"));
				board.setBoard_date(rs.getString("board_date"));
				board.setBoard_like(rs.getInt("board_like"));
				board.setBoard_scrap(rs.getInt("board_scrap"));
				board.setBoard_declaration(rs.getInt("board_declaration"));
				board.setNews_visible(rs.getInt("news_visible"));
				board.setBoard_hit(count(board.getBoard_num(),board.getBoard_hit()+1));
			}
			return board;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int news_update(BoardDataBean board) {
		String SQL="UPDATE board SET board_title = ?, board_content = ?, board_image = ?, board_path = ?, news_visible = ? WHERE board_num = ?";

		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, board.getBoard_title());
			pstmt.setString(2, board.getBoard_content());
			pstmt.setString(3, board.getBoard_image());
			pstmt.setString(4, board.getBoard_path());
			pstmt.setInt(5, board.getNews_visible());
			pstmt.setInt(6, board.getBoard_num());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//뉴스피드 글 삭제
	public int news_delete(int board_num) {
		String SQL="DELETE FROM board WHERE board_num = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, board_num);
			pstmt.executeUpdate();
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	//좋아요 수 가져오기
	public int like_board(int board_num,int board_like) {
		String SQL="UPDATE board SET board_like = ? WHERE board_num = ?";

		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, board_like);
			pstmt.setInt(2, board_num);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	//해당 게시판의 전체 게시글 list로 출력(8개씩)
	public ArrayList<BoardDataBean> getList(int cate_num,int pageNumber){
		String SQL="SELECT * FROM board WHERE board_num < ? AND cate_num = ? AND news_num IS NULL ORDER BY board_num DESC LIMIT 8";
		ArrayList<BoardDataBean> list = new ArrayList<BoardDataBean>();
		try {

			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext_board()-(pageNumber-1)*8);
			pstmt.setInt(2, cate_num);
			rs=pstmt.executeQuery();

			while(rs.next()) {
				BoardDataBean board = new BoardDataBean();
				board.setBoard_num(rs.getInt("board_num"));
				board.setCate_num(rs.getInt("cate_num"));
				board.setUser_id(rs.getString("user_id"));
				board.setBoard_title(rs.getString("board_title"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_image(rs.getString("board_image"));
				board.setBoard_path(rs.getString("board_path"));
				board.setBoard_date(rs.getString("board_date"));
				board.setBoard_like(rs.getInt("board_like"));
				board.setBoard_scrap(rs.getInt("board_scrap"));
				board.setBoard_hit(rs.getInt("board_hit"));
				list.add(board);
			}
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int update(BoardDataBean board) {
		String SQL="UPDATE board SET board_title = ?, board_content = ?, board_image = ?, board_path = ? WHERE board_num = ?";

		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, board.getBoard_title());
			pstmt.setString(2, board.getBoard_content());
			pstmt.setString(3, board.getBoard_image());
			pstmt.setString(4, board.getBoard_path());
			pstmt.setInt(5, board.getBoard_num());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 
	 * @param searchName
	 * @return 결과 list 반환
	 * @throws SQLException
	 * 이름으로 물품을 검색하는 메소드
	 */
	public ArrayList<BoardDataBean> searchByName(String searchName) throws SQLException{
		//conn = getConnection();  //connection 연결
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		ArrayList<BoardDataBean> searchNameProductList = new ArrayList<>();  //반환할 결과 리스트
		if(searchName.equals(""))
			return searchNameProductList;
		pstmt = conn.prepareStatement("select * from board where board_title like ?");  //해당 이름을 포함하는 물품 검색
		pstmt.setString(1, "%" + searchName + "%");
		rs = pstmt.executeQuery();
		while(rs.next()) {
			//각 dto 변수 저장
			BoardDataBean board = new BoardDataBean();
			board.setBoard_num(rs.getInt("board_num"));
			board.setNews_num(rs.getInt("news_num"));
			board.setCate_num(rs.getInt("cate_num"));
			board.setUser_id(rs.getString("user_id"));
			board.setBoard_title(rs.getString("board_title"));
			board.setBoard_content(rs.getString("board_content"));
			board.setBoard_image(rs.getString("board_image"));
			board.setBoard_path(rs.getString("board_path"));
			board.setBoard_date(rs.getString("board_date"));
			board.setBoard_like(rs.getInt("board_like"));
			board.setBoard_scrap(rs.getInt("board_scrap"));
			board.setBoard_declaration(rs.getInt("board_declaration"));
			board.setNews_visible(rs.getInt("news_visible"));
			searchNameProductList.add(board);  //리스트에 추가
		}
		//DbUtil.close(conn, pstmt, rs);  //연결 해지
		return searchNameProductList;
	}

	//현재 게시글의 총 개수를 세준다.
	public int allCount(int cate_num) {
		String SQL1 = "SELECT COUNT(*) FROM board WHERE news_num is null";
		String SQL2 = "SELECT COUNT(*) FROM board WHERE cate_num = ? AND news_num is null";
		try {
			if(cate_num == 0) {
				PreparedStatement pstmt=conn.prepareStatement(SQL1);
				rs=pstmt.executeQuery();
			}
			else {
				PreparedStatement pstmt=conn.prepareStatement(SQL2);
				pstmt.setInt(1, cate_num);
				rs=pstmt.executeQuery();
			}
			if(rs.next()) {
				return rs.getInt(1);
			}
			else 
				return 0; //없을 경우
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;//오류
	}

	//DB에 입력 글쓰기
	public int write(BoardDataBean board) {
		String SQL="INSERT INTO board (board_num,cate_num,user_id,board_title,board_content,board_image,board_path,board_date,board_like, board_hit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext_board());
			pstmt.setInt(2, board.getCate_num());
			pstmt.setString(3, board.getUser_id());
			pstmt.setString(4, board.getBoard_title());
			pstmt.setString(5, board.getBoard_content());
			pstmt.setString(6, board.getBoard_image());
			pstmt.setString(7, board.getBoard_path());
			pstmt.setString(8, getDate());
			pstmt.setInt(9, 0); //좋아요수
			pstmt.setInt(10, 0);//조회수
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}


	//scrap수를 늘려줌
	public int add_scrap(BoardDataBean board) {
		String SQL="UPDATE board SET board_scrap = ? WHERE board_num = ?";

		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, board.getBoard_scrap()+1);
			pstmt.setInt(2, board.getBoard_num());
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	//스크랩 리스트들을 가져옴
	public ArrayList<BoardDataBean> scrap_list(ArrayList<ScrapDataBean> scrap){
		ArrayList<BoardDataBean> list = new ArrayList<BoardDataBean>();
		String SQL = "SELECT * FROM board where board_num = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			for (int i = 0; i < scrap.size(); i++) {
				pstmt.setInt(1, scrap.get(i).getBoard_num());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					BoardDataBean board = new BoardDataBean();
					board.setBoard_num(rs.getInt("board_num"));
					board.setUser_id(rs.getString("user_id"));
					board.setBoard_title(rs.getString("board_title"));
					board.setBoard_content(rs.getString("board_content"));
					board.setBoard_image(rs.getString("board_image"));
					board.setBoard_path(rs.getString("board_path"));
					board.setBoard_date(rs.getString("board_date"));
					list.add(board);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	//하나의 게시글 정보를 얻어옴
	public BoardDataBean getBoard(int board_num) {
		String SQL="SELECT * FROM board WHERE board_num = ? and news_num is null";
		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			pstmt.setInt(1, board_num);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				BoardDataBean board = new BoardDataBean();
				board.setBoard_num(rs.getInt("board_num"));
				board.setCate_num(rs.getInt("cate_num"));
				board.setUser_id(rs.getString("user_id"));
				board.setBoard_title(rs.getString("board_title"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_image(rs.getString("board_image"));
				board.setBoard_path(rs.getString("board_path"));
				board.setBoard_date(rs.getString("board_date"));
				board.setBoard_like(rs.getInt("board_like"));
				board.setBoard_hit(rs.getInt("board_hit"));
				return board;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param searchName
	 * @return 결과 list 반환
	 * @throws SQLException
	 * 카테고리 내에서 이름으로 물품을 검색하는 메소드
	 */
	public ArrayList<BoardDataBean> searchByNameInCategory(String searchName, int categoryNum) throws SQLException{
		//conn = getConnection();  //connection 연결
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		ArrayList<BoardDataBean> searchNameProductList = new ArrayList<>();  //반환할 결과 리스트
		if(searchName.equals(""))
			return searchNameProductList;
		pstmt = conn.prepareStatement("select * from board where board_title like ? and cate_num = ? and news_num is NULL");  //해당 이름을 포함하는 물품 검색
		pstmt.setString(1, "%" + searchName + "%");
		pstmt.setInt(2, categoryNum);
		rs = pstmt.executeQuery();
		while(rs.next()) {
			//각 dto 변수 저장
			BoardDataBean board = new BoardDataBean();
			board.setBoard_num(rs.getInt("board_num"));
			board.setNews_num(rs.getInt("news_num"));
			board.setCate_num(rs.getInt("cate_num"));
			board.setUser_id(rs.getString("user_id"));
			board.setBoard_title(rs.getString("board_title"));
			board.setBoard_content(rs.getString("board_content"));
			board.setBoard_image(rs.getString("board_image"));
			board.setBoard_path(rs.getString("board_path"));
			board.setBoard_date(rs.getString("board_date"));
			board.setBoard_like(rs.getInt("board_like"));
			board.setBoard_scrap(rs.getInt("board_scrap"));
			board.setBoard_declaration(rs.getInt("board_declaration"));
			board.setNews_visible(rs.getInt("news_visible"));
			searchNameProductList.add(board);  //리스트에 추가
		}
		//DbUtil.close(conn, pstmt, rs);  //연결 해지
		return searchNameProductList;
	}

}
