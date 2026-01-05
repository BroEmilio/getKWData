
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.*;
import java.nio.charset.Charset;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;

public class ProcessFile {
	Path loadedFile, tempFile, savingFile;
	SavingFileProfile saver;
	Path tempFilePath = Paths.get(System.getProperty("user.home")+"\\Desktop");
	ArrayList<FieldData> listFieldsData = new ArrayList<FieldData>();
	
	
	/*
	String processLineOfFile(String originalLine) {
        String[] splitLine = originalLine.split("\\s+");
        String pointNumber = splitLine[1];
        
        Pattern pointsPrefixPattern = Pattern.compile("\\d{3}\\.\\d{3}-"); //erase prefix (example 223.112-)
        Matcher matcher = pointsPrefixPattern.matcher(pointNumber);
        String noPrefixNumber = matcher.replaceAll("").toString();
        
        Pattern slashPattern = Pattern.compile("[/]");	//replace / by -
        matcher = slashPattern.matcher(noPrefixNumber);
        String finalNumber = matcher.replaceAll("-");
        
        String newLine = "";
        for(int splitIndex=1; splitIndex<splitLine.length; splitIndex++)
        	newLine = newLine+splitLine[splitIndex]+"\t";
        
        newLine += finalNumber+"\r\n";
        
		return newLine;
	}
	*/
	
	
	
	public ProcessFile(Path file) {
		this.loadedFile = file;
		
	}
	
	/*
	boolean run() {
		BufferedReader reader;
		BufferedWriter writer;
		try {
			reader = Files.newBufferedReader(loadedFile, Charset.defaultCharset());
			tempFile = Files.createTempFile("tempProcessFile", ".txt");
			writer = Files.newBufferedWriter(tempFile, Charset.defaultCharset());
			String currentLine=null;
			while((currentLine = reader.readLine()) != null) {
				String newLine = processLineOfFile(currentLine);
				writer.write(newLine);
				writer.flush();
			}
			//saver = new SavingFileProfile();
			//saver.setNameLoadedFile(loadedFile.getFileName().toString());
			//saver.setSavingFileProfile();
			//savingFile = saver.getPath();
			//Files.copy(tempFile.toAbsolutePath(), savingFile, REPLACE_EXISTING);
			//Files.delete(tempFile);
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
	*/
	
	boolean run() {
		BufferedWriter writer;
		FieldData fieldData = null;
		try {
			tempFile = Files.createTempFile("tempProcessFile", ".txt");
			writer = Files.newBufferedWriter(tempFile, Charset.defaultCharset());
			org.jsoup.nodes.Document doc = Jsoup.parse(this.loadedFile, null);
			org.jsoup.select.Elements tbodysOwners = doc.select("tbody:contains(Lp)");
			org.jsoup.select.Elements tbodysNumbers = doc.select("tbody:contains(Nr dzia³ki)");
			System.out.println("Owners:"+tbodysOwners.size()+" Numbers"+tbodysNumbers.size());
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
				//writer.write(fieldData.toString());
				//writer.flush();
			}
			
			for(FieldData data:listFieldsData) {
				writer.write(data.toString());
				writer.flush();
			}
			
			
			
			
			/*
			int index=0;
			for(org.jsoup.nodes.Element tbody : tbodys){
				if(tbody.text().contains("Lp")){
					//System.out.println("I'm in "+index);
					fieldData = new FieldData();
					index++;
					org.jsoup.select.Elements trows = tbody.select("tr");
					for(org.jsoup.nodes.Element trow : trows){
						if(trow.text().contains("Lp")){
							for(int i=1; i<trows.size(); i++){
								org.jsoup.nodes.Element tData = trows.get(i);
								String[] lines = tData.select("td").get(1).toString().split(";");
								
								// get Name for individual people
								if(lines[0].contains("Rodzice") && ! lines[0].contains("ma³¿eñstwo")) {
									String[] nameList = lines[0].split("Rodzice");
									String nameRaw = nameList[0].subSequence(4, nameList[0].length()-1).toString();
									//System.out.println("Name "+nameRaw);
									fieldData.addOwner(nameRaw);
								}
								
								if(lines[0].contains("ma³¿eñstwo")) {
									String marriageOwners = "MA£¯. ";
									for(String currentLine:lines) {
										String[] nameList = currentLine.split("<br>");
										boolean isSecond = false;
										for(int j=1; j<nameList.length; j++){
											if(nameList[j].contains("Rodzice")){
												String[] nameRaws = currentLine.split("Rodzice");
												System.out.println("nameRaws:" + nameRaws[0]);
												marriageOwners += nameRaws[0];
												System.out.println("marriageOwners "+ marriageOwners);
											}
										}
									}
									//fieldData.addOwner(marriageOwners);
								}
								
								//writer.write(tData.text()+"\n");
								writer.flush();
							}
						}
					}
					
				}
				
				if(tbody.text().contains("Nr dzia³ki")){
						org.jsoup.select.Elements trows = tbody.select("tr");
							org.jsoup.select.Elements tcolumn = trows.get(1).select("td");
							ArrayList<String> fieldNameList = new ArrayList<String>(Arrays.asList(tcolumn.get(0).text().split(" ")));
							fieldData.setFieldNumber(fieldNameList.get(0));
							fieldData.setFieldId(fieldNameList.get(4));
							fieldData.setKW(tcolumn.get(tcolumn.size()-1).text());
							//System.out.println(tcolumn.get(0).text());	
							//System.out.println("KW "+tcolumn.get(tcolumn.size()-1).text()+"\n");
							writer.write(tcolumn.get(0).text()+"\n");
							writer.write("KW "+tcolumn.get(tcolumn.size()-1).text()+"\n\n");
							writer.flush();
							if(fieldData != null && fieldData.getKW()!= null)
								listFieldsData.add(fieldData);
						
					}
				}
				*/
			
			
			
			
			
		    
		    saver = new SavingFileProfile();
			saver.setNameLoadedFile(loadedFile.getFileName().toString());
			saver.setSavingFileProfile();
			savingFile = saver.getPath();
			Files.copy(tempFile.toAbsolutePath(), savingFile, REPLACE_EXISTING);
			Files.delete(tempFile);
			
			
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
