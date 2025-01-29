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
			printMemberList();
		}else if(sel == 2) {
			delMember();
		}else if(sel == 3) {
			cont.setNext("AdminMain");
		}else if(sel == 0) {
			cont.setNext("MallMain");
		}
		return false;
	}

	private void delMember() {
		List<Member> mList = MemberDAO.getInstance().getMemberList();
		if(listCheck(mList)) {
			printMemberList();
			String id = Util.getValue("삭제 할 회원 아이디 ");
			if(id.equals("admin")) {
				System.out.println("관리자 회원 삭제 불가능");
				return;
			}else {
				delId(id,mList);
				return;
			}
		}else {
			return;
		}
	}

	private void delId(String id, List<Member> mList) {
		int idx = -1;
		for(int i = 0; i < mList.size();i++) {
			if(id.equals(mList.get(i).getId())) {
				idx = i;
			}
		}
		if(idx != -1) {
			mList.remove(idx);
			System.out.println("회원 삭제완료");
		}else {
			System.out.println("삭제 실패");
		}
		return;
	}

	private boolean listCheck(List<Member> mList) {
		if(mList.isEmpty()) {
			System.out.println("회원 목록이 비어있습니다!");
			return false;
		}
		return true;
	}

	private void printMemberList() {
		List<Member> mList =MemberDAO.getInstance().getMemberList();
		if(listCheck(mList)) {
			System.out.println("=====[ 회원 목록 ]=====");
			for(Member m : mList) {
				System.out.println(m);
			}
		}else {
			return;
		}
		
	}

}
