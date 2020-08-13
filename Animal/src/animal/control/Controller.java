package animal.control;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import animal.bean.BoardDBBean;
import animal.bean.BoardDataBean;
import animal.bean.CateDBBean;
import animal.bean.CateDataBean;
import animal.bean.CommentDBBean;
import animal.bean.CommentDataBean;
import animal.bean.DeclarationDBBean;
import animal.bean.DeclarationDataBean;
import animal.bean.LikeDBBean;
import animal.bean.LikeDataBean;
import animal.bean.ScrapDBBean;
import animal.bean.ScrapDataBean;
import animal.bean.UserDBBean;
import animal.bean.UserDataBean;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String board_path = "C:\\Users\\이재윤\\Desktop\\web\\upload";
	private static String enType = "utf-8";
	private static int maxSize = 1024 * 1024 * 1024; 

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		BoardDBBean board = BoardDBBean.getinstance();
		UserDBBean user = UserDBBean.getinstance();
		LikeDBBean like = LikeDBBean.getinstance();
		DeclarationDBBean declaration = DeclarationDBBean.getinstance();
		ScrapDBBean scrap = ScrapDBBean.getinstance();
		CateDBBean cate = CateDBBean.getinstance();
		CommentDBBean comment = CommentDBBean.getinstance();

		//action이 null이 아닐 경우에만 수행
		if(action != null) {
			if(action.equals("main")) {
				address = "index.jsp";
			}

			//로그인폼을 매칭시켜주는 부분
			if(action.equals("login")) {
				address = "login.jsp";
			}

			//회원가입폼을 매칭시켜주는 부분
			else if(action.equals("register")) {
				address = "register.jsp";
			}

			//회원가입을 완료시켜주는 부분
			else if(action.equals("register_comp")) {
				request.setCharacterEncoding("utf-8");
				UserDataBean userdt = new UserDataBean(); 

				//회원가입 정보
				userdt.setUser_name(request.getParameter("user_name"));
				userdt.setUser_phone(request.getParameter("user_phone"));
				userdt.setUser_id(request.getParameter("user_id"));
				userdt.setUser_available(1);
				String user_pw = request.getParameter("user_pw");
				String check_passwd = request.getParameter("check_passwd");

				//폼이 비어있을경우
				if(userdt.getUser_name() == null || userdt.getUser_name().equals("") || userdt.getUser_phone() == null || userdt.getUser_phone().equals("") || userdt.getUser_id() == null || userdt.getUser_id().equals("") || user_pw == null || user_pw.equals("") || check_passwd == null || check_passwd.equals("")) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
					address = "register.jsp";
				}

				//비밀번호가 일치하지 않을 경우
				if(!user_pw.equals(check_passwd)) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "비밀번호가 일치하지 않습니다.");
					address = "register.jsp";
				}

				userdt.setUser_pw(SHA1.sha1(user_pw));
				int result = user.register(userdt);

				if(result ==1) {
					request.getSession().setAttribute("messageType", "성공 메시지");
					request.getSession().setAttribute("messageContent", "회원가입이 완료되었습니다. 로그인 해주세요!");
					address = "login.jsp";
				}

				else {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
					address = "register.jsp";
				}
			}

			//로그아웃 시켜주는 부분
			else if(action.equals("logout")) {
				session.invalidate();
				address = "index.jsp";
			}

			//로그인을 처리해주는 부분
			else if(action.equals("login_comp")) {
				String user_id = request.getParameter("user_id");
				String user_pw = request.getParameter("user_pw");
				String sha1_pw = SHA1.sha1(user_pw); //암호화

				if(user_id == null || user_id.equals("") || user_pw == null || user_pw.equals("")) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
					address = "login.jsp";
				}

				else {
					int result = user.login(user_id, sha1_pw);

					if(result == 1) {
						request.getSession().setAttribute("user_id", user_id);
						request.getSession().setAttribute("my_available", user.getUser(user_id).getUser_available());
						address = "index.jsp";
					}

					else if(result == 0) {
						request.getSession().setAttribute("messageType", "오류 메세지");
						request.getSession().setAttribute("messageContent", "아이디 혹은 비밀번호가 맞지 않습니다.");
						address = "login.jsp";
					}

					else {
						request.getSession().setAttribute("messageType", "오류 메시지");
						request.getSession().setAttribute("messageContent", "내부적인 오류입니다. 다시 시도해 주세요.");
						address = "login.jsp";
					}
				}
			}

			//글쓰기폼을 매칭시켜주는 부분
			else if (action.equals("write")) {
				address = "write.jsp";
			}

			//비밀번호 확인을 매칭시켜주는 부분
			else if (action.equals("confirm")) {
				request.setAttribute("action", request.getParameter("goal"));
				address = "Confirmpassword.jsp";
			}
			else if(action.equals("confirmAction")) {
				request.setCharacterEncoding("utf-8");		
				String user_id = request.getParameter("user_id");
				//주어진 id의 중복여부를 체크해 값을 반환.
				int check= user.registerCheck(user_id);


				/*response.getWriter().write(user.registerCheck(user_id)+"");*/
			}

			//아이디찾기폼을 매칭시켜주는 부분
			else if (action.equals("forgetID")) {
				address = "forgetID.jsp";
			}
			else if(action.equals("showID")) {
				request.setCharacterEncoding("utf-8");
				String user_name = request.getParameter("user_name");
				String user_phone = request.getParameter("user_phone");
				String user_id = user.searchId(user_name, user_phone);
				request.setAttribute("user_name", user_name);
				request.setAttribute("user_id", user_id);
				address = "showID.jsp";
			}

			//비밀번호찾기 폼을 매칭시켜주는부분
			else if (action.equals("forgetPW")) {
				address = "forgetPW.jsp";
			}
			//잃어버린 비밀번호 수정
			else if(action.equals("update_pw")) {
				request.setCharacterEncoding("utf-8");
				String user_name = request.getParameter("user_name");
				String user_id = request.getParameter("user_id");
				String user_pw = SHA1.sha1(request.getParameter("user_pw"));
				int result = user.update_PW(user_name, user_id, user_pw);
				if(result == -1) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "내부적인 오류입니다. 다시 시도해 주세요.");
					address = "forgetPW.jsp";
				}
				else {
					request.setAttribute("user_id", user_id);
					address = "showPW.jsp";
				}
			}

			//뉴스피드 글쓰기폼을 매칭시켜주는 부분
			else if(action.equals("news_write")) {
				address = "news_write.jsp";
			}
			else if(action.equals("news_Write")) {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				BoardDataBean boarddt = new BoardDataBean();
				Enumeration oldFileNames = null;
				File oldFile = null;
				File newFile = null;
				String board_image = "";
				String newFileName = "";
				int count = 1;
				//<시작>업로드 된 파일 저장---------------------------------------------------------------------------------------------------------------
				MultipartRequest multipartrequest = new MultipartRequest(request, board_path, maxSize, enType ,new DefaultFileRenamePolicy());

				//파라미터값 받아오기
				boarddt.setBoard_title(multipartrequest.getParameter("board_title"));
				boarddt.setUser_id((String)request.getSession().getAttribute("user_id"));
				boarddt.setBoard_content(multipartrequest.getParameter("board_content"));
				boarddt.setNews_visible(Integer.parseInt(multipartrequest.getParameter("news_visible")));

				//저장할 이름 생성
				String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
				newFileName = board.getNext_board() + boarddt.getUser_id() + now+".jpg";
				oldFileNames = multipartrequest.getFileNames();

				//입력받은 사진들의 이름을 모두 수정
				while(oldFileNames.hasMoreElements()) {
					String parameter = (String)oldFileNames.nextElement();
					if(multipartrequest.getOriginalFileName(parameter) == null)
						continue;
					oldFile = new File(board_path + "/" + multipartrequest.getOriginalFileName(parameter));
					newFile = new File(board_path + "/" +count+ newFileName);
					oldFile.renameTo(newFile);
					board_image += count +newFileName +  "/";
					count++;
				}
				boarddt.setBoard_image(board_image);
				boarddt.setBoard_path(board_path);

				//<끝>업로드 된 파일 저장---------------------------------------------------------------------------------------------------------------

				if(boarddt.getBoard_title() == null || boarddt.getBoard_title().equals("") || boarddt.getBoard_content() == null || boarddt.getBoard_content().equals("")) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
					response.sendRedirect("news_write.jsp");
					return;
				}

				else {
					int result = board.news_write(boarddt);

					if(result == -1) {
						request.getSession().setAttribute("messageType", "오류 메시지");
						request.getSession().setAttribute("messageContent", "글쓰기에 실패했습니다.");
						address = "news_write.jsp";
					}

					else {
						request.setAttribute("click_id", request.getSession().getAttribute("user_id"));
						address = "mypage.jsp";
					}
				}
			}

			//타임라인 폼을 매칭시켜주는 부분
			else if(action.equals("mypage")) {
				request.setAttribute("click_id", request.getParameter("click_id"));
				address = "mypage.jsp";
			}

			//타임라인 글 수정 폼을 매칭시켜주는 부분
			else if(action.equals("news_update")) {
				request.setAttribute("board_num",request.getParameter("board_num"));
				address = "news_update.jsp";
			}

			else if(action.equals("news_Update")) {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				BoardDataBean boarddt = new BoardDataBean();
				Enumeration oldFileNames = null;
				File oldFile = null;
				File newFile = null;
				String board_image = "";
				String newFileName = "";
				int count = 1;
				//<시작>업로드 된 파일 저장---------------------------------------------------------------------------------------------------------------
				MultipartRequest multipartrequest = new MultipartRequest(request, board_path, maxSize, enType ,new DefaultFileRenamePolicy());

				//파라미터값 받아오기
				boarddt.setBoard_num(Integer.parseInt(multipartrequest.getParameter("board_num")));
				boarddt.setBoard_title(multipartrequest.getParameter("board_title"));
				boarddt.setUser_id((String)request.getSession().getAttribute("user_id"));
				boarddt.setBoard_content(multipartrequest.getParameter("board_content"));
				boarddt.setBoard_image(board.news_getboard(boarddt.getBoard_num()).getBoard_image()); //기존의 image파일들
				boarddt.setBoard_path(board_path);
				boarddt.setNews_visible(Integer.parseInt(multipartrequest.getParameter("news_visible")));
				String[] old = boarddt.getBoard_image().split("/"); //기존 파일들의 불러옴
				String[] select_image;
				ArrayList<String> old_image = new ArrayList<String>();

				//arrayList에 현재 이미지들을 넣어줌
				for(int i = 0; i < old.length; i++) {
					old_image.add(old[i]);
				}

				//선택된 파일이 있으면 저장후 선택된 파일 삭제
				if(multipartrequest.getParameterValues("oldfile") != null)
				{
					select_image=multipartrequest.getParameterValues("oldfile");
					for(int i = select_image.length - 1; 0 <= i; i--) {
						if(Integer.parseInt(select_image[i]) == i) {
							old_image.remove(i);
						}
					}
				}

				//파일 이름 중복을 피하기 위해 현재 시간 생성&저장할 이름 생성
				String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
				newFileName = boarddt.getBoard_num() +""+ boarddt.getUser_id()+now+".jpg";
				oldFileNames = multipartrequest.getFileNames();

				if(oldFileNames != null) {
					//새로 업로드한 파일이 있는 경우 5개 까지 저장.
					while(oldFileNames.hasMoreElements()) {

						//입력받은 사진들의 이름을 모두 수정
						String parameter = (String)oldFileNames.nextElement();
						if(multipartrequest.getOriginalFileName(parameter) == null)
							continue;
						oldFile = new File(board_path + "/" + multipartrequest.getOriginalFileName(parameter));
						newFile = new File(board_path + "/"+count + newFileName);
						board_image += count +newFileName + "/";
						oldFile.renameTo(newFile);
						count++;

						old_image.add(board_image);
						if(old_image.size() > 3)
							break;
					}
					board_image = "";
					for(int i = 0; i < old_image.size(); i++) {
						board_image += old_image.get(i) + "/";
					}
					boarddt.setBoard_image(board_image);
				}

				else {
					//새로 받은 이미지 파일이 없을 경우에 현재 남은 image를 다시 저장시킴	
					if(old_image.isEmpty()) {
						request.getSession().setAttribute("messageType", "오류 메시지");
						request.getSession().setAttribute("messageContent", "이미지는 1개이상 남아있어야 합니다.");
						response.sendRedirect("update.jsp");
					}

					for(int i = 0; i < old_image.size(); i++) {
						board_image += old_image.get(i) + "/";
					}

					boarddt.setBoard_image(board_image);
				}
				//<끝>업로드 된 파일 저장---------------------------------------------------------------------------------------------------------------

				if(boarddt.getBoard_title() == null || boarddt.getBoard_title().equals("") || boarddt.getBoard_content() == null || boarddt.getBoard_content().equals("") ) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
					address = "news_update.jsp";
				}

				int result = board.news_update(boarddt);

				if(result == -1) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "글 수정이 실패했습니다.");
					address = "news_update.jsp";
				}
				else {
					request.setAttribute("click_id", request.getSession().getAttribute("user_id"));
					address = "mypage.jsp";
				}
			}

			//타임라인 글 삭제
			else if(action.equals("news_delete")) {
				board.news_delete(Integer.parseInt(request.getParameter("board_num")));
				request.setAttribute("click_id", request.getSession().getAttribute("user_id"));
				address = "mypage.jsp";
			}

			//신고 폼으로 매칭시켜주는 부분
			else if(action.equals("declaration")) {
				request.setAttribute("board_num", request.getParameter("board_num"));
				request.setAttribute("click_id", request.getSession().getAttribute("user_id"));
				request.setAttribute("news_num", request.getParameter("news_num"));
				address = "declaration.jsp";
			}

			//스크랩함 폼을 매칭시켜주는 부분
			else if(action.equals("scrap")){
				request.setAttribute("user_id", request.getSession().getAttribute("user_id"));
				address = "scrap.jsp";
			}

			//게시글에서 스크랩을 눌렀을 경우 스크랩 목록에 추가를 한뒤 view화면으로 돌아감
			else if(action.equals("add_scrap")) {
				ScrapDataBean scrapdt = new ScrapDataBean();
				scrapdt.setUser_id((String)request.getSession().getAttribute("user_id"));
				scrapdt.setBoard_num(Integer.parseInt(request.getParameter("board_num")));
				scrap.add_scrap(scrapdt);
				request.setAttribute("board_num", request.getParameter("board_num"));
				address = "view.jsp";
			}

			//뷰 폼으로 넘어가는 부분
			else if(action.equals("view")) {
				int board_num = Integer.parseInt(request.getParameter("board_num"));
				board.count(board_num, board.getBoard(board_num).getBoard_hit() + 1);
				request.setAttribute("board_num", request.getParameter("board_num"));
				address = "view.jsp";
			}

			//스크랩된 글 삭제하는 부분
			else if(action.equals("delete_scrap")){
				int board_num = Integer.parseInt(request.getParameter("board_num"));
				scrap.delete(board_num,(String)request.getSession().getAttribute("user_id"));
				request.setAttribute("click_id", request.getSession().getAttribute("user_id"));
				address = "scrap.jsp";
			}

			//신고 DB에 저장시켜주는 부분
			else if(action.equals("declaration_comp")){
				DeclarationDataBean declarationdt = new DeclarationDataBean();
				declarationdt.setBoard_num(Integer.parseInt(request.getParameter("board_num")));
				declarationdt.setUser_id((String)request.getSession().getAttribute("user_id"));
				declarationdt.setDeclaration_content(request.getParameter("declaration_content"));

				if(declaration.check_id(declarationdt) == 1) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "이미 신고 하셨습니다.");
					request.setAttribute("board_num", declarationdt.getBoard_num());
					address = "index.jsp";
				}

				else {
					declaration.declaration(declarationdt);
					request.getSession().setAttribute("messageType", "확인 메시지");
					request.getSession().setAttribute("messageContent", "신고 완료했습니다.");
					if(request.getParameter("news_num") == null) {
						request.setAttribute("board_num", declarationdt.getBoard_num());
						address = "index.jsp"; //board만들어지면 경로 수정
					}
					else {
						request.setAttribute("click_id", request.getSession().getAttribute("user_id"));
						address = "mypage.jsp";
					}
				}
			}

			//회원정보 수정 폼으로 매칭
			else if(action.equals("update_user")) {
				UserDataBean userdt = user.getUser((String)request.getSession().getAttribute("user_id"));
				String confirm_pw = SHA1.sha1(request.getParameter("confirm_pw"));
				if(confirm_pw.equals(userdt.getUser_pw())) {
					request.setAttribute("user_name", userdt.getUser_name());
					request.setAttribute("user_phone", userdt.getUser_phone());
					address = "user_update.jsp";
				}

				else {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "비밀번호가 틀렸습니다.");
					address = "Confirmpassword.jsp";
				}
			}

			//페이징을 처리해주는 부분
			else if(action.equals("paging")) {
				request.setAttribute("pageNumber", request.getParameter("pageNumber"));
				request.setAttribute("cate_num", request.getParameter("cate_num"));
				address = "board.jsp";
			}

			//회원정보 수정해주는 부분
			else if(action.equals("user_update_comp")) {
				UserDataBean userdt = new UserDataBean();
				userdt.setUser_phone(request.getParameter("user_phone"));
				userdt.setUser_id((String)request.getSession().getAttribute("user_id"));
				String user_pw = request.getParameter("user_pw");
				String check_passwd = request.getParameter("check_passwd");

				if(!user_pw.equals(check_passwd) || user_pw == null || user_pw.equals("") || userdt.getUser_phone() == null || userdt.getUser_phone().equals("") || check_passwd == null || check_passwd.equals("")) {
					request.setAttribute("user_name", userdt.getUser_name());
					request.setAttribute("user_phone", userdt.getUser_phone());
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "다시 확인해 주세요.");
					address = "user_update.jsp";
				}

				userdt.setUser_pw(SHA1.sha1(user_pw));
				user.update_user(userdt);
				request.setAttribute("click_id", userdt.getUser_id());
				address = "mypage.jsp";
			}

			//탈퇴 버튼 누를 경우
			else if(action.equals("delete_user")) {
				String confirm_pw = SHA1.sha1(request.getParameter("confirm_pw"));
				UserDataBean userdt = user.getUser((String)request.getSession().getAttribute("user_id"));

				if(!userdt.getUser_pw().equals(confirm_pw)) {
					request.setAttribute("action", "delete_user");
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "비밀번호가 틀렸습니다.");
					address = "Confirmpassword.jsp";
				}

				else{
					user.deleteUser(userdt.getUser_id());
					session.invalidate();
					address = "index.jsp";
				}
			}

			//뉴스피드가기 누름
			else if(action.equals("news")) {
				request.setAttribute("click_id", request.getSession().getAttribute("user_id"));
				address = "mypage.jsp";
			}

			//좋아요 버튼 누른 경우
			else if(action.equals("like")) {
				LikeDataBean likedt = new LikeDataBean();
				String from = request.getParameter("from");
				likedt.setUser_id((String)request.getSession().getAttribute("user_id"));
				likedt.setBoard_num(Integer.parseInt(request.getParameter("board_num"))); 
				if(from.equals("mypage")) {
					if(like.check_id(likedt) == 1){
						like.add(likedt);
						board.like_board(likedt.getBoard_num(), board.news_getboard(likedt.getBoard_num()).getBoard_like()+1);
					}

					else {
						like.delete(likedt);
						board.like_board(likedt.getBoard_num(), board.news_getboard(likedt.getBoard_num()).getBoard_like()-1);
					}

					request.setAttribute("click_id", board.news_getboard(likedt.getBoard_num()).getUser_id());
					address = "mypage.jsp";
				}

				if(from.equals("Boardlike")) {
					if(like.check_id(likedt) == 1) {
						like.add(likedt);
						board.like_board(likedt.getBoard_num(), board.getBoard(likedt.getBoard_num()).getBoard_like()+1);
					}

					else{
						like.delete(likedt);
						board.like_board(likedt.getBoard_num(), board.getBoard(likedt.getBoard_num()).getBoard_like()-1);
					}
					request.setAttribute("board_num",likedt.getBoard_num());
					request.setAttribute("click_id", board.getBoard(likedt.getBoard_num()).getUser_id());
					address = "view.jsp";
				}
			}

			//게시판을 누를 경우
			else if(action.equals("boardAction")) {
				CateDataBean catedt = cate.getBoard(Integer.parseInt(request.getParameter("cate_num")));//카테고리의 정보 (이름, 번호)
				request.setAttribute("cate_num", request.getParameter("cate_num"));
				request.setAttribute("cate_name", catedt.getCate_name());
				address = "board.jsp";
			}

			//게시판 글 쓰는 경우
			else if(action.equals("writeAction")) {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				BoardDataBean boarddt = new BoardDataBean();
				Enumeration oldFileNames = null;
				File oldFile = null;
				File newFile = null;
				String board_image = "";
				String newFileName = "";
				int count = 1;
				//<시작>업로드 된 파일 저장---------------------------------------------------------------------------------------------------------------
				MultipartRequest multipartrequest = new MultipartRequest(request, board_path, maxSize, enType ,new DefaultFileRenamePolicy());

				//파라미터값 받아오기
				boarddt.setBoard_title(multipartrequest.getParameter("board_title"));
				boarddt.setCate_num(Integer.parseInt(multipartrequest.getParameter("cate_num")));
				boarddt.setUser_id((String)request.getSession().getAttribute("user_id"));
				boarddt.setBoard_content(multipartrequest.getParameter("board_content"));

				//저장할 이름 생성
				newFileName = boarddt.getCate_num() +""+ board.getNext_board() +""+ boarddt.getUser_id()+".jpg";
				oldFileNames = multipartrequest.getFileNames();

				//입력받은 사진들의 이름을 모두 수정
				while(oldFileNames.hasMoreElements()) {
					String parameter = (String)oldFileNames.nextElement();
					if(multipartrequest.getOriginalFileName(parameter) == null)
						continue;
					oldFile = new File(board_path + "/" + multipartrequest.getOriginalFileName(parameter));
					newFile = new File(board_path + "/" +count+ newFileName);
					oldFile.renameTo(newFile);
					board_image += count +newFileName + "/";
					count++;
				}
				if(count != 1) {
					boarddt.setBoard_image(board_image);
					boarddt.setBoard_path(board_path);
				}
				//<끝>업로드 된 파일 저장---------------------------------------------------------------------------------------------------------------
				if(boarddt.getBoard_title() == null || boarddt.getBoard_title().equals("") || boarddt.getBoard_content() == null || boarddt.getBoard_content().equals("")) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
					address="write.jsp";
				}

				else {
					int result = board.write(boarddt);

					if(result == -1) {
						request.getSession().setAttribute("messageType", "오류 메시지");
						request.getSession().setAttribute("messageContent", "글쓰기에 실패했습니다.");
						address="write.jsp";
					}

					else {
						request.setAttribute("cate_num", multipartrequest.getParameter("cate_num"));
						address="board.jsp";
					}
				}
			}

			//글 수정 폼을 연결해 주는 부분
			else if(action.equals("UpdateAction")) {
				request.setAttribute("board_num", request.getParameter("board_num"));
				address = "update.jsp";
			}

			//글 수정 완료 부분
			else if(action.equals("update_comp")){
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				BoardDataBean boarddt = new BoardDataBean();
				Enumeration oldFileNames = null;
				File oldFile = null;
				File newFile = null;
				String board_image = "";
				String newFileName = "";
				int count = 1;
				//<시작>업로드 된 파일 저장---------------------------------------------------------------------------------------------------------------
				MultipartRequest multipartrequest = new MultipartRequest(request, board_path, maxSize, enType ,new DefaultFileRenamePolicy());

				//파라미터값 받아오기
				boarddt.setCate_num(Integer.parseInt(multipartrequest.getParameter("cate_num")));
				boarddt.setBoard_num(Integer.parseInt(multipartrequest.getParameter("board_num")));
				boarddt.setBoard_title(multipartrequest.getParameter("board_title"));
				boarddt.setUser_id((String)request.getSession().getAttribute("user_id"));
				boarddt.setBoard_content(multipartrequest.getParameter("board_content"));

				if(multipartrequest.getFileNames()!=null) {
					if(board.getBoard(boarddt.getBoard_num()).getBoard_image() != null) {
						boarddt.setBoard_image(board.getBoard(boarddt.getBoard_num()).getBoard_image()); //기존의 image파일들
						boarddt.setBoard_path(board_path);
						String[] old = boarddt.getBoard_image().split("/"); //기존 파일들의 불러옴
						String[] select_image;
						ArrayList<String> old_image = new ArrayList<String>();

						//arrayList에 현재 이미지들을 넣어줌
						for(int i = 0; i < old.length; i++) {
							old_image.add(old[i]);
						}

						//선택된 파일이 있으면 저장후 선택된 파일 삭제
						if(multipartrequest.getParameterValues("oldfile") != null)
						{
							select_image=multipartrequest.getParameterValues("oldfile");
							for(int i = select_image.length - 1; 0 <= i; i--) {
								if(Integer.parseInt(select_image[i]) == i) {
									old_image.remove(i);
								}
							}
						}

						//파일 이름 중복을 피하기 위해 현재 시간 생성&저장할 이름 생성
						String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
						newFileName = boarddt.getCate_num() +""+ boarddt.getBoard_num() +""+ boarddt.getUser_id()+now+".jpg";
						oldFileNames = multipartrequest.getFileNames();

						if(oldFileNames != null) {
							//새로 업로드한 파일이 있는 경우 5개 까지 저장.
							while(oldFileNames.hasMoreElements()) {

								//입력받은 사진들의 이름을 모두 수정
								String parameter = (String)oldFileNames.nextElement();
								if(multipartrequest.getOriginalFileName(parameter) == null)
									continue;
								oldFile = new File(board_path + "/" + multipartrequest.getOriginalFileName(parameter));
								newFile = new File(board_path + "/"+count + newFileName);
								board_image +=count +newFileName + "/" ;
								oldFile.renameTo(newFile);
								count++;

								old_image.add(board_image);
								if(old_image.size() > 5)
									break;
							}
							board_image = "";
							for(int i = 0; i < old_image.size(); i++) {
								board_image += old_image.get(i) + "/";
							}
							boarddt.setBoard_image(board_image);
						}

						else {
							//새로 받은 이미지 파일이 없을 경우에 현재 남은 image를 다시 저장시킴	
							if(old_image.isEmpty()) {
								request.getSession().setAttribute("messageType", "오류 메시지");
								request.getSession().setAttribute("messageContent", "이미지는 1개이상 남아있어야 합니다.");
								response.sendRedirect("update.jsp");
							}

							for(int i = 0; i < old_image.size(); i++) {
								board_image += old_image.get(i) + "/";
							}

							boarddt.setBoard_image(board_image);
						}}

					else {
						//저장할 이름 생성
						newFileName = boarddt.getCate_num() +""+ board.getNext_board() +""+ boarddt.getUser_id()+".jpg";
						oldFileNames = multipartrequest.getFileNames();

						//입력받은 사진들의 이름을 모두 수정
						while(oldFileNames.hasMoreElements()) {
							String parameter = (String)oldFileNames.nextElement();
							if(multipartrequest.getOriginalFileName(parameter) == null)
								continue;
							oldFile = new File(board_path + "/" + multipartrequest.getOriginalFileName(parameter));
							newFile = new File(board_path + "/" +count+ newFileName);
							oldFile.renameTo(newFile);
							board_image += newFileName + count + "/";
							count++;
						}

						if(count != 1) {
							boarddt.setBoard_image(board_image);
							boarddt.setBoard_path(board_path);
						}
					}}


				//<끝>업로드 된 파일 저장---------------------------------------------------------------------------------------------------------------

				if(boarddt.getBoard_title() == null || boarddt.getBoard_title().equals("") || boarddt.getBoard_content() == null || boarddt.getBoard_content().equals("") ) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
					request.setAttribute("board_num", multipartrequest.getParameter("board_num"));
					address = "update.jsp";
				}

				int result = board.update(boarddt);

				if(result == -1) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "글 수정이 실패했습니다.");
					request.setAttribute("board_num", multipartrequest.getParameter("board_num"));
					address = "update.jsp";
				}
				else {
					request.setAttribute("board_num", multipartrequest.getParameter("board_num"));
					address = "view.jsp";
				}
			}
			//게시글 삭제
			else if(action.equals("boardDelete")) {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				int cate_num = Integer.parseInt(request.getParameter("cate_num"));
				int board_num = Integer.parseInt(request.getParameter("board_num"));

				int result = board.news_delete(board_num);

				if(result == -1) {
					request.getSession().setAttribute("messageType", "오류 메세지");
					request.getSession().setAttribute("messageContent", "글 삭제를 실패했습니다.");
					address = "view.jsp";
				}

				else {
					request.setAttribute("cate_num", Integer.toString(cate_num));
					address = "board.jsp";
				}
			}
			else if(action.equals("searchAction")) {
				String searchName = null;  //검색하려는 키워드
				ArrayList<BoardDataBean> list = null;  //검색 결과를 가져올 list
				try {  //해당 parameter가 없을 경우
					searchName = request.getParameter("searchKeyword");
				} catch(NullPointerException e) {  //키워드의 내용이 없을 경우
					e.printStackTrace();
				}
				try {
					list = BoardDBBean.getinstance().searchByName(searchName);
					request.setAttribute("searchResultList", list);  //검색 결과 리스트 attribute에 저장
					request.setAttribute("cate", "전체");
					address="searchBoard.jsp";
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			else if(action.equals("catecorySearchAction")) {
				String searchName = null;  //검색하려는 키워드
				int category = -1;  //init
				ArrayList<BoardDataBean> list = null;  //검색 결과를 가져올 list
				try {  //해당 parameter가 없을 경우
					searchName = request.getParameter("searchKeyword");
					category = Integer.parseInt(request.getParameter("categoryNum"));
				} catch(NullPointerException e) {  //키워드의 내용이 없을 경우
					e.printStackTrace();
				}
				try {
					list = BoardDBBean.getinstance().searchByNameInCategory(searchName, category);
					request.setAttribute("searchResultList", list);  //검색 결과 리스트 attribute에 저장
					request.setAttribute("cate_num", category);
					request.setAttribute("cate", CateDBBean.getinstance().getCate(category).getCate_name());
					address="searchBoard.jsp";
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			else if(action.equals("MngrUserAction")) {
				String mngrAction = request.getParameter("mngrAction");

				if(mngrAction != null) {
					if(mngrAction.equals("banUser")) {
						UserDBBean.getinstance().banUser(request.getParameter("user_id"));
					}
					else if(mngrAction.equals("appointStaff")){
						UserDBBean.getinstance().appointStaff(request.getParameter("user_id"));
					}
				}

				ArrayList<UserDataBean> userList = null;
				userList = UserDBBean.getinstance().getAllUser();
				request.setAttribute("userList", userList);
				request.setAttribute("count", new Integer(userList.size()));

				address="/mngr/member/fullMemberManage.jsp";
			}
			else if(action.equals("MngrStaffAction")) {
				String mngrAction = request.getParameter("mngrAction");

				if(mngrAction != null) {
					if(mngrAction.equals("dismissStaff")) {
						UserDBBean.getinstance().startUser(request.getParameter("user_id"));
					}
				}
				ArrayList<UserDataBean> staffList = null; 
				staffList = UserDBBean.getinstance().getAllStaff();
				request.setAttribute("staffList", staffList);
				request.setAttribute("count", new Integer(staffList.size()));

				address="/mngr/staff/staffManage.jsp";
			}
			else if(action.equals("MngrDeclarAction")) {
				String mngrAction = request.getParameter("mngrAction");

				if(mngrAction != null) {
					if(mngrAction.equals("boardDelete")){
						BoardDBBean.getinstance().news_delete(Integer.parseInt(request.getParameter("board_num")));
					}
				}

				ArrayList<DeclarationDataBean> declarList = null; 
				declarList = DeclarationDBBean.getinstance().declaration_getList();

				request.setAttribute("declarList", declarList);
				request.setAttribute("count", new Integer(declarList.size()));

				address="/mngr/declar/declarManage.jsp";
			}
			else if(action.equals("MngrCategoryAction")) {
				String mngrAction = request.getParameter("mngrAction");

				if(mngrAction != null) {
					if(mngrAction.equals("newCategory")) {
						CateDBBean.getinstance().add_cate(request.getParameter("cate_name"));
					}
					else if(mngrAction.equals("deleteCategory")){
						CateDBBean.getinstance().deleteCategory(Integer.parseInt(request.getParameter("cate_num")));
					}
				}

				ArrayList<CateDataBean> cateList = null;
				cateList = CateDBBean.getinstance().getList();
				request.setAttribute("categoryList", cateList);
				request.setAttribute("count", new Integer(cateList.size()));

				address="/mngr/category/categoryManage.jsp";
			}
			else if(action.equals("MngrBanAction")) {
				String mngrAction = request.getParameter("mngrAction");

				if(mngrAction != null) {
					if(mngrAction.equals("userStart")) {
						UserDBBean.getinstance().startUser(request.getParameter("user_id"));
					}
				}

				ArrayList<UserDataBean> bannedUserList = null;
				bannedUserList = UserDBBean.getinstance().getBannedUser();
				request.setAttribute("bannedUserList", bannedUserList);
				request.setAttribute("count", new Integer(bannedUserList.size()));

				address="/mngr/member/stopMemberManage.jsp";
			}
			//댓글 쓰기
			else if(action.equals("writeComment")) {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				CommentDataBean commentdt = new CommentDataBean();

				String cate_num = request.getParameter("cate_num");
				String board_num = request.getParameter("board_num");
				commentdt.setComment_content(request.getParameter("comment_content"));
				commentdt.setUser_id(request.getParameter("user_id"));

				if(commentdt.getComment_content() == null || commentdt.getComment_content().equals("")){
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "모든 내용을 입력하세요.");
					request.setAttribute("board_num", commentdt.getBoard_num());
					address = "view.jsp";
				}

				else if(cate_num == null || cate_num.equals("") || board_num == null || board_num.equals("") || commentdt.getUser_id() == null || commentdt.getUser_id().equals("")) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "내부적인 오류입니다.");
					request.setAttribute("board_num", commentdt.getBoard_num());
					address = "view.jsp";
				}
				else {
					commentdt.setCate_num(Integer.parseInt(cate_num));
					commentdt.setBoard_num(Integer.parseInt(board_num));

					int result = comment.write(commentdt);

					if(result == -1) {
						request.getSession().setAttribute("messageType", "오류 메시지");
						request.getSession().setAttribute("messageContent", "댓글 작성 실패");
						request.setAttribute("board_num", commentdt.getBoard_num());
						address = "view.jsp";
					}
					else {
						request.getSession().setAttribute("messageType", "성공 메시지");
						request.getSession().setAttribute("messageContent", "댓글 작성을 성공했습니다.");
						request.setAttribute("board_num", commentdt.getBoard_num());
						address = "view.jsp";
					}
				}
			}
			//댓글 수정 폼 으로 이동
			else if(action.equals("updatecomment")) {
				request.setAttribute("comment_num", request.getParameter("comment_num"));
				request.setAttribute("board_num", request.getParameter("board_num"));
				address = "updateComment.jsp";
			}
			//댓글 수정
			else if(action.equals("updateComment")) {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				CommentDataBean commentdt = new CommentDataBean();
				commentdt.setBoard_num(Integer.parseInt(request.getParameter("board_num")));
				commentdt.setComment_num(Integer.parseInt(request.getParameter("comment_num")));
				commentdt.setComment_content(request.getParameter("comment_content"));
				comment.update(commentdt);

				request.getSession().setAttribute("messageType", "성공 메시지");
				request.getSession().setAttribute("messageContent", "댓글 수정을 성공했습니다.");

				request.setAttribute("board_num", commentdt.getBoard_num());
				address = "view.jsp";
			}
			//댓글 삭제
			else if(action.equals("deleteComment")) {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");

				int comment_num=Integer.parseInt(request.getParameter("comment_num"));
				int board_num = Integer.parseInt(request.getParameter("board_num"));

				int result = comment.delete(comment_num);

				if(result == -1) {
					request.getSession().setAttribute("messageType", "오류 메시지");
					request.getSession().setAttribute("messageContent", "댓글 삭제를 실패했습니다.");
					request.setAttribute("board_num", board_num);
					address = "board.jsp";
				}
				else {
					request.getSession().setAttribute("messageType", "성공 메시지");
					request.getSession().setAttribute("messageContent", "댓글 삭제를 성공했습니다.");
					request.setAttribute("board_num", board_num);
					address = "view.jsp";
				}
			}

			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
