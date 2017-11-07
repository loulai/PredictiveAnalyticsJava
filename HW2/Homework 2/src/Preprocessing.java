import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Scanner;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.logging.RedwoodConfiguration;

public class Preprocessing {

	public static void main(String[] args) throws FileNotFoundException {
		File testFile = new File("./../DataSet/C1/article01.txt");
		ArrayList<String> test = convertFileToArrayList(testFile);
		System.out.println(test);
	}
	
	public static ArrayList<String> convertFileToArrayList(File file) throws FileNotFoundException  { //calls preprocessing method
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
	
	public static ArrayList<String> preprocess(String textString) throws FileNotFoundException{
		ArrayList<String> finalArrayList = new ArrayList<String>();
		String[] textArray;
		
		textArray = textString.toLowerCase().split("[-!~,.():\"\\s]+"); // lower case & punctuation 
		
		// stopwords
		String stopwords = "";
		String[] stopwordArray;
        File stopwordsFile = new File("./../DataSet/stopwords.txt");
        Scanner stopwordsInput = new Scanner(stopwordsFile);
        while(stopwordsInput.hasNext()) {
        	stopwords += stopwordsInput.next().trim() + " ";
        }
        stopwordArray = stopwords.split(" ");
        
		for(int i = 0; i < textArray.length; i++) {
			boolean isStopword = false;
			for(int k = 0; k < stopwordArray.length; k++) { //check is stopword
				if((textArray[i] + " ").equals(stopwordArray[k] + " ")) {
		 			isStopword = true;
		 			break;
		 		}
		 	}
			if(!isStopword) { //keep non-stopwords 
				finalArrayList.add(textArray[i]);
			}
		 }
		
		/*
		// lemmatization
		String text = String.join(" ", finalArrayList.toArray(new String[0])); // the string you want; 
		
		Properties props = new Properties(); 
        props.setProperty("annotators","tokenize, ssplit, pos, lemma, ner"); 
        RedwoodConfiguration.current().clear().apply(); //suppress warnings
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        pipeline = new StanfordCoreNLP(props, false);
    
        finalArrayList.clear();
        Annotation document = pipeline.process(text); 

        for(CoreMap sentence: document.get(SentencesAnnotation.class))
        {    
            for(CoreLabel token: sentence.get(TokensAnnotation.class))
            {               
                String lemma = token.get(LemmaAnnotation.class).toLowerCase(); 
                /* Grader: Can comment out to see that Named Entity Recognition and Tokenization has been implemented
                 * String token = token.get(TextAnnotation.class).toLowerCase(); 
                 * String ne = token.get(NamedEntityTagAnnotation.class).toLowerCase();
                 * System.out.printf("word: %12s NER:%12s\n", token, ne);
                 */ /*
                finalArrayList.add(lemma);
            }
        }
		*/
		
		//finalArrayList = new ArrayList<String>(Arrays.asList(textArray)); //convert String[] to ArrayList<String>. Keep in case don't want to remove stopwords
		return finalArrayList;
	}
	
	public static ArrayList<String> toUnique(ArrayList<String> arrayList){
		ArrayList<String> uniqueArrayList;
		uniqueArrayList = new ArrayList<String>(new LinkedHashSet<String>(arrayList)); //unique tokens 
		return uniqueArrayList;
	}

}
