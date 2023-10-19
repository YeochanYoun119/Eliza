// TODO file header
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *  The Eliza class holds the user input and response formation for a system
 *  that collects user input and responds appropriately. Eliza is based off of
 *  a computer program written at MIT in the 1960's by Joseph Weizenbaum. Eliza
 *  uses keyword matching to respond to users in a way that displays interest
 *  in the users and continues the conversation until instructed otherwise.
 */
public class Eliza {

   /*
    * This method does input and output with the user. It calls supporting methods
    * to read and write files and process each user input.
    *  
    * @param args (unused)
    */
	public static void main(String[] args) {
        //Milestone 2
        //create a scanner for reading user input and a random number
        //generator with Config.SEED as the seed
		Scanner sc = new Scanner(System.in);
		Random rand = new Random(Config.SEED);

        


        //Milestone 3
        //How the program starts depends on the command-line arguments.
        // Command-line arguments can be names of therapists for example:
        //   Eliza Joe Laura
        // If no command-line arguments then the therapists name is Eliza
        // and this reads a file with that name and the Config.RESPONSE_FILE_EXTENSION.
        // Example filename: Eliza.rsp
        // If only one command-line argument, then read the responses from 
        // the corresponding file with Config.RESPONSE_FILE_EXTENSION. 
        // If there is more than one command-line argument then offer them
        // as a list of people to talk with. For the three therapists above the prompt is
        //   "Would you like to speak with Eliza, Joe, or Laura?"
        // When a user types a name then read the responses from the file which
        // is the selected name and Config.RESPONSE_FILE_EXTENSION extension.
        // Whatever name the user types has the extension appended and
        // is read using loadResponseTable. If loadResponseTable can't load
        // the file then it will report an error.


        //Milestone 2
        //name prompt
		String name = "";
		if (args.length == 0) {
			name = "Eliza";  // default name "Eliza"
		}else if(args.length == 1){
		name = args[0]; // if setting another name 
		}else {
			System.out.print("Would you like to speak with ");
			for (int i=0; i <= args.length -2; i++) {  // if there are more than one name
				System.out.print(args[i] + ", ");
			}
			System.out.print("or " + args[args.length -1]);
			System.out.println("?");
			name = sc.nextLine(); // set name to that user chose
			name = name.trim();			
       }
        ArrayList<ArrayList<String>> responseTable = null;        
        responseTable = loadResponseTable(name + Config.RESPONSE_FILE_EXTENSION); // load response table with chosen name
        
        // name prompt
        ArrayList<String> dialog = new ArrayList<>();
        String promp = "";
        promp = "Hi I'm "+ name +", what is your name?";
        System.out.println(promp); 
        dialog.add(promp); // add every line of conversation to dialog 
        String user = sc.nextLine(); // get user name
        dialog.add(user); 
		
        
        
        //Milestone 2
        //welcome prompt
        promp = "Nice to meet you "+ user + ". What is on your mind?";
        System.out.println(promp);
        dialog.add(promp);

        
        //Milestone 2
        //begin conversation loop
        while(true) {

            //Milestone 2
            //obtain user input
        	String input = sc.nextLine();
        	dialog.add(input);


            //Milestone 2
            //prepareInput
        	String[] prepInput = prepareInput(input);

                //Milestone 3
                //if no quit words then prepareResponse
        	if(prepInput != null) {
        		String response = prepareResponse(prepInput, rand, responseTable);
        		System.out.println(response);
        		dialog.add(response);      		
        	}
        	
        	//Milestone 2
        	//end loop if quit word
        	else {
        		break;
        	}
        }

        //Milestone 2
        //ending prompt
        	promp = "Goodbye " + user + "."; // after user finishes the conversation, print Goodbye prompt
        	System.out.println(promp);
        	dialog.add(promp);
     
        //Milestone 3
        //Save all conversation (user and system responses) starting
        //with this program saying "Hi I'm..." and concludes with
        //"Goodbye <name>.".
        //Always prompt the user to see if they would like to save a 
        //record of the conversation.  If the user enters a y or Y as the 
        //first non-whitespace character then prompt for filename and save, 
        //otherwise don't save dialog.  After successfully saving a dialog 
        //print the message "Thanks again for talking! Our conversation is saved in: <filename>".
        //If saveDialog throws an IOException then catch it, print out the error:
        //  "Unable to save conversation to: " <name of file> 
        //Repeat the code prompting the user if they want to save the dialog.

        
        while (true) { // if the user ends the conversation, ask file saving
            System.out.println("Would you like to save conversation? ");
            String saveFile = sc.nextLine();

            if (saveFile.charAt(0) == 'y' || saveFile.charAt(0) == 'Y') { // if user enters string starts with 'y'  or 'Y'
                System.out.println("Enter filename: ");
                String filename = sc.nextLine();
                try {
                    saveDialog(dialog, filename);
                    System.out.println("Thanks again for talking! Our conversation is saved in: " + filename);
                    break;                 
                } catch (IOException e) {
                    System.out.println("Unable to save conversation to: " + filename); // if IOException occurs, print message
                } 
            } else {
                break; // if user entered sting that does not start with 'y' or 'Y'
            }
        }        
    }
	    
	
	   /**
     * This method processes the user input, returning an ArrayList containing Strings,
     * where each String is a phrase from the user's input. This is done by removing leading
     * and trailing whitespace, making the user's input all lower case, then going through 
     * each character of the user's input. When going through each character this
     * keeps all digits, alphabetic characters and ' (single quote). The characters ? ! , . 
     * signal the end of a phrase, and possibly the beginning of the next phrase,
     * but are not included in the result.
     * All other characters such as ( ) - " ] etc. should be replaced with a space. 
     * This method makes sure that every phrase has some visible characters but no
     * leading or trailing whitespace and only a single space between words of a phrase.
     * If userInput is null then return null, if no characters then return a
     * 0 length list, otherwise return a list of phrases.  Empty phrases and phrases
     * with just invalid/whitespace characters should NOT be added to the list.
     * 
     * Example userInput: "Hi,  I  am! a big-fun robot!!!"
     * Example returned: "hi", "i am", "a big fun robot"
     * 
     * @param userInput text the user typed
     * @return the phrases from the user's input
     */
    public static ArrayList<String> separatePhrases(String userInput) {
    			userInput = userInput.toLowerCase();
		ArrayList<String> phrases = new ArrayList<>();

		char[] endPhrase = { '?', '!', ',', '.' };  // char array of endPhrases
		char[] replaceSpace = { '(', ')', '-', '"' }; // char array of replaceChars

		int pos = 0;
		String inputArr;
		for (int i = 0; i < userInput.length(); ++i) {
			for (int j = 0; j < replaceSpace.length; ++j) {
				if (userInput.charAt(i) == replaceSpace[j]) {  // compare all the charAt userInput with char in replaceSpace array
					userInput = userInput.replace(userInput.charAt(i), ' '); // if charAt userInput and replace array match, replace charAt userInput to ' '
				}
			}
			for (int j = 0; j < endPhrase.length; ++j) { // compare all the charAt userInput with char in endPhrase array
				if (userInput.charAt(i) == endPhrase[j]) {
					inputArr = userInput.substring(pos, i); 
					inputArr = inputArr.replace(endPhrase[j], ' ');
					inputArr = inputArr.trim();  // if find endphrase in userInput substring it from pos and replace endphrase with ' ' and trim it 
					if (!inputArr.isEmpty()) {
						phrases.add(inputArr); // if there is something in inputArr, add it to phrases ArrayLIst
					}
					pos = i + 1; // after substring the userInput, add 1 to pos to make next substring does not overlap with previous substringed String
				}
			}
		}
        if(pos!=userInput.length()) {
            inputArr = userInput.substring(pos).trim(); // trim hte input before add to phrase
            phrases.add(inputArr);
        }

		for (int i = 0; i < phrases.size(); ++i) { // run through every phrases
			for (int j = 0; j < phrases.get(i).length(); ++j) { // run through every char in each phrases 
				if (phrases.get(i).charAt(j) == ' ' && phrases.get(i).charAt(j) == phrases.get(i).charAt(j + 1)) { 
					String nVal = phrases.get(i).substring(0, j) + phrases.get(i).substring(j + 1, phrases.get(i).length());
					phrases.set(i, nVal); 
				}
			}
		}

		if (userInput.equals(null)) {
			return null;
		} else if (userInput.isEmpty()) {
			return phrases;
		} else {
			return phrases;
		}
	}

