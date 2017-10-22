import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class TFIDF extends Preprocessing {

	public static void main(String[] args) {
		File targetFile = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/testtext.txt");

		ArrayList<String> targetArrayList = convertFileToArrayList(targetFile);
		//targetArrayList = toUnique(targetArrayList);
		System.out.println(targetArrayList);
		
	}

}
