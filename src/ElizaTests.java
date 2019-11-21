//
//TODO  file header comment////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title:           Eliza Tests
//Files:           Eliza.java, Eliza Tests.java, Config.java
//Course:          CS 200 Fall 2018
//
//Author:           Yeochan Youn
//Email:            yyoun5@wisc.edu
//Lecturer's Name:  Marc Renault 
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//NO help from other person
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This class contains a few methods for testing methods in the Eliza
 * class as they are developed. These methods are private since they are only
 * intended for use within this class.
 * 
 * @author Jim Williams
 * @author TODO add your name here when you add tests and comment the tests
 *
 */
public class ElizaTests {
	
    /**
     * This is the main method that runs the various tests. Uncomment the tests
     * when you are ready for them to run.
     * 
     * @param args  (unused)
     */
	public static void main(String[] args) {

		//Milestone 1: Process User Input
		//M1: main	nothing to do	
		testSeparatePhrases();  
		testFoundQuitWord();    
		testSelectPhrase();     
		testReplaceWord();      
		testAssemblePhrase();   

		//Milestone 2: 
		//M2: implement parts of main as described in main method comments
		testSwapWords();
		testPrepareInput();
	    testLoadResponseTable();
		
		//Milestone 3: 
		//main: implement the rest of main as described in the main method comments
		testFindKeyWordsInPhrase();  
		testSelectResponse();      
		testInputAndResponse();     
		testSaveDialog();			
	}
	/**
	 * This runs some tests on the separatePhrases method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */	
	private static void testSeparatePhrases() {
		boolean error = false;

		// 1.
		ArrayList<String> phrases = Eliza.separatePhrases("Hi,  I  am! a big-fun robot!!!"); // test string
		ArrayList<String> expected = new ArrayList<>();
		expected.add("hi");
		expected.add("i am");
		expected.add("a big fun robot"); // add correctly separated answers to the list

		if (phrases.size() != expected.size()) { // if number of words in returned list doesnt match the size of expected list
			error = true;
			System.out.println(
					"testSeparatePhrases: expected " + expected.size() + " but found " + phrases.size() + " phrases.");
		}

		// 2.
		for (int i = 0; i < expected.size(); i++) {
			if (!expected.get(i).equals(phrases.get(i))) { // if words are not separated properly, print error messages
				error = true;
				System.out.println("testSeparatePhrases: phrases not the same");
				System.out.println("  " + expected.get(i)); // print how the phrases should be
				System.out.println("  " + phrases.get(i)); // print what separatePhrase return
			}
		}
		
		// additional test
		
		// 1.
		ArrayList<String> phrases2 = Eliza.separatePhrases("What? This isn't the 4th messy-sentence!"); // test string
		ArrayList<String> expected2 = new ArrayList<>();
		expected2.add("what");
		expected2.add("this isn't the 4th messy sentence"); // add correctly separated answers to the list

		if (phrases2.size() != expected2.size()) { // if number of words in 
			error = true;
			System.out.println(
					"testSeparatePhrases: expected " + expected2.size() + " but found " + phrases2.size() + " phrases.");
		}

		// 2.
		for (int i = 0; i < expected2.size(); i++) {
			if (!expected2.get(i).equals(phrases2.get(i))) { // if words are not separated properly, print error messages
				error = true;
				System.out.println("testSeparatePhrases: phrases not the same");
				System.out.println("  " + expected.get(i)); // print how the phrases should be
				System.out.println("  " + phrases.get(i)); // print what separatePhrase return
			}
		}

		// Additional test suggestions
		// "What? This isn't the 4th messy-sentence!" should result in ?
		// "NO" should result in?
		// "this tab" should result in?
		// "What?" should result in?
		// "Thank you, but no, I have to go. Goodbye!!!" should result in?

		if (error) {
			System.out.println("testSeparatePhrases failed");
		} else {
			System.out.println("testSeparatePhrases passed");
		}
	}
	
	