    /**
     * Checks whether any of the phrases in the parameter match
     * a quit word from Config.QUIT_WORDS.  Note: complete phrases
     * are matched, not individual words within a phrase.
     * 
     * @param phrases List of user phrases
     * @return true if any phrase matches a quit word, otherwise false
     */
    public static boolean foundQuitWord(ArrayList<String> phrases) { 
    	for(int i = 0; i <= phrases.size()-1; i++) { // run through every phrases in param
    		for(int j = 0; j <= Config.QUIT_WORDS.length-1; j++) { 
    			if(phrases.get(i).equals(Config.QUIT_WORDS[j])) { // finding quit words in param
    				return true; // if find quitword
    			}
    		}
    	}
        return false; // if did not find quitword
    }

    /**
     * Iterates through the phrases of the user's input, finding
     * the longest phrase to which to respond. If two phrases are the same 
     * length, returns whichever has the lower index in the list. 
     * If phrases parameter is null or size 0 then return null.
     * 
     * @param phrases List of user phrases
     * @return the selected phrase
     */
    public static String selectPhrase(ArrayList<String> phrases) {
    	if (phrases == null || phrases.size() == 0) {
    		return "";  // if param is null or nothing in list  		
    	}
    	if(phrases.size() ==1) {
    		return phrases.get(0); // if phrase has only one word, return it
    	}
    	
    	String selected = "";
    	for(int i = 0; i <= phrases.size()-1; i++) {
    		if(phrases.get(i).length() > selected.length()) { 
    			selected = phrases.get(i); // if current phrase is longer than previous pharse, replace selected String to current phrase
    		}
    	}
 //   	System.out.println("selected: " + selected);
    	return selected; // return longest String
    }

