
public class Items {
	
	private String name;
	private String description;
	private int weight;
	
	public Items(String name,String Itemdescription, int weight) {
		this.name = name;
		this.description = description;
		
	}
	
	public String getShortDescription() {
    
        return description;
    }

	public String getName() {
		return name;
	}
	public int getWeight() {
		return weight;
	}

}
 	