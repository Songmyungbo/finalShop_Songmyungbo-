package dao;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Cart;
import dto.Item;
import dto.Member;

public class MemberDAO {
    
    private static MemberDAO instance;
    public static MemberDAO getInstance() {
        if(instance == null) instance = new MemberDAO();
        return instance;
    }

    int idx = 0;
    List<Member> mList = new ArrayList<>();
    private Map<String, List<Item>> memberCarts = new HashMap<>(); 
    private String logInMemberId; 

    public boolean insertMember(String id, String pw, String name) {
        Member member = new Member(id, pw, name);
        mList.add(member);
        memberCarts.put(id, new ArrayList<>()); 
        if(!id.equals("admin")) {
            System.out.println(member);
        }
        idx++;
        return true;
    }

    public String isValidMember(String id, String pw) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getId().equals(id) && mList.get(i).getPw().equals(pw)) {
            	logInMemberId = id; 
                return id;
            }
        }
        return null;
    }

    public String getMemberById(String id) {
        String ids = "";
        if (mList.size() <= 0) {
            return null;
        }
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getId().equals(id)) {
                ids = id;
                break;
            }
        }
        if (ids.equals("")) {
            return null;
        }
        return ids;
    }

    public List<Member> getMemberList() {
        return mList;
    }

  
    public String getMemberId() {
        return logInMemberId;
    }

 
    public List<Item> getCartItems(String memberId) {
        List<Item> cartItems = memberCarts.get(memberId);
        if (cartItems == null) { 
            cartItems = new ArrayList<>();
            memberCarts.put(memberId, cartItems);
        }
        return cartItems;
    }

  
    public void addItemToCart(String memberId, Item item, int num) {
        List<Item> cartItems = memberCarts.get(memberId);
        if (cartItems == null) { 
            cartItems = new ArrayList<>();
            memberCarts.put(memberId, cartItems);
        }

        for (Item cartItem : cartItems) {
            if (cartItem.getItemName().equals(item.getItemName())) {
                cartItem.setNumInCart(cartItem.getNumInCart() + num); 
                return;
            }
        }

        item.setNumInCart(num);
        cartItems.add(item);
    }

	public void printInfo(String loggedInUserId) {
		for(Member m : mList) {
			if(m.getId().equals(loggedInUserId)) {
				System.out.println(m);
				break;
			}
		}
		return;
	}

    public boolean changePassword(String memberId, String oldPassword, String newPassword) {
        for (Member member : mList) {
            if (member.getId().equals(memberId)) {
                if (member.getPw().equals(oldPassword)) {
                    member.setPw(newPassword);
                    System.out.println("비밀번호가 변경되었습니다.");
                    return true;
                } else {
                    System.out.println("현재 비밀번호가 일치하지 않습니다.");
                    return false;
                }
            }
        }
        return false;
    }


	public void removeMember(String memberId) {
		  Member removeMember = null;
	        for (Member member : mList) {
	            if (member.getId().equals(memberId)) {
	                removeMember = member;
	                break;
	            }
	        }
	        if ( removeMember != null) {
	            mList.remove(removeMember);
	    }
	}
	public void setLoadData(String data) {
		if(data == null) {
			System.out.println("저장을 먼저하세요!");
			return;
		}
		String[] info = data.split("\n");
		for(int i = 0; i < info.length; i++) {
			String[] temp = info[i].split("/");
			mList.add(new Member(Integer.parseInt(temp[0]),temp[1],temp[2],temp[3]));
		}
	}
	
	public String getSaveData() {
		if(mList.size() <= 0) {
			System.out.println("아이템이 없습니다");
			return null;
		}
		String data = "";
		for(Member m : mList) {
			data += String.format("%d/%s/%s/%s\n", m.getMemberNum(),m.getId(),m.getPw(),m.getMemberName());
		}
		return data.substring(0,data.length()-1);
	}


}