    /** 
     * Looks for a replacement word for the word parameter and if found,
     * returns the replacement word. Otherwise if the word parameter is not
     * found then the word parameter itself is returned. 
     * The wordMap parameter contains rows of match and replacement strings.
     * On a row, the element at the 0 index is the word to match and if it 
     * matches return the string at index 1 in the same row.  Some example
     * word maps that will be passed in are Config.INPUT_WORD_MAP and 
     * Config.PRONOUN_MAP. 
     * 
     * If word is null return null. If wordMap is null or wordMap length is 
     * 0 simply return word parameter. For this implementation it is reasonable to 
     * assume that if wordMap length is >= 1 then the number of elements in 
     * each row is at least 2.
     * 
     * @param word The word to look for in the map
     * @param wordMap  The map of words to look in
     * @return the replacement string if the word parameter is found in the 
     * wordMap otherwise the word parameter itself.
     */
    public static String replaceWord(String word, String[][] wordMap) {
		String newWord = "";

		if (word == null) {
			return null; // if param word is null, return null
		}
		if (wordMap == null || wordMap.length == 0) {
			return word; // if param wordMap is null or nothing in array, return param word
		}

		for (int i = 0; i <= wordMap.length - 1; i++) {
			if (wordMap[i][0].equals(word)) { // determining that param word is in param wordMap array
				newWord = wordMap[i][1];  
				return newWord; // return corresponding word
			}
		}
        return word;
    }


    /**
     * Concatenates the elements in words parameter into a string with
     * a single space between each array element. Does not change any 
     * of the strings in the words array. There are no leading or trailing 
     * spaces in the returned string.
     * 
     * @param words a list of words
     * @return a string containing all the words with a space between each.
     */
    public static String assemblePhrase(String[] words) {
    	String assemble = "";
    	for (int i = 0; i <= words.length-1; i++) {
    	assemble = assemble + " " + words[i]; // put single space between every words
    	}
    	
    	assemble = assemble.trim(); // trim the String
    	assemble = assemble.replace("  ", " "); // replace double space with single space 
        return assemble;
    }


