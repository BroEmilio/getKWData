
public class Owner {
	String name;
	String ownershipType;
	String participation;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwnershipType() {
		return ownershipType;
	}
	public void setOwnershipType(String ownershipType) {
		this.ownershipType = ownershipType;
	}
	public String getParticipation() {
		return participation;
	}
	public void setParticipation(String participation) {
		this.participation = participation;
	}
	@Override
	public String toString() {
		String output = "";
		
		if(participation.equals("1/1")) {
			output += name;
		} else
			output += name + "- " + participation;
		
		return output;
	}
	
	
	
	
	
	

}
