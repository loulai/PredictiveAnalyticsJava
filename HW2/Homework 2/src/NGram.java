import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
public class NGram {

	public static void main(String[] args) {
		System.out.println("Hel-fucking-lo");
	}
	
	public NGram(ArrayList<String> textList, int n) {
		
		ArrayList<String> uniqueTextArray = new ArrayList<String>(new LinkedHashSet<String>(textList)); //unique tokens 
		ArrayList<String> rows = uniqueTextArray;
		ArrayList <String> columns;
		
		// create a column for each unique token
		for(int i=0; i< uniqueTextArray.size();i++) {
			Map<String, ArrayList<Integer>> col = new HashMap<String, ArrayList<Integer>>(); //initializing empty hash with key=String, value=ArrayList
			col.put(uniqueTextArray.get(i), (ArrayList<Integer>) col);
		}
	}

}