    /**
     * Replaces words in phrase parameter if matching words are found
     * in the mapWord parameter. A word at a time from phrase parameter
     * is looked for in wordMap which may result in more than one word.
     * For example: i'm => i am
     * Uses the replaceWord and assemblePhrase methods.
     * Example wordMaps are Config.PRONOUN_MAP and Config.INPUT_WORD_MAP.
     * If wordMap is null then phrase parameter is returned.
     * Note: there will Not be a case where a mapping will itself
     * be a key to another entry. In other words, only one pass
     * through swapWords will ever be necessary.
     * 
     * @param phrase The given phrase which contains words to swap
     * @param wordMap Pairs of corresponding match & replacement words
     * @return The reassembled phrase
     */
    public static String swapWords(String phrase, String [][]wordMap) {
		String[] array = phrase.split(" "); // divide phrase by space
		if (wordMap == null) {
			return phrase; // if param wordMap is null, return param phrase
		}

		for (int i = 0; i <= array.length - 1; i++) {
			array[i] = replaceWord(array[i], wordMap); // replace matching word in array with words in wordMap
		}
		phrase = assemblePhrase(array); // assemble the new phrase
		return phrase; // return new phrase

	}


    /**
     * This prepares the user input. First, it separates input into phrases
     * (using separatePhrases). If a phrase is a quit word (foundQuitWord) 
     * then return null.  Otherwise, select a phrase (selectPhrase), swap input 
     * words (swapWords with Config.INPUT_WORD_MAP) and return an array with
     * each word its own element in the array. 
     * 
     * @param input The input from the user
     * @return  words from the selected phrase
     */
	public static String[] prepareInput(String input) {
		ArrayList<String> separateInput = new ArrayList<String>();
		separateInput = separatePhrases(input); // separate input to ArrayList
//		System.out.println("separate" + separateInput);

		if(foundQuitWord(separateInput)) {
//			System.out.println("NULL");
			return null; // if find quitword in input, return null
		}
		
		String selectInput = "";
		String finalInput = "";
		selectInput = selectPhrase(separateInput); // find selectPhrase from input

		finalInput = swapWords(selectInput, Config.INPUT_WORD_MAP); // swapwords in selectInput with words in Config.INPUT_WORD_MAP 
	    String[] a = finalInput.split(" "); // split final input by single space
	    
//	    System.out.println("retun: " + Arrays.toString(a));
		return a; 
	}
	
	
    /**
     * Reads a file that contains keywords and responses.  A line contains either a list of keywords
     * or response, any blank lines are ignored. All leading and trailing whitespace on a line
     * is ignored. A keyword line begins with "keywords" with all the following tokens on the line, 
     * the keywords.  Each line that follows a keyword line that is not blank is a possible response
     * for the keywords. For example (the numbers are for our description purposes here and are not in the
     * file): 
     * 
     *1  keywords computer
     *2  Do computers worry you?
     *3  Why do you mention computers?
     *4
     *5  keywords i dreamed
     *6  Really, <3>?
     *7  Have you ever fantasized <3> while you were awake?
     *8
     *9  Have you ever dreamed <3> before?
     *
     *   In line 1 is a single keyword "computer" followed by two possible responses on lines
     *   2 and 3. Line 4 and 8 are ignored since they are blank (contain only whitespace).
     *   Line 5 begins new keywords that are the words "i" and "dreamed".  This keyword list
     *   is followed by three possible responses on lines 6, 7 and 9.
     *   
     *   The keywords and associated responses are each stored in their own ArrayList. The
     *   response table is an ArrayList of the keyword and responses lists. For every keywords list
     *   there is an associated response list. They are added in pairs into the list
     *   that is returned.  There will always be an even number of items in the returned list.
     *   
     *   Note that in the event an IOException occurs when trying to read the file then
     *   an error message "Error reading <fileName>", where <fileName> is the parameter, 
     *   is printed and a non-null reference is returned, which may or may not have any elements
     *   in it.
     * 
     * @param fileName  The name of the file to read
     * @return  The response table
     */
    public static ArrayList<ArrayList<String>> loadResponseTable(String fileName) {
    	ArrayList<ArrayList<String>> responseTable = new ArrayList<ArrayList<String>>(); // ArrayList of ArrayList
		ArrayList<String> keyWord; // ArrayList of String 
		ArrayList<String> response = new ArrayList<String>(); // ArrayList of String
		try {
			File file = new File(fileName); // open file
			Scanner scnr = new Scanner(file); // read the file
			String input = "";
			
	           while (scnr.hasNextLine()) {
	                input = scnr.nextLine(); 
	                input = input.trim(); // get the user input and trim it

	                if (input.indexOf("keywords") != -1) {
	                    keyWord = new ArrayList<>(); // create new List for keyword
	                    input = input.replace("keywords", ""); 
	                    input = input.trim(); // remove word "keywords" and remove white space
	                    String[] Array = input.split(" "); // get the keywords and put it into array
	                    for(int i=0; i < Array.length; ++i) {
	                        keyWord.add(Array[i]); // add keywords from array to keyword List
	                    }                    
	                    responseTable.add(keyWord); //
	                    response = new ArrayList<>();
	                    responseTable.add(response);
	                } else if (input.equals("")) {
	                    continue; // if no keyword
	                } else {
	                    response.add(input); 
	                }                
	            }
	        } catch (IOException error) {
	            System.out.println("Error reading " + fileName); // print message when IOException occurs
	        }        
	        return responseTable;
	    }

