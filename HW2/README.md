**Louise Y. Lai***

***N12709809 ll2663***

***Predictive Analytics**

**Submission 1, Oct 22 2017**

# Homework 2, Descriptive Modeling
Parent class: Preprocessing.java

Children    : NGram.java, TFIDF.java

Independent : DescriptiveModeling.java

# Class Desciptions

### Preprocessing.java
This is the parent class because it contains preprocessing methods that can be inhereted by N-grams and TFIDF.

Functions: convert file to ArrayList, punctuation removal, extract unique terms.

### DescrpitiveModeling.java
Implements tokenization, lemmatization, Named Entity Tagging, and removes stopwords using the Stanford NLP Library.

### NGram.java
An NGram object is a matrix that shows which words appear together most often. The data structure of an NGRam object is a 2 dimension ListArray that has columns as a hashMap, with the token as the key and an int array as the frequency that those two words corral.

Output interpretation: To read the array, first look at the row. For example, "the". Then look at a column, e.g. "mail". The corresponding value is 2, meaning that "the mail" occurs twice in our document.

Functions: calculates and prints NGram matrix, retrieves NGram given desired threshold

### TFIDF.java
TFIDF employs a similar data structire as an NGram object. The values are the TF-IDF values of a given term (row) in a given document (column) against the entire corpus of documents (all rows).

Functions: calculates and prints TFIDF matrix

# To-do Log
1. DescriptiveModeling/Preprocessing: move lemmatization to be in Preprocessing instead of DescriptiveModeling.
2. Preprocessing: improve punctuation removal. Currenly ignoring apostrophes.
3. NGram: instead of just bigram, expand to tri- to n- grams in function addFrequencies.
4. NGram: intead of indicating threshold, indicate 'top 3' (or similar) in function getConcurrent.
5. Word to Vec: Task has yet to be completed.

# Nice-to-haves
1. Implement Viterbi probabilites based on part-of-speech tagging learnt in my NLP class to give better predictions
