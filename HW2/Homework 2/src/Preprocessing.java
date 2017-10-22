import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class Preprocessing {

	public static void main(String[] args) {
	
	}
	
	public static ArrayList<String> convertFileToArrayList(File file)  {
		String finalString = "";
		String[] finalArray;
		ArrayList<String> finalArrayList;
		Scanner targetInput = null;
		try { targetInput = new Scanner(file);} catch (FileNotFoundException e) {e.printStackTrace();}
		
		//read textfile one line at a time
		while(targetInput.hasNext()) {
			finalString += targetInput.next().trim() + " "; 
		}
		finalArrayList = preprocess(finalString);
		return finalArrayList;
	}

	
	public static ArrayList<String> preprocess(String textString){
		ArrayList<String> finalArrayList = new ArrayList<String>();
		String[] textArray;
		
		textString = textString.toLowerCase(); //lower case
		textArray = textString.split("[-!~,.()\"\\s]+"); //punctuation 
		
		finalArrayList = new ArrayList<String>(Arrays.asList(textArray)); //convert String[] to ArrayList<String>
		return finalArrayList;
	}
	
	public static ArrayList<String> toUnique(ArrayList<String> arrayList){
		ArrayList<String> uniqueArrayList;
		uniqueArrayList = new ArrayList<String>(new LinkedHashSet<String>(arrayList)); //unique tokens 
		return uniqueArrayList;
	}

}