    /**
     * Checks to see if the keywords match the sentence. In other words, checks to see that all the
     * words in the keyword list are in the sentence and in the same order. If all the keywords match
     * then this method returns an array with the unmatched words before, between and after 
     * the keywords. If the keywords do not match then null is returned. 
     * 
     * When the phrase contains elements before, between, and after the keywords, each set of the 
     * three is returned in its own element
     * String[] keywords = {"i", "dreamed"};
     * String[] phrase = {"do", "you", "know", that", "i", "have", "dreamed", "of", "being", "an", "astronaut"};
     * 
     * toReturn[0] = "do you know that"
     * toReturn[1] = "have"
     * toReturn[2] = "of being an astronaut"
     *  
     * In an example where there is a single keyword, the resulting List's first element will be the 
     * the pre-sequence element and the second element will be everything after the keyword, in the phrase
     * String[] keywords = {"always"};
     * String[] phrase = {"I", "always", "knew"};
     * 
     * toReturn[0] = "I"
     * toReturn[1] = "knew"
     * 
     * In an example where a keyword is not in the phrase in the correct order, null is returned.
     * String[] keywords = {"computer"};
     * String[] phrase = {"My","dog", "is", "lost"};
     * 
     * return null
     * 
     * @param keywords The words to match, in order, in the sentence.
     * @param phrase Each word in the sentence.
     * @return The unmatched words before, between and after the keywords or null if the keywords
     * are not all matched in order in the phrase.
     */
    public static String[] findKeyWordsInPhrase(ArrayList<String> keywords, String[] phrase) {
        int indexK = 0; // index go through keyword list
        String st = ""; // string that stores the words that doesnt match
        
        String[] unmatched = new String[keywords.size() + 1];
        for(int i=0; i<unmatched.length; ++i) {
            unmatched[i] = ""; // fill unmatched array with empty string to avoid null pointer exception
        }
        
        for (int i = 0; i < phrase.length; ++i) { // check all the elements in phrase
        	if (indexK < keywords.size()) { //indexK does not go over the size of keyword
        		if(phrase[i].equals(keywords.get(indexK))) {
                    unmatched[indexK] = st;
                    st = "";
                    ++indexK; // if keyword match with word in phrase, save st that stores words and refresh it and move to next index
        		}
            	else {
            		st += " " + phrase[i];
            		st = st.trim(); // if keyword doesnt match the word
            	}
        	}
        	else {
        		st += " " + phrase[i];
        		st = st.trim(); 
        	}
        }
        unmatched[indexK] = st; // save the rest of the words after the last keyword
        
        if(indexK != keywords.size()) {
            return null; // if index doesnt match the number of keywords
        }
                
        return unmatched;
    }

    /**
     * Selects a randomly generated response within the list of possible responses
     * using the provided random number generator where the number generated corresponds
     * to the index of the selected response. Use Random nextInt( responseList.size())
     * to generate the random number.  If responseList is null or 0 length then
     * return null.
     * 
     * @param rand  A random number generator.
     * @param responseList  A list of responses to choose from.
     * @return A randomly selected response
     */
    public static String selectResponse(Random rand, ArrayList<String> responseList) {
    	if(responseList == null || responseList.size() == 0) {
    		return null; // if responseList is null or empty, return null
    	}
    	
    	int i = rand.nextInt(responseList.size()); //create random number smaller than responseList size
        return responseList.get(i); // return random index from List
    }

