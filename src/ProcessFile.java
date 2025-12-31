
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
		FieldData fieldData = new FieldData();
		try {
			tempFile = Files.createTempFile("tempProcessFile", ".txt");
			writer = Files.newBufferedWriter(tempFile, Charset.defaultCharset());
			org.jsoup.nodes.Document doc = Jsoup.parse(this.loadedFile, null);
			org.jsoup.select.Elements tbodys = doc.select("tbody");
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
											//System.out.println(j+": "+nameList[j]);
										/*
										for(String name1 : nameList){
											if(name1.contains("Rodzice")){
												String[] nameRaws = currentLine.split("Rodzice");
												marriageOwners += nameRaws[0]+" i \r\n    ";
										}
										*/
											/*
											if(isSecond) {
												marriageOwners += nameRaw;
											}
											if(! isSecond) {
												marriageOwners += nameRaw+" i \r\n    ";
												isSecond = true;
											}
											*/
										}
									}
									//fieldData.addOwner(marriageOwners);
								}
								
								//fieldData.setOwnersList(new ArrayList<String>(Arrays.asList(tData.text().split(" "))));
								//fieldData.setOwnersList(getNamesAndShares(new ArrayList<String>(Arrays.asList(tData.text().split(" ")))));
								//System.out.println(index+" "+firstLine[0]);
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
				
			
			
			
				//for(org.jsoup.nodes.Element trow : trows){
					
					//}
				
					
				//String xxx = tbody.select("tr").text();
				//System.out.println(xxx+"\n");
			/*
		    org.jsoup.select.Elements rows = doc.select("tr");
		    for(org.jsoup.nodes.Element row :rows)
		    {
		        org.jsoup.select.Elements columns = row.select("td");
		        for (org.jsoup.nodes.Element column:columns)
		        {
		        	//System.out.println(column.text());
		        	writer.write(column.text()+"\n");
					writer.flush();
		        }
		        //System.out.println();
		    }
		    */
			
			
		    
		    saver = new SavingFileProfile();
			saver.setNameLoadedFile(loadedFile.getFileName().toString());
			saver.setSavingFileProfile();
			savingFile = saver.getPath();
			Files.copy(tempFile.toAbsolutePath(), savingFile, REPLACE_EXISTING);
			Files.delete(tempFile);
			for(FieldData data:listFieldsData)
				System.out.println(data.toString());
			System.out.println("listFieldsData size="+listFieldsData.size());
			
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
	
	ArrayList<String> getNamesAndShares(ArrayList<String> stringList) {
		ArrayList<String> rawData = new ArrayList<String>();
		//get names
		int i=2;
		while(stringList.get(i)=="Rodzice") {
			rawData.add(stringList.get(i));
			i++;
		}
		//get property
		rawData.add(stringList.get(stringList.size()-2));
		//get shares
		rawData.add(stringList.get(stringList.size()-1));
		
		return rawData;
	}
	
}
