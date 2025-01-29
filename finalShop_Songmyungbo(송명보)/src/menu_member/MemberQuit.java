package menu_member;

import java.util.List;

import _mall.MenuCommand;
import controller.MallController;
import dao.CartDAO;
import dao.MemberDAO;
import dto.Item;
import util.Util;

public class MemberQuit implements MenuCommand {
    private MallController cont;

    public void init() {
        cont = MallController.getInstance();
        System.out.println("========[ 회원 탈퇴 ]=========");
        System.out.println("회원 탈퇴 시 구매 내역이 사라집니다.");
        System.out.println("정말 탈퇴하시겠습니까?");
        System.out.println("[1] 예");
        System.out.println("[2] 뒤로가기");
        System.out.println("[0] 종료");
    }

    public boolean update() {
        int choice = Util.getValue("메뉴 선택[0-2]: ", 0, 2);

        if (choice == 1) {
            String loggedInMemberId = MemberDAO.getInstance().getMemberId();
            
            if (loggedInMemberId == null) {
                System.out.println("로그인된 회원이 없습니다.");
                cont.setNext("MallMain");
                return false;
            }

            deleteMemberCart(loggedInMemberId);

            deleteMember(loggedInMemberId);

            System.out.println("회원 탈퇴가 완료되었습니다.");
            cont.setNext("MallMain");
        } else if (choice == 2) {
            cont.setNext("MemberMain");
        } else {
        	System.out.println("프로그램을 종료합니다.");
            cont.setNext(null);
        }
		return false;
    }

    private void deleteMemberCart(String memberId) {
        List<Item> cartItems = MemberDAO.getInstance().getCartItems(memberId);
        
        if (cartItems != null) {
            cartItems.clear(); 
        }
    }

    private void deleteMember(String memberId) {
        MemberDAO.getInstance().removeMember(memberId);
        System.out.println("회원 " + memberId + " 정보가 삭제되었습니다.");
    }
}
