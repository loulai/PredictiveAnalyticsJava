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

	
	public static void main(String[] args) throws FileNotFoundException {
	
		File[] datasetNames = new File("./../DataSet").listFiles();
		File[][] dataset = new File[15][];
		
		//read files
		 for (int i = 0; i < datasetNames.length; i++) {
		      if (datasetNames[i].isDirectory()) {
		    	int cIndex = Integer.valueOf(datasetNames[i].getName().replace("C", "")) - 1;
		        //System.out.println("Directory: " + (cIndex + 1));
		        
		        File[] articleNames = datasetNames[i].listFiles();
		        dataset[cIndex] = new File[articleNames.length]; // e.g. dataset[0] = new File[8]
		        
		        for(int k = 0; k < articleNames.length; k++) {
			    	System.out.printf("\t%s\n", articleNames[k].getName());
			    	String articleName = articleNames[k].getName();
			    	dataset[cIndex][k] = new File("./../DataSet/C" + (cIndex+1) + "/" + articleName);
			      }
			    }
		      }
		 
		 //evaluate everything
		for (int i = 0; i < dataset.length; i++) {
			System.out.println("Corpus" + i);
			for(int k = 0; k < dataset[i].length; k++) {
				ArrayList<String> fileString = convertFileToArrayList(dataset[i][k]);
				NGram myNGram = new NGram(fileString);
				myNGram.addFrequencies(2);
				/* Grader: uncomment to see tables
				 * System.out.println("---------TABLE---------");
				 * myNGram.printNGram();
				 * System.out.println("-----------------------");
				 */
				
				myNGram.getConcurrent(3); //get words occuring more than twice together
			}
		}
	
		/*
		 for (int i = 0; i < dataset.length; i++) {
			 System.out.printf("C: %d\n", i);
			 for (int k = 0; k < dataset[i].length; k++ ) {
				 System.out.printf("\t%s\n", dataset[i][k]);
			 }
		 }
		 
		
		File[][] grandData = new File[15][];
		grandData[0] = new File[8];
		grandData[1] = new File[8];
		grandData[2] = new File[4];
		grandData[3] = new File[8];
		grandData[4] = new File[13];
		grandData[5] = new File[5];
		grandData[6] = new File[8];
		grandData[7] = new File[10];
		grandData[8] = new File[4];
		grandData[9] = new File[18];
		grandData[10] = new File[8];
		grandData[11] = new File[10];
		grandData[12] = new File[7];
		grandData[13] = new File[5];
		grandData[14] = new File[6];
		
		for (int i = 0; i < grandData.length; i++) {
			String corpusNum = "C" + (i+1) + "";
			for(int k = 0; k < grandData[i].length; k++) {
				String articleNum = "";
				if(k < 10) {
					articleNum = "article0" + (k+1) + ".txt";
				} else {
					articleNum = "article" + (k+1) + ".txt";
				}
				
				grandData[i][k] = new File("./../DataSet/" + corpusNum + "/" + articleNum);
			}
		}
	
		*/
		
		
	}
	

}
