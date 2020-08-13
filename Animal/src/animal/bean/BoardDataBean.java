package animal.bean;

public class BoardDataBean {
	private int board_num;
	private int news_num; //�����ǵ忡�� �ۼ��� ��� 1
	private int cate_num; //�����ǵ忡�� �ۼ��� ��� null 
	private String user_id;
	private String board_title;
	private String board_content;
	private String board_image;
	private String board_path;
	private String board_date;
	private int board_like;
	private int board_scrap;
	private int board_declaration;
	private int news_visible; //�����ǵ忡�� ��������� ���� 0�϶� ��κ��� 1�϶� ��������
	private int board_hit;
	
	
	public String getBoard_date() {
		return board_date;
	}
	public void setBoard_date(String board_date) {
		this.board_date = board_date;
	}
	public int getNews_visible() {
		return news_visible;
	}
	public void setNews_visible(int news_visible) {
		this.news_visible = news_visible;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public int getNews_num() {
		return news_num;
	}
	public void setNews_num(int news_num) {
		this.news_num = news_num;
	}
	public int getCate_num() {
		return cate_num;
	}
	public void setCate_num(int cate_num) {
		this.cate_num = cate_num;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public String getBoard_image() {
		return board_image;
	}
	public void setBoard_image(String board_image) {
		this.board_image = board_image;
	}
	public String getBoard_path() {
		return board_path;
	}
	public void setBoard_path(String board_path) {
		this.board_path = board_path;
	}
	public int getBoard_like() {
		return board_like;
	}
	public void setBoard_like(int board_like) {
		this.board_like = board_like;
	}
	public int getBoard_scrap() {
		return board_scrap;
	}
	public void setBoard_scrap(int board_scrap) {
		this.board_scrap = board_scrap;
	}
	public int getBoard_declaration() {
		return board_declaration;
	}
	public void setBoard_declaration(int board_declaration) {
		this.board_declaration = board_declaration;
	}
	public int getBoard_hit() {
		return board_hit;
	}
	public void setBoard_hit(int board_hit) {
		this.board_hit = board_hit;
	}
}
