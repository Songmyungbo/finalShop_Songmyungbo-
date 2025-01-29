package dto;

public class Item {
	private static int num;
	private int itemNum;
	private String categoryName;
	private String itemName;
	private int price;
	private static int itemNumber = 0;
	private int numInCart;
	private int soldCount; 


	public int getSoldCount() {
		return soldCount;
	}
	public void setSoldCount(int quantity) {
        this.soldCount += quantity;
    }
	
	public static int getNum() {
		return num;
	}

	public static void setNum(int num) {
		Item.num = num;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	public void setNumInCart(int numInCart) {
	    this.numInCart = numInCart;
	}

	public int getNumInCart() {
	    return this.numInCart;
	}
	
	public Item(String categoryName, String itemName, int price) {
		itemNumber++;
		this.itemNum = itemNumber;
		this.categoryName = categoryName;
		this.itemName = itemName;
		this.price = price;
		this.numInCart = 0;
		this.soldCount = 0;
	}
	public Item(int itemNum, String categoryName, String itemName, int price, int soldCount) {
	    this.itemNum = itemNum;
	    this.categoryName = categoryName;
	    this.itemName = itemName;
	    this.price = price;
	    this.soldCount = soldCount;
	}
	@Override
	public String toString() {
		return "[" + itemNum + "] [" + categoryName + "] [" + itemName + "] ["
				+ price + "원]";
	}
	
}