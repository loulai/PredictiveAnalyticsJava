import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
public class NGram {

	public int windowSize;
	public ArrayList<String> rows;
	public Map<String, ArrayList<Integer>> columnsMap;
	public int nRow;
	public int nCol;
	ArrayList<String> initialTextList;
	
	public NGram(ArrayList<String> textList) {
		initialTextList = textList;
		ArrayList<String> uniqueTextArray = new ArrayList<String>(new LinkedHashSet<String>(textList)); //unique tokens 
		
		rows = uniqueTextArray;
		nRow = rows.size();
		nCol = rows.size();
		
		Map<String, ArrayList<Integer>> columnsMap = new HashMap<>(); //columns is a hash map
		for(int i=0; i < nRow; i++) {
			ArrayList<Integer> freq = new ArrayList<>(Collections.nCopies(nRow, 0)); //column initialized with all zeros
			columnsMap.put(uniqueTextArray.get(i), freq);
		}
		this.columnsMap = columnsMap;
	}
	
	public void printNGram() {
		//column header
		System.out.printf("%-10s ", ""); //padding for row
		for(String key:columnsMap.keySet()) {
			System.out.printf("%-10s ", key);
		}
		System.out.println();
		
		//rows
		for(int i = 0; i < nRow; i++) {
			System.out.printf("%-10s ", rows.get(i)); //current row (e.g. "the")
			for(String key:columnsMap.keySet()) { //iterates over all columns for that row
				System.out.printf("%-10d ", columnsMap.get(key).get(i)); 
			}
			System.out.println();
		}
	}
	
	public NGram addFrequencies(int n) {
		windowSize = n; 
		
		for(int i = 0; i < initialTextList.size() - 1 ;i++) {
			String currentWord = initialTextList.get(i);
			String followingWord = initialTextList.get(i+1);
			int rowPosition = rows.indexOf(currentWord); //position of row in table
			int currentFreq = columnsMap.get(followingWord).get(rowPosition);
			int newFreq = currentFreq + 1;
			
			//update 
			columnsMap.get(followingWord).set(rowPosition, newFreq);
		}
		return this;
	}
	
	public ArrayList<String[]> getConcurrent(int threshold){
		ArrayList<String[]> grandResults = new ArrayList<String[]>(); // return: [ ["the", "the", "0"], ["the", "theme", "1"] ]
		
		for(int i = 0; i < nRow; i++) { 
			for(String key:this.columnsMap.keySet()) {
				String currentRow = this.rows.get(i); //"the"
				String currentColumn = key; //"theme"
				int currentFreq = this.columnsMap.get(key).get(i); //"0"
				
				//compile one result
				String[] oneResult = {currentRow, currentColumn, String.valueOf(currentFreq)};
				
				//append to grandResults
				grandResults.add(oneResult);
			}
		}
		
	
		for(int i = 0; i < grandResults.size(); i++) {
			String[] currentArray = grandResults.get(i);
			for(int k = 0; k < currentArray.length; k++) {
				System.out.printf("%-10s ", currentArray[k]); //"the "
			}
			System.out.println();
		}
		return grandResults;
		
	}
	
	public static void main(String[] args) {
		
		ArrayList<String> test = new ArrayList<String>(Arrays.asList("today we sent mail to the mail person but the mail truck is late".split(" ")));
		NGram myNGram = new NGram(test);
		myNGram.addFrequencies(2);
		System.out.println("---------TABLE---------");
		myNGram.printNGram();
		System.out.println("-----------------------");
		myNGram.getConcurrent(2);
		/*
		Map<String, Integer> mappy = new HashMap<>();
		mappy.put("hello", 1);
		mappy.put("bye", 2);
		System.out.println(mappy.get("hello"));
		*/
		
	}
	

}
