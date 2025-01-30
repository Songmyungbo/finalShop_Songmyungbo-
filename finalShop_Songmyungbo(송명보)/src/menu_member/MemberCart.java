package menu_member;

import java.util.List;

import _mall.MenuCommand;
import controller.MallController;
import dao.CartDAO;
import dao.MemberDAO;
import dto.Cart;
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
        String loggedInMemberId = MemberDAO.getInstance().getMemberId();

        if (loggedInMemberId == null) {
            System.out.println("로그인된 회원이 없습니다.");
            cont.setNext("MemberMain");
            return false;
        }

        List<Cart> cartList = CartDAO.getInstance().getCartList();

        printCart(cartList,loggedInMemberId);

        System.out.println("[1] 쇼핑하기\n[2] 뒤로가기\n[0] 종료");

        int sel = Util.getValue("메뉴 선택 ", 0, 2);
        if (sel == 1) {
            cont.setNext("MemberShopping");
        } else if (sel == 2) {
            cont.setNext("MemberMain");
        } else if (sel == 0) {
            System.out.println("프로그램을 종료합니다.");
            cont.setNext(null); 
        }

        return false; 
    }

    private void printCart(List<Cart> cartList,String loggedInMemberId) {
        System.out.println("=====================");
        if (cartList.isEmpty()) {
            System.out.println("구매 내역이 없습니다.");
        } else {
            int totalCount = 0;
            int totalPrice = 0;
            int index = 1;

            for (Cart cart : cartList) {
            	 Item item = cart.getItem();
            	 if(item == null)continue;
            	 if(cart.getMemberId().equals(loggedInMemberId)) {
                int itemTotalPrice = item.getPrice() * cart.getQuantity(); 
                totalCount += cart.getQuantity(); 
                totalPrice += itemTotalPrice;
                System.out.printf("[%d] %s(%d원) %d개 총 %d원\n",
                        index++, item.getItemName(), item.getPrice(), cart.getQuantity(), itemTotalPrice);
                	}
                }

            System.out.println("=====================");
            System.out.printf("총 %d개 (%d원)\n", totalCount, totalPrice);
        }
    }
}

