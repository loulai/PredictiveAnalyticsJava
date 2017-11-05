import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
public class NGram extends Preprocessing{

	public int windowSize;
	public ArrayList<String> rows;
	public Map<String, ArrayList<Integer>> columnsMap;
	public int nRow;
	public int nCol;
	ArrayList<String> initialTextList;
	
	public NGram(ArrayList<String> textList) {
		initialTextList = textList;
		ArrayList<String> uniqueTextArray = toUnique(textList); //unique tokens 
		
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
		System.out.printf("%-14s ", ""); //padding for row
		for(String key:columnsMap.keySet()) {
			System.out.printf("%-14s ", key);
		}
		System.out.println();
		
		//rows
		for(int i = 0; i < nRow; i++) {
			System.out.printf("%-14s ", rows.get(i)); //current row (e.g. "the")
			for(String key:columnsMap.keySet()) { //iterates over all columns for that row
				System.out.printf("%-14d ", columnsMap.get(key).get(i)); 
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
		ArrayList<String[]> grandResults = new ArrayList<String[]>(); 
		
		for(int i = 0; i < nRow; i++) { 
			for(String key:this.columnsMap.keySet()) {
				String currentRow = this.rows.get(i); //"the"
				String currentColumn = key;           //"theme"
				int currentFreq = this.columnsMap.get(key).get(i); //"0"
				
				//only select words that appear together >= threshold value
				if(currentFreq >= threshold) {
					String[] oneResult = {currentRow, currentColumn, String.valueOf(currentFreq)};
					grandResults.add(oneResult);
				} 
			}
		}
		//printing
		for(int i = 0; i < grandResults.size(); i++) {
			String[] currentArray = grandResults.get(i);
			for(int k = 0; k < currentArray.length; k++) {
				System.out.printf("%-14s ", currentArray[k]); //"the "
			}
			System.out.println();
		}
		return grandResults;	
	}
	
	public void printKeyWords() {
		File[] c1 = new File[8];
		File[] c2 = new File[8];
		File[] c3 = new File[4];
		File[] c4 = new File[8];
		File[] c5 = new File[13];
		File[] c6 = new File[5];
		File[] c7 = new File[8];
		File[] c8 = new File[10];
		File[] c9 = new File[4];
		File[] c10 = new File[18];
		File[] c11 = new File[8];
		File[] c12 = new File[10];
		File[] c13 = new File[7];
		File[] c14 = new File[5];
		File[] c15 = new File[6];
		
		c1[0] = new File("./../DataSet/C1/article01.txt");
		corpus[1] = new File("./../DataSet/C1/article02.txt");
		ArrayList<String> bigTest = convertFileToArrayList(corpus[1]);
		NGram myNGram = new NGram(bigTest);
		myNGram.addFrequencies(2);
		System.out.println("---------TABLE---------");
		myNGram.printNGram();
		System.out.println("-----------------------");
		myNGram.getConcurrent(3);
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		//ArrayList<String> test = new ArrayList<String>(Arrays.asList("today we sent mail to the mail person but the mail truck is late".split(" ")));
		File[] corpus = new File[2];
		corpus[0] = new File("./../DataSet/C1/article01.txt");
		corpus[1] = new File("./../DataSet/C1/article02.txt");
		ArrayList<String> bigTest = convertFileToArrayList(corpus[1]);
		NGram myNGram = new NGram(bigTest);
		myNGram.addFrequencies(2);
		System.out.println("---------TABLE---------");
		myNGram.printNGram();
		System.out.println("-----------------------");
		myNGram.getConcurrent(3);
		
		// starts 
		File[][] allData = new File[15][];
		allData[0] = new File[8];
		allData[1] = new File[8];
		allData[2] = new File[4];
		allData[3] = new File[8];
		allData[4] = new File[13];
		allData[5] = new File[5];
		allData[6] = new File[8];
		allData[7] = new File[10];
		allData[8] = new File[4];
		allData[9] = new File[18];
		allData[10] = new File[8];
		allData[11] = new File[10];
		allData[12] = new File[7];
		allData[13] = new File[5];
		allData[14] = new File[6];
		
		for(int i = 0; i < allData.length; i++){
			String corpusNum = "C" + i;
			for(int k = 0; k<allData[i].length; k++){
				if(k < 10){
					String articleNum = "article0" + k + ".txt";
				}else{
					String articleNum = "article" + k + ".txt";
				}
			}
		}
		
		for(int i = 0; i<allData.length; i++){
			ArrayList<String> bigTest = convertFileToArrayList(corpus[1]);
			NGram myNGram = new NGram(bigTest);
			myNGram.addFrequencies(2);
			System.out.println("---------TABLE---------");
			myNGram.printNGram();
			System.out.println("-----------------------");
			myNGram.getConcurrent(3);
		}
			
		c1[0] = new File("./../DataSet/C1/article01.txt");
		corpus[1] = new File("./../DataSet/C1/article02.txt");
		ArrayList<String> bigTest = convertFileToArrayList(corpus[1]);
		NGram myNGram = new NGram(bigTest);
		myNGram.addFrequencies(2);
		System.out.println("---------TABLE---------");
		myNGram.printNGram();
		System.out.println("-----------------------");
		myNGram.getConcurrent(3);
		
		
	}
	

}
