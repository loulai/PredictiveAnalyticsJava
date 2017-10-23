import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.*; 

import edu.stanford.nlp.*;
import edu.stanford.nlp.coref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation; 


public class DescriptiveModeling {

	public static void main(String[] args) throws FileNotFoundException {
		String text = "";
		String stopwords = "";
		ArrayList<String> textList = null;
		String[] stopwordArray;
	
		// read text 
        File textFile = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/C1/article01.txt");
        Scanner textInput = new Scanner(textFile);
        while (textInput.hasNext()) { 	// read textFile one word at a time, adding spaces
            text += textInput.next().trim() + " ";
        }
 
        // read stopwords 
        File stopwordsFile = new File("/home/louiselai88gmail/Desktop/programming/pa/java/HW2/DataSet/stopwords.txt");
        Scanner stopwordsInput = new Scanner(stopwordsFile);
        while(stopwordsInput.hasNext()) {
        	stopwords += stopwordsInput.next().trim() + " ";
        }
       
       //remove all punctuation, create arrays
       textList = new ArrayList<String>(Arrays.asList(text.replaceAll("[^a-zA-Z ]", "").split(" ")));
       stopwordArray = stopwords.split(" ");
        
       //loop through each word in text document
       int numWordsInText = textList.size();
        for(int i = 0; i < textList.size(); i++) {
       
        	//compare each text word with stopword. 
        	for(int k = 0; k < stopwordArray.length; k++) {
        		//if word is a stopword, remove from list
        		if((textList.get(i) + " ").equals(stopwordArray[k] + " ")) {
        			textList.remove(i);
        		}
        	}
        }
            
		String textString = String.join(" ", textList.toArray(new String[0])); // the string you want; 
		Properties props = new Properties(); 
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner"); 
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        pipeline = new StanfordCoreNLP(props, false);
    
        ArrayList<String> modifiedTextList = new ArrayList<String>();
        Annotation document = pipeline.process(text);  

        for(CoreMap sentence: document.get(SentencesAnnotation.class))
        {    
            for(CoreLabel token: sentence.get(TokensAnnotation.class))
            {       
                String word = token.get(TextAnnotation.class).toLowerCase();      
                String lemma = token.get(LemmaAnnotation.class).toLowerCase(); 
                String ne = token.get(NamedEntityTagAnnotation.class);
                //System.out.printf("[%8s]\n\t%11s   %s\n", word, lemma, ne);
                modifiedTextList.add(lemma);
            }
        }
        System.out.println(modifiedTextList);
        
 		//test NGram
 		NGram testNGram = new NGram(modifiedTextList);
 		testNGram.printNGram();
 		testNGram.addFrequencies(2);
 		testNGram.getConcurrent(2);
    }
}

