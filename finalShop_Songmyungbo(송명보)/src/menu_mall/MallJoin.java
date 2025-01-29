package menu_mall;

import _mall.MenuCommand;
import controller.MallController;
import dao.MemberDAO;
import util.Util;

public class MallJoin implements MenuCommand {
	private MallController cont;

	@Override
	public void init() {
		cont = MallController.getInstance();
	}

	@Override
	public boolean update() {
		MemberDAO dao = MemberDAO.getInstance();
		System.out.println("=====[ 회원가입 ]=====");
		String id = Util.getValue("아이디 ");
		
		if(dao.getMemberById(id)!= null || id.equals("admin")) {
			System.out.println("이미 사용하는 아이디");
			return false;
		}
		
		String pw = Util.getValue("비밀번호 ");
		String name = Util.getValue("이름 ");
		
		if(dao.insertMember(id , pw , name)) {
			System.out.println("[ 회원 추가 완료 ]");
		}else {
			System.out.println("[ 회원 추가 실패]");
		}
		cont.setNext("MallMain");
		return false;
	}

}
