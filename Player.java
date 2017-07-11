
public class Player {
	
	private Room currentRoom;
	private int maxhealth;
	private int currhealth;
	private Inventory inventory;
	
	public Player(){
		maxhealth = 50;
		currhealth = maxhealth;
		inventory = new Inventory();
	}
	public void damage(int dmg){
		if (currhealth - dmg <= 0){
			currhealth = 0;
		} else {
			currhealth -= dmg;
		}
	}
	public void heal (int hl){
	    if (currhealth + hl >= maxhealth){
	    	currhealth = maxhealth;
	    } else {
	    	currhealth += hl;
	    }
	}

	public void isAlive(){
		if (currhealth <= 0){
			System.out.println("Why don't you just quit for today, you don't seem to know how to manage your health");
			System.exit(0);
		}
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
	public void setCurrentRoom(Room currentRoom) {
		this.currentRoom = currentRoom;
	}
	public boolean take(Items item) {
		return inventory.addItem(item);
	}
	
	 public void goRoom(Command command)
	    {
	        if(!command.hasSecondWord()) {
	            // if there is no second word, we don't know where to go...
	            System.out.println("Go where?");
	            return;
	        }

	        String direction = command.getSecondWord();

	        // Try to leave current room.
	        Room nextRoom = currentRoom.getExit(direction);

	        if (nextRoom == null)
	            System.out.println("You walked face first into a wall, try another direction!");
	        else {
	            currentRoom = (nextRoom);
	            damage(5);
	            isAlive();
	            System.out.println();
	            System.out.println(currentRoom.getLongDescription());
	            System.out.println(currhealth);
	            
	        }
	    }
	public Items takeItem(String itemName) {
		return inventory.takeItem(itemName);
	}
}
