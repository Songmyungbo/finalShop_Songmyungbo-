package menu_admin;

import _mall.MenuCommand;

import controller.MallController;
import dao.FileDAO;
import dao.MemberDAO;
import util.Util;

public class _AdminMain implements MenuCommand{
	private MallController cont;
	@Override
	public void init() {
		cont = MallController.getInstance();
		System.out.println("=====[ 관리자 ]=====");
	}
	
	@Override
	public boolean update() {
		MemberDAO dao = MemberDAO.getInstance();
		System.out.println("[1] 회원관리\n[2] 상품관리\n[3] 게시판관리\n[4] 로그아웃\n[5] 파일저장\n[0] 종료");
		System.out.println("=====================");
		int sel = Util.getValue("메뉴 입력", 0, 5);
		if(sel == 1) {
			cont.setNext("AdminMember");
		}else if(sel == 2) {
			cont.setNext("AdminItem");
		}else if(sel == 3) {
			cont.setNext("AdminBoard");
		}else if(sel == 4) {
			cont.setNext("MallMain");
		}else if(sel == 5) {
			FileDAO.getInstance().saveToFile();
		}else {
			cont.setNext(null);
		}
		return false;
	}

}
