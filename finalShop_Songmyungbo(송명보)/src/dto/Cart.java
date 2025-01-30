package dto;

public class Cart {
    private static int num;
    private int cartNum; 
    private String memberId; 
    private Item item;       
    private int quantity;    
    private int itemNum;

  
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
    public Cart(int cartNum, String memberId, int quantity , Item item) {
        this.cartNum = cartNum;
        this.memberId = memberId;
        this.quantity = quantity;
        this.item = item;
    }

    @Override
    public String toString() {
        return "Cart [cartNum=" + cartNum + ", memberId=" + memberId + ", item=" + item + ", quantity=" + quantity + "]";
    }
}
