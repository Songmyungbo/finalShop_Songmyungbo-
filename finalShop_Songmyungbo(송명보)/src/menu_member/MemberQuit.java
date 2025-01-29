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
            // 탈퇴하려는 회원 ID 가져오기
            String loggedInMemberId = MemberDAO.getInstance().getMemberId();
            
            if (loggedInMemberId == null) {
                System.out.println("로그인된 회원이 없습니다.");
                cont.setNext("MallMain");
                return false;
            }

            // 회원의 구매내역 및 장바구니 삭제
            deleteMemberCart(loggedInMemberId);

            // 회원 정보 삭제
            deleteMember(loggedInMemberId);

            System.out.println("회원 탈퇴가 완료되었습니다.");
            cont.setNext("MallMain");
        } else if (choice == 2) {
            // 뒤로가기
            cont.setNext("MemberMain");
        } else {
            // 종료
        	System.out.println("프로그램을 종료합니다.");
            cont.setNext(null);
        }
		return false;
    }

    private void deleteMemberCart(String memberId) {
        // 해당 회원의 장바구니 및 구매 내역 삭제
        List<Item> cartItems = MemberDAO.getInstance().getCartItems(memberId);
        
        if (cartItems != null) {
            cartItems.clear(); // 장바구니의 모든 아이템 삭제
        }
    }

    private void deleteMember(String memberId) {
        // 회원 정보 삭제
        MemberDAO.getInstance().removeMember(memberId);
        System.out.println("회원 " + memberId + " 정보가 삭제되었습니다.");
    }
}
