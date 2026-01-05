
import java.util.ArrayList;

public class FieldData {
	private static int count;
	int id;
	String FieldId;
	String FieldNumber;
	ArrayList<Owner> OwnersList = new ArrayList<Owner>();
	String KW;	
	

public FieldData(){
	count++;
	this.id = count;
}

public int getId(){
	return id;
}

public String getFieldId() {
	return FieldId;
}

public void setFieldId(String fieldId) {
	FieldId = fieldId;
}

public String getFieldNumber() {
	return FieldNumber;
}

public void setFieldNumber(String fieldNumber) {
	this.FieldNumber = fieldNumber;
}

public ArrayList<Owner> getOwnersList() {
	return OwnersList;
}

public void setOwnersList(ArrayList<Owner> ownersList) {
	this.OwnersList = ownersList;
}

public void addOwner(Owner owner) {
	this.OwnersList.add(owner);
}

public String getKW() {
	return KW;
}

public void setKW(String kW) {
	this.KW = kW;
}

@Override
public String toString() {
	String output = "";
	output = FieldNumber +" - " + FieldId + "\r\n";
	boolean isFirst=true;
	for(Owner owner:OwnersList) {
		if(isFirst) {
			if(owner.getOwnershipType().equals("W³asnoœæ"))
				output += "W£. ";
			isFirst = false;
		} else if(! owner.getOwnershipType().equals("W³asnoœæ")) {
			output += "ZA. ";
		}
		
		if(output.equals("W£.")) {
			output += owner.toString() +"\r\n";
		} else
			output += "   "+owner.toString() +"\r\n";
	}
	output += "KW "+ KW +"\r\n\r\n";
	
	return output;
}



}



