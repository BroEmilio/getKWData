
import java.io.File;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SavingFileProfile {
	Path savingFile;
	JFileChooser saveChooser;
	String nameSavingFile;
	String extensionForFile = "txt";	//set extension for saving file
	
	void setSavingFileProfile() {
		configureSaveChooser(saveFilter);
	}
	
	//set filter for files 
	FileNameExtensionFilter saveFilter = new FileNameExtensionFilter
										("Pliki przetworzone ("+extensionForFile+")", extensionForFile);

	JFileChooser configureSaveChooser(FileNameExtensionFilter filter) {
		String userDir = System.getProperty("user.home");
		JFileChooser chooser= new JFileChooser(userDir +"/Desktop");
		chooser.setFileFilter(filter);
    	chooser.setDialogTitle("Zapisz plik jako");
    	chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	chooser.setAcceptAllFileFilterUsed(false);
    	chooser.setSelectedFile(new File("Przetworzony-"+nameSavingFile));
    	chooser.showSaveDialog(null);
    	savingFile = chooser.getSelectedFile().toPath();
    	
    	return chooser;
	}
	
	public Path getPath() {
		return savingFile;
	}
	
	public void setNameLoadedFile(String nameLoadedFile) {
		String rawName = nameLoadedFile.substring(0, nameLoadedFile.lastIndexOf("."));
		this.nameSavingFile = rawName+"."+extensionForFile;
	}
	
}
