package menu_admin;

import _mall.MenuCommand;
import controller.MallController;
import dao.BoardDAO;
import util.Util;

public class AdminBoard implements MenuCommand{
	private MallController cont;
	@Override
	public void init() {
		cont = MallController.getInstance();
		System.out.println("=========[ 관리자 게시판 ]=========");
		System.out.println("[1] 게시글목록\n[2] 게시글삭제\n[3] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
	}
	
	@Override
	public boolean update() {
		int sel = Util.getValue("메뉴 선택", 0, 3);
		if(sel == 1) {
			BoardDAO.getInstance().showBoard();
		}else if(sel == 2) {
			BoardDAO.getInstance().deleteBoardAdmin();
		}else if(sel == 3) {
			cont.setNext("AdminMain");
		}else {
			System.out.println("종료합니다");
			cont.setNext(null);
		}
		
		return false;
	}

}
