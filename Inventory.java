import java.util.*;

public class Inventory {
	
	private ArrayList<Items> itemList;
	private Items item;
	private int maxWeight;

 public Inventory() {
	 itemList = new ArrayList<Items>();
     maxWeight = 10;
 }
 
 public boolean addItem(Items item) {
	 if (getCurrWeight() + item.getWeight() > maxWeight) {
		
		 return false;
	 }
	 itemList.add(item);
	 return true;
 }
 
 public Items takeItem(String itemName) {
	 Items item = null;
	 for (Items items : itemList) {
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
	for (Items items : itemList) {
		System.out.println(items.getName());
	}
	
}

public boolean isInventoryEmpty() {
	return itemList.isEmpty();
}	

public int getCurrWeight() {
	int total = 0;
	for (Items items : itemList) {
		total += items.getWeight();
	}
	return total;
}
}

