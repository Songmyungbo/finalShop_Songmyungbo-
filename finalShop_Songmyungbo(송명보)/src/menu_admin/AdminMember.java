package menu_admin;

import java.util.List;

import _mall.MenuCommand;
import controller.MallController;
import dao.MemberDAO;
import dto.Member;
import util.Util;

public class AdminMember implements MenuCommand{
	private MallController cont;
	@Override
	public void init() {
		cont = MallController.getInstance();
		System.out.println("=====[ 관리자 회원목록 ]=====");
		System.out.println("[1] 회원목록\n[2] 회원삭제\n[3] 뒤로가기\n[0] 종료");
	}

	@Override
	public boolean update() {
		int sel = Util.getValue("메뉴 선택", 0, 3);
		
		if(sel == 1) {
			MemberDAO.getInstance().printMemberList();
		}else if(sel == 2) {
			MemberDAO.getInstance().delMember();
		}else if(sel == 3) {
			cont.setNext("AdminMain");
		}else if(sel == 0) {
			cont.setNext("MallMain");
		}
		return false;
	}


}
