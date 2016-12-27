/*
Class to create new playlist files (M3U, M3U8).
Version 1.0 (Thursday 8 december 2016, sunny-little cold).
Created by Oscar Miguel.
*/

/*
Section to registrate changes to this file.

OscarMiguel.
Thu 8 dec 2016, sunny-little cold.
- Create file.
- Add methods:
	orchestrator()
	welcome(boolean firstTime)
	requestData()
	returnFiles(String path, String sufix)	Method to replace the deleted methods.
	checkExistence(String path, String folder)	Method deleted.
	editFileName()
	createPlayList()
	getSongName()	Method deleted.
	addSong()


*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Collections;


public class Playlist{

    private String pathName;
    private String folderName;
    private String fileName;
    private String endsWith = ".m3u";
    private String sufixSong = ".flac";
    private String separator = "/";
    private String fileCreated = "";


	// The orchestrator() method is used to integrate the flow.
	public void orchestrator(){

	    // Saying welcome
	    welcome(true);

	    // Requesting data
	    requestData();

	    // Checking existence
	    ArrayList<String> exist = returnFiles(pathName, endsWith);
	    if(exist.size()>0){
		System.out.println("Exists "+exist.size()+" files ends with "+endsWith );
		for(String nameIt:exist)
		    System.out.println(nameIt);
	    }else{

	    // Starting process create
	    createPlayList();
	    }

	    // Getting song names
	    ArrayList<String> songs = returnFiles(pathName,sufixSong);
	    Collections.sort(songs);
	    if(songs.size()>0){
		System.out.println("Exists "+songs.size()+" files ends with "+sufixSong );
		for(String nameIt:songs)
		    System.out.println(nameIt);
	    }
	    
	    // Adding songs into the new file
	    //	    ArrayList<String> me = new ArrayList<String>();
	    addSong(songs);

	    // Saying goodbye
		welcome(false);
	}


	// The welcome() method displays a message.
	public void welcome(boolean firstTime){

		if (firstTime)
			System.out.println("****************************** Welcome! ******************************");
		else
			System.out.println("**********         Thank you for use this program.          **********");

	}


	// The requestData() method requests information to the user.
	public void requestData(){
		Scanner input = new Scanner(System.in);

		System.out.print("Enter path/directory name: ");
		pathName = input.nextLine();

		String[] splitPath = pathName.split(separator);

		folderName = splitPath[splitPath.length-1];
	}


    // Return files from a path (directory) if ends with the sufix.
    public ArrayList<String> returnFiles(String path, String sufix){
	ArrayList<String> returnFiles = new ArrayList<String>();
	File directory = new File(path);
	String[] content = directory.list();
	for(String contentName : content){
	    if(contentName.endsWith(sufix)){
		returnFiles.add(contentName);
	    }
	}
	return returnFiles;
    }


	// The createPlayList() method creates the playlist file.
	public void createPlayList(){
	    String fileToCreate = folderName+endsWith;
	    String userInput = "";
	    System.out.println("The next file will be created: "+fileToCreate);

	    Scanner input = new Scanner(System.in);
	    System.out.println("Do you want to edit the name? [y/n]");
	    userInput = input.next();
	    if(userInput.equalsIgnoreCase("y")){
		Scanner inNewName = new Scanner(System.in);
		System.out.println("Please enter the new name (without extension): ");
		userInput = inNewName.nextLine();
		fileToCreate = userInput+endsWith;
	    }
	    System.out.println("File to create: "+ fileToCreate);

	    try{

	    File newFile = new File(pathName+"/"+fileToCreate);
	    boolean created = newFile.createNewFile();

	    if(created){
		fileCreated = fileToCreate;
		System.out.println("The file "+fileToCreate+" was created successfully.");
	    }else{
		System.out.println("The file was not created.");
	    }
	    
	    }catch(IOException e){
		System.out.println(e);
		e.printStackTrace();
	    }
	    
	}


	// The addSong() method adds the song names into the playlist file.
	public void addSong(ArrayList<String> songName){
	    try{
		String pathFile = pathName + separator + fileCreated;
	    File document = new File(pathFile);
	    FileWriter writeIt = new FileWriter(document);

	    writeIt.write(System.lineSeparator());

	    for(String name : songName){
		writeIt.write(name);
		writeIt.write(System.lineSeparator());
	    }
	    
	    writeIt.flush();
	    writeIt.close();
	    
	    }catch(IOException e){
		System.out.println(e);
		e.printStackTrace();
	    }
	}


}
