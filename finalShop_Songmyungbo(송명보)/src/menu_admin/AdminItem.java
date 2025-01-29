package menu_admin;

import _mall.MenuCommand;
import controller.MallController;
import dao.ItemDAO;
import util.Util;

public class AdminItem implements MenuCommand{
	private MallController cont;
	
	
	@Override
	public void init() {
		cont = MallController.getInstance();
		System.out.println("=====[ 관리자 쇼핑몰관리 ]=====");
		
		
	}

	@Override
	public boolean update() {
		ItemDAO itemDao = ItemDAO.getInstance();
		System.out.println("[1] 아이템 추가\n[2] 아이템 삭제\n[3] 총 매출 아이템 갯수 출력(판매량 높은순으로)\n[4] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
		int sel = Util.getValue("메뉴 선택", 0, 4);
		if(sel == 1) {
			itemDao.addItem();
		}else if(sel == 2) {
			itemDao.delItem();
		}else if(sel == 3) {
			itemDao.showItemBySales();
		}else if(sel == 4) {
			cont.setNext("AdminMain");
		}else {
			cont.setNext("MallMain");
		}
		
		return false;
	}

}
