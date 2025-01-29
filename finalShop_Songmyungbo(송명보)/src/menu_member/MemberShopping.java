package menu_member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import _mall.MenuCommand;
import controller.MallController;
import dao.CartDAO;
import dao.ItemDAO;
import dao.MemberDAO;
import dto.Item;
import dto.Member;
import util.Util;

public class MemberShopping implements MenuCommand {
    private MallController cont;
    
    @Override
    public void init() {
        System.out.println("======== 쇼핑몰에 오신것을 환영합니다 ========");
        cont = MallController.getInstance();
    }

    private void printCategory(List<Item> itemList) {
        Set<String> categoryDuple = new HashSet<>();
        
        for (Item item : itemList) {
            categoryDuple.add(item.getCategoryName());
        }
        
        int idx = 1;
        for (String category : categoryDuple) {
            System.out.printf("[%d] %s\n", idx++, category);
        }
    }
    
    
    
    @Override
    public boolean update() {
        List<Item> itemList = ItemDAO.getInstance().getItemList();
        if (itemList.isEmpty()) {
            System.out.println("아이템이 없습니다. 관리자에게 문의하세요.");
            cont.setNext("MemberMain");
        } else {
            printCategory(itemList);
            Set<String> categoryDuple = new HashSet<>();
            for (Item item : itemList) {
                categoryDuple.add(item.getCategoryName());
            }
            int categoryCount = categoryDuple.size();
            int sel = Util.getValue("메뉴 선택: ", 0, categoryCount);
            System.out.println("0) 뒤로가기");
            if (sel == 0) {
                cont.setNext("MemberMain");
            } else if (sel >= 1 && sel <= itemList.size()) {
                // 선택한 카테고리만 해당하는 아이템 목록 출력
                String selectedCategory = CartDAO.getInstance().getCategoryByIndex(sel, itemList);
                List<Item> selectedItems = CartDAO.getInstance().getItemsByCategory(selectedCategory, itemList);
				CartDAO.getInstance().printItemList(selectedItems);
            }
        }
        return false;
    }

    
}