	/**
	 * This runs some tests on the foundQuitWord method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */
	// TODO should QUIT_WORDS be embedded within foundQuitWord?
	private static void testFoundQuitWord() {
		boolean error = false;

		// 1.
		ArrayList<String> phrases = new ArrayList<>();
		phrases.add("thank you for talking");
		phrases.add("bye"); // create list that contains quit word

		boolean quit = Eliza.foundQuitWord(phrases); // store boolean to quit
		if (!quit) {
			error = true;
			System.out.println("testFoundQuitWord 1: failed"); // if method returns flase, it fails
		}
		
		// additional test "goodbye"
		ArrayList<String> phrases2 = new ArrayList<>();
		phrases2.add("goodbye"); // create list that contains quit word

		boolean quit2 = Eliza.foundQuitWord(phrases2); // store boolean to quit
		if (!quit2) {
			error = true;
			System.out.println("testFoundQuitWord 2: failed"); // if method returns flase, it fails
		}

		// additional test suggestions:
		// should foundQuitWord return true if bye is a part of a phrase rather than
		// separate?
		// should foundQuitWord return true if goodbye is the first or only phrase?
		// should foundQuitWord return true if there are no quit words in the phrases
		// list.

		if (error) {
			System.err.println("testFoundQuitWord failed");
		} else {
			System.out.println("testFoundQuitWord passed");
		}
	}

	/**
	 * This runs some tests on the selectPhrase method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */	
	private static void testSelectPhrase() {
		boolean error = false;

		// choose the longest
		ArrayList<String> phrases = new ArrayList<>();
		phrases.add("no");
		phrases.add("sometimes I remember being on a boat");
		phrases.add("not often"); // create list that contains different length of sentences
		String choice = Eliza.selectPhrase(phrases);
		if (!choice.equals("sometimes I remember being on a boat")) { // if it doesn't return longest one, if fails
			error = true;
			System.out.println("testSelectPhrase 1: failed");
		}
		

		// additional test suggestions:
		// What should happen when there are 2 choices of the same length?
		// What should happen if an empty list is passed to selectPhrase?
		//additional test same length
		ArrayList<String> phrases2 = new ArrayList<>();
		phrases2.add("no");
		phrases2.add("on"); // create list that contains same length of sentences
		String choice2 = Eliza.selectPhrase(phrases2);
		if (!choice2.equals("no")) { // if same length strings are passed, choose the one in the smaller index
			error = true;
			System.out.println("testSelectPhrase 2: failed");
		}

		if (error) {
			System.err.println("testSelectPhrase failed");
		} else {
			System.out.println("testSelectPhrase passed");
		}
	}

	/**
	 * This runs some tests on the assemblePhrase method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */
	private static void testAssemblePhrase() {
		boolean error = false;

		String[] words = { "This", "is a", "sentence" };
		String sentence = Eliza.assemblePhrase(words);
		String expectedSentence = "This is a sentence"; // concatenate the words in array and create a sentence 

		if (!sentence.equals(expectedSentence)) {
		    error = true;
			System.out.println("testAssembleSentence 1 failed '" + sentence + "'");
		}

		// additional test suggestions:
		// what should happen if an array with no strings in it is passed in?
		// what should happen if just a list of words, with no internal spaces, is
		// passed in?
		
		String[] words2 = { }; // array with no strings
		String sentence2 = Eliza.assemblePhrase(words2);
		String expectedSentence2 = ""; // returns empty string

		if (!sentence2.equals(expectedSentence2)) {
		    error = true;
			System.out.println("testAssembleSentence 1 failed '" + sentence2 + "'");
		}
		


		if (error) {
			System.err.println("testAssemblePhrase failed");
		} else {
			System.out.println("testAssemblePhrase passed");
		}
	}

	/**
	 * This runs some tests on the findKeyWordsInPhrase method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */
	private static void testFindKeyWordsInPhrase() {
		boolean error = false;

		{// block so each test has its own variable scope.
			// 1.
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add("computer"); // add keyword to list
			String[] phrase = { "are", "you", "a", "computer" }; // pass array that contains keyword

			String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);
			if (matches == null || matches.length != 2 || !matches[0].equals("are you a") || !matches[1].equals("")) { // if method doesn't get the keyword or length is not 2 or each index do not match with correct, it fails
				error = true;
				System.out.println("testFindKeyWordsInPhrase 1 failed.");
				System.out.println(Arrays.toString(matches)); // print wrong array
			}
		}

