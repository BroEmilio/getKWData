
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;

public class ProcessFile {
	Path loadedFile, tempFile, savingFile;
	SavingFileProfile saver;
	Path tempFilePath = Paths.get(System.getProperty("user.home")+"\\Desktop");
	ArrayList<FieldData> listFieldsData = new ArrayList<FieldData>();
		
	public ProcessFile(Path file) {
		this.loadedFile = file;
	}
	
	boolean run() {
		//BufferedWriter writer;
		FieldData fieldData = null;
		try {
			tempFile = Files.createTempFile("tempProcessFile", ".txt");
			//writer = Files.newBufferedWriter(tempFile, Charset.defaultCharset());
			org.jsoup.nodes.Document doc = Jsoup.parse(this.loadedFile, null);
			org.jsoup.select.Elements tbodysOwners = doc.select("tbody:contains(Lp)");
			org.jsoup.select.Elements tbodysNumbers = doc.select("tbody:contains(Nr dzia³ki)");
			for(int i=0; i<tbodysOwners.size(); i++){
				fieldData = new FieldData();
				org.jsoup.select.Elements tRows = tbodysOwners.get(i).select("tr");
				fieldData.setOwnersList(getNamesAndParticipations(tRows));
				
				//get FieldNumber, FieldId and KW
				org.jsoup.nodes.Element tbody = tbodysNumbers.get(i);
				org.jsoup.select.Elements tcolumn = tbody.select("tr").get(1).select("td");
				ArrayList<String> fieldNameList = new ArrayList<String>(Arrays.asList(tcolumn.get(0).text().split(" ")));
				fieldData.setFieldNumber(fieldNameList.get(0));
				fieldData.setFieldId(fieldNameList.get(4));
				fieldData.setKW(tcolumn.get(tcolumn.size()-1).text());
				if(fieldData != null && fieldData.getKW()!= null)
					listFieldsData.add(fieldData);
			}
			
			/*
			for(FieldData data:listFieldsData) {
				writer.write(data.toString());
				writer.flush();
			}
		    saver = new SavingFileProfile();
			saver.setNameLoadedFile(loadedFile.getFileName().toString());
			saver.setSavingFileProfile();
			savingFile = saver.getPath();
			Files.copy(tempFile.toAbsolutePath(), savingFile, REPLACE_EXISTING);
			Files.delete(tempFile);
			*/
			
		} catch (FileNotFoundException e) {
			displayErrorFrame(e.toString());
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			displayErrorFrame(e.toString());
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	void displayErrorFrame(String errorMessege) {
		JOptionPane.showMessageDialog(null,
				"Wyst¹pi³ b³ad: \r\n"+errorMessege,
		        "Wyst¹pi³ b³¹d",
		        JOptionPane.ERROR_MESSAGE);
	}
	
	
	ArrayList<Owner> getNamesAndParticipations(org.jsoup.select.Elements tRows){
		ArrayList<Owner> ownersAndSharesList = new ArrayList<Owner>();
		Owner owner = null;
		String ownerName = null;
		for(int i=1; i<tRows.size(); i++){
			org.jsoup.select.Elements tColumns = tRows.get(i).select("td");
			org.jsoup.nodes.Element tName = tColumns.get(1);
			owner = new Owner();
			ownerName = "";
			String ownershipType = tColumns.get(2).text();
			String participation = tColumns.get(3).text();
			String longName = tName.toString();
			longName = longName.substring(4);
			String[] nameList = longName.split("<br>");
			
			// get marriage names
			if(nameList[0].contains("ma³¿eñstwo")) {
				ownerName +="MA£¯.";
				boolean isFirst = true;
				for(String line:nameList){
					if(line.contains("Rodzice")){
						String nameMeriage = getNameIndyvidual(line);
						nameMeriage = nameMeriage.substring(1);
						ownerName += nameMeriage;
						if(isFirst){
							ownerName += "i \n        ";
							isFirst=false;
						}
					}
				}
			}
			
			// get individual person name
			if(nameList[0].contains("Rodzice")){
				ownerName += getNameIndyvidual(nameList[0]);
			} else
				// get institutions names
				if(! nameList[0].contains("ma³¿eñstwo")) {
					String nameInstitution = "";
					if(nameList[0].contains("</td>")) {
						nameInstitution = nameList[0].split("</td>")[0].toString();
					}
					else {
						nameInstitution = nameList[0].split("\n")[0].toString();
					}
					ownerName = nameInstitution;
				} 
			System.out.println(ownerName);
			owner.setName(ownerName);
			owner.setOwnershipType(ownershipType);
			owner.setParticipation(participation);
			ownersAndSharesList.add(owner);
		}
		
		return ownersAndSharesList;
	}
	
	String getNameIndyvidual (String input){
		String name = null;
		name = input.split("Rodzice")[0];
		return name;
	}
	
}
