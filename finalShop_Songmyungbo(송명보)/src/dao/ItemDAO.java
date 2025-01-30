package dao;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import dto.Board;
import dto.Cart;
import dto.Item;
import dto.Member;
import util.Util;
import menu_admin.AdminItem;
public class ItemDAO {

	private static ItemDAO instance;
	public static ItemDAO getInstance() {
		if(instance == null) instance = new ItemDAO();
		return instance;
	}
	List<Item> itemList = new ArrayList<>();
	public void addItem() {
		if(itemList.size() > 0) {
			printItemList();
		}else {
			System.out.println("현재 아이템이 없습니다");
		}
		String itemName = Util.getValue("아이템 ");
		if(itemCheck(itemName)) {
			itemDuplePrint();
			return;
		}else {
			String categoryName = Util.getValue("카테고리 ");
			if(categoryCheck(itemName,categoryName)) {
				itemDuplePrint();
				return;
			}
			int itemPrice = Util.getValue("가격 ", 100, 1000000);
			itemList.add(new Item(categoryName, itemName, itemPrice));
			updateItemNumbers();
			System.out.println("아이템 추가완료!");
		}
	}
	private void itemDuplePrint() {
		System.out.println("이미 있는 카테고리/아이템 이릅입니다");
		return;
	}
	private boolean categoryCheck(String itemName ,String categoryName) {
		if(itemList.size() <= 0) {
			return false;
		}
		for(int i = 0; i < itemList.size(); i++) {
			if(itemList.get(i).getCategoryName().equals(itemName)||
					itemList.get(i).getItemName().equals(categoryName))return true;
		}
		return false;
	}

	private boolean itemCheck(String item) {
		if(itemList.size() <= 0) return false;
		for(int i = 0; i<itemList.size(); i++) {
			if(itemList.get(i).getItemName().equals(item)) return true;
		}
		return false;
	}
	private void printItemList() {
		System.out.println("===== 카테고리별 아이템 목록 =====");
		itemList.stream().sorted(Comparator.comparing(Item::getCategoryName).thenComparing(Item::getItemName)).forEach(System.out::println);
	}
	public void delItem() {
		if(itemList.size() <= 0) {System.out.println("아이템 목록이 없습니다");return;}
		printItemList();
		System.out.println("[ 아이템 삭제시 구매 내역이 사라집니다 ]");
		int delIdx = Util.getValue("삭제 할 아이템 번호 ", 1, itemList.size());
		Item itemToDelete = itemList.get(delIdx - 1);
		removeItemFromAllCarts(itemToDelete);
		itemList.remove(delIdx-1);
		 updateItemNumbers();
		System.out.println("아이템 삭제 완료");return;
	}
	private void removeItemFromAllCarts(Item item) {
	    if (item == null) {
	        System.out.println("아이템 정보가 없습니다");
	        return;
	    }
	    MemberDAO memberDao = MemberDAO.getInstance();
	    List<Member> memberList = memberDao.getMemberList(); 

	    if (memberList != null && !memberList.isEmpty()) {
	        for (Member member : memberList) {
	            List<Item> cartItems = memberDao.getCartItems(member.getId());

	            if (cartItems != null && !cartItems.isEmpty()) {
	                for (int i = 0; i < cartItems.size(); i++) {
	                    if (cartItems.get(i).getItemName().equals(item.getItemName())) {
	                        cartItems.remove(i);
	                        i--; 
	                    }
	                }
	            }
	        }
	        System.out.println("아이템이 모든 회원의 장바구니에서 삭제되었습니다.");
	    } else {
	        System.out.println("회원 목록이 비어 있습니다.");
	    }
	}
	private void updateItemNumbers() {
	    for (int i = 0; i < itemList.size(); i++) {
	        itemList.get(i).setItemNum(i + 1); 
	    }
	}
	
	
	public List<Item> getItemList() {
		return itemList;
	}
	public void showItemBySales() {
	      if (itemList == null || itemList.isEmpty()) {
	          System.out.println("아이템 목록이 비어 있습니다.");
	          return;
	        }

	        itemList.sort(Comparator.comparingInt(Item::getSoldCount).reversed());

	        System.out.println("========== 판매된 아이템 목록 ===========");
	        for (int i = 0; i < itemList.size(); i++) {
	            Item item = itemList.get(i);
	            System.out.println("[" + (item.getItemNum()) + "] [" + item.getCategoryName() + "] [" + item.getItemName() + "] [" + item.getPrice() + "원] " + item.getSoldCount() + "개");
	       }
	}
	public Item getItemByNum(int itemNum) {
		for(Item item : itemList) {
			if(item.getItemNum() == itemNum) {
				return item;
			}
		}
		return null;
	}
	public void setLoadData(String data) {
		if(data.equals("")) {
			System.out.println("저장을 먼저하세요!");
			return;
		}
		String[] info = data.split("\n");
		for(int i = 0; i < info.length; i++) {
			String[] temp = info[i].split("/");
			itemList.add(new Item(Integer.parseInt(temp[0]),temp[1],temp[2],Integer.parseInt(temp[3]),Integer.parseInt(temp[4])));
		}
	}
	
	public String getSaveData() {
		if(itemList.size() <= 0) {
			System.out.println("아이템이 없습니다");
			return null;
		}
		String data = "";
		for(Item i : itemList) {
			data += String.format("%d/%s/%s/%d/%d\n", i.getItemNum(),i.getCategoryName(),i.getItemName(),i.getPrice(),i.getSoldCount());
		}
		return data.substring(0,data.length()-1);
	}
	

}