		{
			// 2.
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add("computer");
			String[] phrase = { "computer", "are", "you" }; // keyword at first

			String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);
			if (matches == null || matches.length != 2 || !matches[0].equals("") || !matches[1].equals("are you")) {
				error = true;
				System.out.println("testFindKeyWordsInPhrase 2 failed.");
				System.out.println(Arrays.toString(matches));
			}
		}

		{
			// 3.
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add("computer");
			String[] phrase = { "does", "that", "computer", "on", "your", "desk", "work" }; //keyword in the middle
			String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);  
			if (matches == null || matches.length != 2 || !matches[0].equals("does that")
					|| !matches[1].equals("on your desk work")) {
				error = true;
				System.out.println("testFindKeyWordsInPhrase 3 failed.");
				System.out.println(Arrays.toString(matches));
			}
		}

		{
			// 4.
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add("you");			
			keywords.add("me");			
			String[] phrase = { "why", "don't", "you", "like", "me" }; // contains multiple keywords
			String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);
			if (matches == null || matches.length != 3 || !matches[0].equals("why don't") || !matches[1].equals("like")
					|| !matches[2].equals("")) {
				error = true;
				System.out.println("testFindKeyWordsInPhrase 4 failed.");
				System.out.println(Arrays.toString(matches));
			}
		}

		{
			// 5.
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add("you");			
			keywords.add("me");				
			String[] sentence = { "me", "don't", "like", "you" }; // keywords in wrong order
			String[] matches = Eliza.findKeyWordsInPhrase(keywords, sentence);
			if (matches != null) {
				error = true;
				System.out.println("testFindKeyWordsInPhrase 5 failed.");
				System.out.println(Arrays.toString(matches));
			}
		}

		// additional tests?
		
		{// block so each test has its own variable scope.
			// 6.
			ArrayList<String> keywords = new ArrayList<String>();
			keywords.add("bee"); // add keyword to list
			String[] phrase = { "i", "like", "bee"}; // pass array that contains keyword

			String[] matches = Eliza.findKeyWordsInPhrase(keywords, phrase);
			if (matches == null || matches.length != 2 || !matches[0].equals("i like") || !matches[1].equals("")) { // if method doesn't get the keyword or length is not 2 or each index do not match with correct, it fails
				error = true;
				System.out.println("testFindKeyWordsInPhrase 1 failed.");
				System.out.println(Arrays.toString(matches)); // print wrong array
			}
		}

		if (error) {
			System.err.println("testFindKeyWordsInPhrase failed");
		} else {
			System.out.println("testFindKeyWordsInPhrase passed");
		}
	}
	
	/**
	 * This runs some tests on the saveDialog method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */	
	private static void testSaveDialog()  {
		boolean error = false;
		final String TEST_FILENAME = "testing.txt"; // testfilename "testing.txt"
		{ // 1.
			ArrayList<String> list = new ArrayList<>();
			list.add("this is a single line."); // add sentence to test list
			try {
				Eliza.saveDialog( list, TEST_FILENAME); // save file as test file name and write list in the file 
				String readFromFile = readFile( TEST_FILENAME); // read the saved file
				if ( !readFromFile.equals(list.get(0) + "\n")) { // if sentence read from saved file doesnt match the list, it fails
					error = true;
					System.out.println("testSaveDialog 1 failed.");
					System.out.println("content read: " + readFromFile);
				}
				removeFile( TEST_FILENAME); // remove file after test
			} catch (IOException e) {
				e.printStackTrace(); // catch IOException
			}
			
		}
		
		// 2. test multiple lines of output written to the file.
		// additional tests?
		{ // 1.
			ArrayList<String> list = new ArrayList<>();
			list.add("this is a single line."); // test multiple lines
			list.add("this is a second line.");
			list.add("third");
			String list2 = "";
			try {
				Eliza.saveDialog( list, TEST_FILENAME); // save file as test file name and write list in the file 
				String readFromFile = readFile( TEST_FILENAME); // read the saved file
				for(int i = 0; i < list.size(); i++) {
					list2 += list.get(i) + "\n"; // all the lines from list
				}
				
				if ( !readFromFile.equals(list2)) { // if sentence read from saved file doesnt match the list, it fails
					error = true;
					System.out.println("testSaveDialog 1 failed.");
					System.out.println("content read: " + readFromFile);
				}
				
				removeFile( TEST_FILENAME); // remove file after test
			} catch (IOException e) {
				e.printStackTrace(); // catch IOException
			}
			
		}

		if (error) {
			System.err.println("testSaveDialog failed");
		} else {
			System.out.println("testSaveDialog passed");
		}
	}
	
	/**
	 * Supporting method for testSaveDialog
	 * @param fileName name of the file to read
	 * @return the contents of the file
	 */
	private static String readFile(String fileName) {
		StringBuffer buf = new StringBuffer();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
			String line;
			while ((line = reader.readLine()) != null) {
				buf.append(line);
				buf.append("\n");
			}
			return buf.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Supporting method for testSaveDialog.
	 * 
	 * @param filename  file to be removed.
	 */
	private static void removeFile(String filename) {
		File file = new File(filename);
		try {
			if (file.exists())
				file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This runs some tests on the replaceword method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */	
	private static void testReplaceWord()  {
		boolean error = false;
		
		{    //1. 
			String word = "machine"; // original word
			String expected = "computer"; // the word should be like after method
			String result = Eliza.replaceWord(word, Config.INPUT_WORD_MAP); // replace word using Config.INPUT_WORD_MAP
			if (result == null || !result.equals(expected)) {
				error = true;
				System.out.println("testReplaceWord 1 failed. result:'" + result + "' expected:'" + expected + "'");
			}
		}	
		
		{    //2. 
			String word = "yourself";
			String expected = "myself";
			String result = Eliza.replaceWord(word, Config.PRONOUN_MAP); // replace word using Config.PRONOUN_MAP
			if (result == null || !result.equals(expected)) {
				error = true;
				System.out.println("testReplaceWord 2 failed. result:'" + result + "' expected:'" + expected + "'");
			}
		}
		
		//additional suggestions:
		//Do these tests meet all the requirements described in the replaceWord header comment,
		//such as handling a null wordMap?
		
		if (error) {
			System.err.println("testReplaceWord failed");
		} else {
			System.out.println("testReplaceWord passed");
		}
	}
	
	/**
	 * This runs some tests on the swapWords method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */	
	private static void testSwapWords()  {
		boolean error = false;
		
		{    //1. 
			String someWords = "i'm cant recollect";
			String expected = "i am cannot remember"; // ' should be swapped and cant -> cannot and words in word map should also be swapped
			String result = Eliza.swapWords(someWords, Config.INPUT_WORD_MAP);
			if (result == null || !result.equals(expected)) {
				error = true;
				System.out.println("testSwapWords 1 failed. result:'" + result + "' expected:'" + expected + "'");
			}
		}		

		{    //2. 
			String someWords = "i'm happy";
			String expected = "you are happy"; // pronoun i should be you 
			String result = Eliza.swapWords(someWords, Config.PRONOUN_MAP);
			if (result == null || !result.equals(expected)) {
				error = true;
				System.out.println("testSwapWords 2 failed. result:'" + result + "' expected:'" + expected + "'");
			}
		}
		
		{    //3. 
			String someWords = "about my dog";
			String expected = "about your dog"; // my -> your
			String result = Eliza.swapWords(someWords, Config.PRONOUN_MAP);
			if (result == null || !result.equals(expected)) {
				error = true;
				System.out.println("testSwapWords 3 failed. result:'" + result + "' expected:'" + expected + "'");
			}
		}
		
		//additional tests?
		
		if (error) {
			System.err.println("testSwapWords failed");
		} else {
			System.out.println("testSwapWords passed");
		}		
	}	

	/**
	 * This runs some tests on the selectResponse method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */	
	private static void testSelectResponse() {
		boolean error = false;
		
		{  //1.
			// is one of the 3 strings chosen?
			Random randGen = new Random(434);
			ArrayList<String> strList = new ArrayList<>(); // test answer list
			strList.add("The");
			strList.add("happy");
			strList.add("cat");			
			String choice = Eliza.selectResponse(randGen, strList);

			if (!(choice.equals("The") || choice.equals("happy") || choice.equals("cat"))) { // random answer should be in test answer list
				error = true;
				System.out.println("testSelectResponse 1 failed.");
			}
		}
		
		{ // 2.
			// if called 1000 times, are the choices approximately random?
			Random randGen = new Random(765);
			ArrayList<String> strList = new ArrayList<>();
			strList.add("this");
			strList.add("is");
			strList.add("a");
			strList.add("list");
			strList.add("to");
			strList.add("choose");
			strList.add("from");
			int [] actualCount = new int[strList.size()];
			int [] expectedCount = new int[] {156, 146, 142, 138, 160, 130, 128};
			for (int iterationIndex = 0; iterationIndex < 1000; iterationIndex++) {
				String choice = Eliza.selectResponse(randGen, strList);
				for ( int wordIndex = 0; wordIndex < strList.size(); wordIndex++) {
					if ( choice.equals(strList.get(wordIndex))) {
						actualCount[wordIndex]++;
					}
				}
			}
			//since we set a seed on the random number generator we should know the expected 
			//outcome
			for ( int countIndex = 0; countIndex < actualCount.length; countIndex++) {
				if ( actualCount[countIndex] != expectedCount[countIndex]) {
					error = true;
					System.out.println("testSelectResponse 2 failed.");
					System.out.println("  expectedCount: " + Arrays.toString( expectedCount));
					System.out.println("  actualCount: " + Arrays.toString( actualCount));
				}
			}

		}
		
		//additional test suggestions: 
		//What should happen when a list a single string is provided? 
		//What should happen when null is passed to selectResponse?
		
		
		if (error) {
			System.err.println("testSelectResponse failed");
		} else {
			System.out.println("testSelectResponse passed");
		}			
	}	

	/**
	 * This runs some tests on the prepareInput method. 
	 * 1. TODO describe each test in your own words. 
	 * 2.
	 */		
	private static void testPrepareInput() {
		boolean error = false;
		
		{ // 1.
			String input = "bye";
			String[] result = null;
			result = Eliza.prepareInput("bye"); // if quit word is passed, result should be null
			if (result != null) {
				error = true;
				System.out.println("testPrepareInput 1: failed");
				System.out.println("  input: " + input);
				System.out.println("  result: " + Arrays.toString(result));
			}
		}
		
		//additional test suggestions
		
		{ // 1.
			String input = "I said goodbye";
			String [] expected = {"i", "said", "goodbye"};
			String[] result = null;
			result = Eliza.prepareInput("I said goodbye"); // if "I said goodbye" is passed, result "i", "said", "goodbye"
			if (result.equals(expected)) {
				error = true;
				System.out.println("testPrepareInput 1: failed");
				System.out.println("  input: " + input);
				System.out.println("  result: " + Arrays.toString(result));
			}
		}
		//"I said goodbye" should result in "i", "said", "goodbye"
		//"I can't do that" should result in "i", "cannot", "do", "that"
		
		
		if (error) {
			System.err.println("testPrepareInput failed");
		} else {
			System.out.println("testPrepareInput passed");
		}	
	}
	
    /**
     * This runs some tests on the loadResponseTable method. 
     * 1. TODO describe each test in your own words. 
     * 2.
     */     
    private static void testLoadResponseTable() {
        boolean error = false;
        
        { // 1.
            ArrayList<String> expected1stRow = new ArrayList<String>();
            expected1stRow.add("computer"); // add new keyword to list
            ArrayList<ArrayList<String>> table = Eliza.loadResponseTable("Eliza.rsp");
            if (!table.get(0).equals( expected1stRow)) {
                error = true;
                System.out.println("testLoadResponseTable 1: failed");
                System.out.println("  expected1stRow: " + expected1stRow);
                System.out.println("  table1stRow: " +table.get(0));
            }
            
            // 2.
            if ( table.size() % 2 != 0) { // table should be paired. keyword and response
                error = true;
                System.out.println("testLoadResponseTable 2: failed");
                System.out.println("  expected an even number of elements in table but found: " + table.size());
            }
        }
        
        //additinal test
        
        { // 1.
            ArrayList<String> expected1stRow = new ArrayList<String>();
            expected1stRow.add("name"); // add new keyword to list
            ArrayList<ArrayList<String>> table = Eliza.loadResponseTable("Eliza.rsp");
            if (!table.get(2).equals( expected1stRow)) {
                error = true;
                System.out.println("testLoadResponseTable 1: failed");
                System.out.println("  expected1stRow: " + expected1stRow);
                System.out.println("  table1stRow: " +table.get(2));
            }
            
            // 2.
            if ( table.size() % 2 != 0) { // table should be paired. keyword and response
                error = true;
                System.out.println("testLoadResponseTable 2: failed");
                System.out.println("  expected an even number of elements in table but found: " + table.size());
            }
        }
        
        //additional test suggestions
        //Are the right number of keywords and responses read in for a keyword/response pair?
        //Are blank lines (containing no spaces or only whitespace) ignored?
        //Are the last rows of the file read in?
        //Are the right number of rows read in?
        
        if (error) {
            System.err.println("testLoadResponseTable failed");
        } else {
            System.out.println("testLoadResponseTable passed");
        }   
    }	
	
	/*
	 * Supporting method for testInputAndResponse.
	 * Returns 1 if the test passed and 0 if the test failed.
	 */
	private static int checkResponse(String input, String expectedResponse, Random rand, ArrayList<ArrayList<String>> table) {
		
		String [] words = Eliza.prepareInput(input);
		if ( words == null) {
			if ( expectedResponse == null) {
				return 1;
			} else {
				System.out.println("testInputLines  checkResponse error");
				System.out.println("  input='" + input + "'");
				System.out.println("  response=null");
				System.out.println("  expected='" + expectedResponse + "'");
				return 0;
			}
		}
		
		String response = Eliza.prepareResponse(words, rand, table);
		if ( !response.equals( expectedResponse)) {
			System.out.println("testPrepareResponse  checkResponse error");
			System.out.println("  input='" + input + "'");
			System.out.println("  response='" + response +"'");
			System.out.println("  expected='" + expectedResponse + "'");
			return 0;
		} else {
			return 1;
		}
	}
	
	/*
	 * Runs tests on a bunch of example sentences using a Random number generator with a seed.
	 */
	private static void testInputAndResponse() {
	    int numPassed = 0;
		//since we are using a seed in the random number generator, the responses should be fixed for the provided response table.
		Random randGen = new Random( 923);
        ArrayList<ArrayList<String>> responseTable = Eliza.loadResponseTable("Eliza" + Config.RESPONSE_FILE_EXTENSION);

        numPassed += checkResponse("I like computers.", "What do you think machines have to do with your problem?", randGen, responseTable);
        
        numPassed += checkResponse("What is your name?", "I don't care about names -- please continue.", randGen, responseTable);
        numPassed += checkResponse("You remind me of a grumpy uncle.", "What other connections do you see?", randGen, responseTable);
        numPassed += checkResponse("You don't seem to say much.", "What are your feelings now?", randGen, responseTable);
        numPassed += checkResponse("You reflect me.", "What makes you think I reflect you?", randGen, responseTable);
        numPassed += checkResponse("I wish to believe you.", "Why do you think you wish to believe me?", randGen, responseTable);
        numPassed += checkResponse("I dreamed of going into space.", "Have you ever dreamed of going into space before?", randGen, responseTable);
        numPassed += checkResponse("I apologize for boring you.", "Why do you think you apologize for boring me?", randGen, responseTable);
        numPassed += checkResponse("Because it seems the polite thing to do.", "Does that reason seem to explain anything else?", randGen, responseTable);
        numPassed += checkResponse("Yes.", "You are sure.", randGen, responseTable);
        numPassed += checkResponse("I remember being in 1st grade.", "What is the connection between me and being in 1st grade?", randGen, responseTable);
        numPassed += checkResponse("No, not often. Sometimes I remember being on a boat.", "Why do you recollect being on a boat just now?", randGen, responseTable);
        numPassed += checkResponse("My family liked to play cards.", "Tell me more about your family.", randGen, responseTable);
        numPassed += checkResponse("Do you remember growing up?", "What about growing up?", randGen, responseTable);
        numPassed += checkResponse("Are you a Packers fan?", "Perhaps I am a packers fan in your fantasies.", randGen, responseTable);
        numPassed += checkResponse("I am sad to hear that.", "Can you explain what made you sad?", randGen, responseTable);
        numPassed += checkResponse("I cannot explain this.", "Do you really want to be able to explain this?", randGen, responseTable);
        numPassed += checkResponse("You seem to have a different perspective than many.", "You're not really talking about me -- are you?", randGen, responseTable);
        numPassed += checkResponse("I'm talking to my dog.", "How long have you been talking to your dog?", randGen, responseTable);
        numPassed += checkResponse("goodbye", null, randGen, responseTable);
		
        numPassed += checkResponse("", "I'm not sure I understand you fully.", randGen, responseTable);
        
        int expected = 21;
        if (numPassed == expected) {
            System.out.println("testInputAndResponse passed ");
        } else {
            System.err.println("testInputAndResponse failed " + (expected - numPassed));
        }   
	}
	
}
