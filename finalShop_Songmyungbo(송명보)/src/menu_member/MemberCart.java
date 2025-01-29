package menu_member;

import java.util.List;

import _mall.MenuCommand;
import controller.MallController;
import dao.MemberDAO;
import dto.Item;
import util.Util;

public class MemberCart implements MenuCommand {
    private MallController cont;

    @Override
    public void init() {
        System.out.println("===========[ 구매내역 ]============");
        System.out.println("[1] 쇼핑하기\n[2] 뒤로가기\n[0] 종료");
        cont = MallController.getInstance();
    }

    @Override
    public boolean update() {
        // 로그인된 회원 ID를 가져옴
        String loggedInMemberId = MemberDAO.getInstance().getMemberId();

        if (loggedInMemberId == null) {
            System.out.println("로그인된 회원이 없습니다.");
            cont.setNext("MemberMain");
            return false;
        }

        // 로그인된 회원의 장바구니 아이템 목록을 가져옴
        List<Item> cartItems = MemberDAO.getInstance().getCartItems(loggedInMemberId);

        // 장바구니 내용 출력
        printCart(cartItems);

        // 메뉴 표시
        System.out.println("[1] 쇼핑하기\n[2] 뒤로가기\n[0] 종료");

        // 사용자 입력 처리
        int sel = Util.getValue("메뉴 선택 ", 0, 2);
        if (sel == 1) {
            cont.setNext("MemberShopping");
        } else if (sel == 2) {
            cont.setNext("MemberMain");
        } else if (sel == 0) {
            System.out.println("프로그램을 종료합니다.");
            cont.setNext(null); // 프로그램 종료
        }

        return false; // 프로그램 계속 실행
    }

    // 장바구니 내용 출력
    private void printCart(List<Item> cartItems) {
        System.out.println("=====================");
        if (cartItems.isEmpty()) {
            System.out.println("구매 내역이 없습니다.");
        } else {
            int totalCount = 0;
            int totalPrice = 0;
            int index = 1;

            for (Item item : cartItems) {
                int itemTotalPrice = item.getPrice() * item.getNumInCart(); // numInCart 반영
                totalCount += item.getNumInCart();
                totalPrice += itemTotalPrice;

                System.out.printf("[%d] %s(%d원) %d개 총 %d원\n",
                        index++, item.getItemName(), item.getPrice(), item.getNumInCart(), itemTotalPrice);
            }

            System.out.println("=====================");
            System.out.printf("총 %d개 (%d원)\n", totalCount, totalPrice);
        }
    }
}