    /**
     * This method takes processed user input and forms a response.
     * This looks through the response table in order checking to
     * see if each keyword pattern matches the userWords. The first matching 
     * keyword pattern found determines the list of responses to choose from. 
     * A keyword pattern matches the userWords, if all the keywords are found, 
     * in order, but not necessarily contiguous. This keyword matching is done 
     * by findKeyWordsInPhrase method.  See the findKeyWordsInPhrase algorithm
     * in the Eliza.pdf.  
     * 
     * If no keyword pattern matches then Config.NO_MATCH_RESPONSE is returned.
     * Otherwise one of possible responses for the matched keywords is selected
     * with selectResponse method. The response selected is checked for the 
     * replacement symbol <n> where n is 1 to the length of unmatchedWords array
     * returned by findKeyWordsInPhrase.  For each replacement symbol the 
     * corresponding unmatched words element (index 0 for <1>, 1 for <2> etc.)
     * has its pronouns swapped with swapWords using Config.PRONOUN_MAP and then
     * replaces the replacement symbol in the response.
     * 
     * @param userWords using input after preparing.
     * @param rand A random number generator.
     * @param responseTable  A table containing a list of keywords and response pairs.  
     * @return The generated response
     */
	public static String prepareResponse( String [] userWords, Random rand, 
			ArrayList<ArrayList<String>> responseTable) {

		//Iterate through the response table.
		//The response table has paired rows.  The first row is a list of key 
		//words, the next a list of corresponding responses. The 3rd row another 
		//list of keywords and 4th row the corresponding responses.
			
			// checks to see if the current keywords match the user's words 
	        // using findKeyWordsInPhrase.

		// if no keyword pattern was matched, return Config.NO_MATCH_RESPONSE
		// else, select a response using the appropriate list of responses for the keywords

            // Look for <1>, <2> etc in the chosen response.  The number starts with 1 and
            // there won't be more than the number of elements in unmatchedWords returned by 
            // findKeyWordsInPhrase. Note the number of elements in unmatchedWords will be
            // 1 more than the number of keywords.
            // For each <n> found, find the corresponding unmatchedWords phrase (n-1) and swap
            // its pronoun words (swapWords using Config.PRONOUN_MAP). Then use the
            // result to replace the <n> in the chosen response. 
					
					//in the selected echo, swap pronouns
					
					// inserts the new phrase with pronouns swapped, into the response

        String[] array = null;
        int i = 0;
        for (i = 0; i < responseTable.size(); i += 2) {
            array = findKeyWordsInPhrase(responseTable.get(i), userWords);
            if (array != null) {
                break; // if array is not null, break the loop
            }
        }
        if (array == null) {
            return Config.NO_MATCH_RESPONSE; // if array is null return no match response
        }
        String response = selectResponse(rand, responseTable.get(i + 1));
        for (int n = 1; n <= array.length; n++) {
            if (response.contains("<" + n + ">")) { // find replacement symbol
                String swap = swapWords(array[n - 1], Config.PRONOUN_MAP);
                response = response.replaceAll("<" + n + ">", swap); // swap replacement words with swap words
            }
        }

        return response;

    }
	

	/**
	 * Creates a file with the given name, and fills that file
	 * line-by-line with the tracked conversation. Every line ends
	 * with a newline. Throws an IOException if a writing error occurs.
	 * 
	 * @param dialog the complete conversation
	 * @param fileName The file in which to write the conversation
	 * @throws IOException
	 */
	public static void saveDialog(ArrayList<String> dialog, String fileName) throws IOException {

		PrintWriter writer = new PrintWriter(fileName); // write in the filename, if it doesn't excist, create one. If it exist, overwrite 
		for (int i = 0; i <= dialog.size() - 1; i++) {
			writer.println(dialog.get(i)); // write every line of dialog
		}
			writer.close();
	}
}

