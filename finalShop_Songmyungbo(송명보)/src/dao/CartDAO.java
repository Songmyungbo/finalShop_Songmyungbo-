package dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dto.Cart;
import dto.Item;
import dto.Member;
import util.Util;

public class CartDAO {
	private static CartDAO instance;
	public static CartDAO getInstance() {
		if(instance == null) instance = new CartDAO();
		return instance;
	}
	int idx = 0;
	List<Cart> cList = new ArrayList<>();
	 public void addCart(String memberId, Item item, int quantity) {
	        Cart cart = new Cart(memberId, item, quantity);
	        cList.add(cart);
	        item.setSoldCount(quantity);  
	    }
	
	
	
	
    public String getCategoryByIndex(int index, List<Item> itemList) {
        Set<String> categorySet = new HashSet<>();
        for (Item item : itemList) {
            categorySet.add(item.getCategoryName());
        }

        List<String> categories = new ArrayList<>(categorySet);
        return categories.get(index - 1);  
    }

   
    public List<Item> getItemsByCategory(String category, List<Item> itemList) {
        List<Item> selectedItems = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getCategoryName().equals(category)) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

 
    public void printItemList(List<Item> selectedItems) {
        if (selectedItems.isEmpty()) {
            System.out.println("선택된 카테고리에는 아이템이 없습니다.");
            return;
        }

   
        System.out.printf("[%s의 아이템 목록]\n", selectedItems.get(0).getCategoryName());
        int num = 1;
        for (Item item : selectedItems) {
            System.out.printf("[%d] %s %d원\n", num++, item.getItemName(), item.getPrice());
        }

        while (true) {
            String itemName = Util.getValue("구매 아이템 이름");
            Item selectedItem = null;
            for (Item item : selectedItems) {
                if (itemName.equals(item.getItemName())) {
                    selectedItem = item;
                    break;
                }
            }
            if (selectedItem == null) {
                System.out.println("아이템 이름 오류. 다시 입력해주세요.");
                continue;
            }
            int itemCnt = Util.getValue("아이템 구매 수량 ", 1, 100);
            
            // 장바구니에 아이템 추가
            String loginUserId = MemberDAO.getInstance().getMemberId();
            if (loginUserId != null) {
                MemberDAO.getInstance().addItemToCart(loginUserId, selectedItem, itemCnt);
                addCart(loginUserId, selectedItem, itemCnt);
                System.out.printf("[ %s %d개 구매 완료\n", itemName, itemCnt);
                break;
            } else {
                System.out.println("로그인된 회원이 없습니다.");
                break;
            }
        }
    }

	public List<Cart> getCartList() {
		return cList;
	}
	
	public void setLoadData(String data) {
		if(data.equals("")) {
			System.out.println("저장을 먼저하세요!");
			return;
		}
		String[] info = data.split("\n");
		for(int i = 0; i < info.length; i++) {
			String[] temp = info[i].split("/");
			cList.add(new Cart(Integer.parseInt(temp[0]),temp[1],Integer.parseInt(temp[3]),ItemDAO.getInstance().getItemByNum(Integer.parseInt(temp[2]))));
		}
	}
	
	public String getSaveData() {
		if(cList.size() <= 0) {
			System.out.println("장바구니가 존재하지 않습니다");
			return null;
		}
		String data = "";
		for(Cart c : cList){
			data += String.format("%d/%s/%d/%d\n",c.getCartNum(),c.getMemberId(),c.getItem().getItemNum(),c.getQuantity());
		}
		return data.substring(0,data.length()-1);
		
	}
	
}
