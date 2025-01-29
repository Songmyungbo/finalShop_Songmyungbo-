package dto;

public class Cart {
    private static int num;
    private int cartNum;  // 장바구니 항목 고유 번호
    private String memberId; // 사용자 ID
    private Item item;       // 장바구니에 담긴 아이템 (Item 객체)
    private int quantity;    // 아이템 수량
    private int itemNum;

    // 생성자
    public Cart(String memberId, Item item, int quantity) {
        this.cartNum = ++num;
        this.memberId = memberId;
        this.item = item;
        this.quantity = quantity;
    }

    public int getCartNum() {
        return cartNum;
    }

    public String getMemberId() {
        return memberId;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
    public Cart(int cartNum, String memberId, int ItemNum, int quantity) {
        this.cartNum = cartNum;
        this.memberId = memberId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart [cartNum=" + cartNum + ", memberId=" + memberId + ", item=" + item + ", quantity=" + quantity + "]";
    }
}
