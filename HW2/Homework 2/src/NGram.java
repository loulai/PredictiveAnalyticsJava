import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
public class NGram {

	public static void main(String[] args) {
		
		System.out.println("Hel-fucking-lo");
		ArrayList<String> test = new ArrayList<String>(Arrays.asList("the theme theme".split(" ")));
		NGram myNGram = new NGram(test, 2);
		myNGram.printNGram();
		/*
		Map<String, Integer> mappy = new HashMap<>();
		mappy.put("hello", 1);
		mappy.put("bye", 2);
		System.out.println(mappy.get("hello"));
		*/
		
	}
	

	public int windowSize;
	public ArrayList<String> rows;
	public Map<String, ArrayList<Integer>> columnsMap;
	public int nRow;
	public int nCol;
	
	public NGram(ArrayList<String> textList, int n) {
		ArrayList<String> uniqueTextArray = new ArrayList<String>(new LinkedHashSet<String>(textList)); //unique tokens 
		windowSize = n;
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
			System.out.printf("%-10s ", rows.get(i));
			
			for(String key:columnsMap.keySet()) {
				System.out.printf("%-10d ", columnsMap.get(key).get(i));
			}
			System.out.println();
		}
		
	
		
	}

}
