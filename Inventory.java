import java.util.*;

public class Inventory {
	
	private ArrayList<Item> itemList;
	private Item item;
	private int maxWeight;

 public Inventory() {
	 itemList = new ArrayList<Item>();
     maxWeight = 10;
 }
 
 public boolean addItem(Item item) {
	 if (getCurrWeight() + item.getWeight() > maxWeight) {
		
		 return false;
	 }
	 itemList.add(item);
	 return true;
 }
 
 public Item takeItem(String itemName) {
	 Item item = null;
	 for (Item items : itemList) {
		if (items.getName().equalsIgnoreCase(itemName)) {
			item = items;
			break;
		}
	 }
	 if (item != null) {
		 itemList.remove(item);
	 }
	 return item;
  }

public void showItems() {
	for (Item items : itemList) {
		System.out.println(items.getName());
	}
	
}

public boolean isInventoryEmpty() {
	return itemList.isEmpty();
}	

public int getCurrWeight() {
	int total = 0;
	for (Item items : itemList) {
		total += items.getWeight();
	}
	return total;
}
}

