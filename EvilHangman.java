/**
 * Evil Hangman
 *
 * by Jake Leland
 *
 * This game is an "evil" version of hangman. It functions very similarly to the hangman game that everyone is used to, but it is much more difficult.
 * Instead of picking a word at the beginning of the game, the computer never decides on an actual word until the very end.
 * Instead, it changes the "word" that the user is guessing so that it is as difficult as possible to actually guess.
 * As the user guesses letters, the code goes through a dictionary and determines if there are more words with or without the guessed letter.
 * If there are more words in the dictionary without the guessed letter, then the computer will say that the word does not contain the letter, because this leaves more words as the possible answer.
 * If there are more words in the dictionary with the letter, the computer will split up the words based on the pattern that the guessed letter forms in the word. (this is explained better in the code below)
 * The pattern that has the most matching words is chosen, because this leaves more words as the possible answer.
 * In summary, the computer eventually chooses the last word that you would have ever guessed, making it very difficult.
 * It is possible to win! Have fun and good luck!
 */


import java.io.*;
import java.util.*;

public class EvilHangman
{
    public static void main(String[] args) throws FileNotFoundException
    {
    	Scanner kb = new Scanner(System.in);
    	System.out.println("Welcome to hangman.");
    	System.out.print("How many letters would you like? ");
    	int letters = kb.nextInt();
    	kb.nextLine();
    	System.out.print("How many guesses would you like? ");
    	int guesses = kb.nextInt();
    	kb.nextLine();
    	Scanner file = new Scanner(new File("dictionary.txt"));
    	ArrayList<String> words = new ArrayList<String>();

    	// Go through the dictionary.
    	// Add every word with the same number of letters as requested to the ArrayList called words.
    	while(file.hasNext())
    	{
    		String s = file.nextLine();
    		if(s.length() == letters)
    			words.add(s);
    	}

    	ArrayList<Character> guessedLetters = new ArrayList<Character>();

		String word = "";
		for(int i=0;i<letters;i++)
			word += "-";
    	while(word.contains("-") && guesses>0)
    	{
    		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    		System.out.print("You have guessed the following letters:");
	    	System.out.println(guessedLetters);

	    	System.out.println("You have " + guesses + " guesses remaining.");

	    	System.out.println();

			System.out.println(word);
	    	System.out.println();


    		System.out.print("Enter a letter: ");
	    	String tmpLetter = kb.nextLine();
	    	char letter = tmpLetter.charAt(0);
	    	System.out.println();

	    	ArrayList<String> yesLetter = new ArrayList<String>();
	    	ArrayList<String> noLetter = new ArrayList<String>();
	    	// Go through all of the words in the ArrayList "words".
	    	// If the word contains the requested letter, add it to the ArrayList "yesLetter".
	    	// If the word does not contain the requested letter, add it to the ArrayList "noLetter".
	    	for(String w : words)
	    	{
	    		if(w.contains(""+letter))
	    			yesLetter.add(w);
	    		else
	    			noLetter.add(w);
	    	}

	    	// If there are more words in the dictionary with that letter than without, claim that the word contains the letter
	    	if(yesLetter.size() >= noLetter.size())
	    	{
	    		// Fill the ArrayList "words" with all of the words that contain the requested letter.
	    		words = (ArrayList<String>)(yesLetter.clone());
	    		// Put them in families based on the location(s) of the resquested letter within the word.

	    		// This map will be used to store all of the words based on the pattern.
	    		// The keys will be the patterns formed by the requested letter, and the value of each key will be an ArrayList containing all of the words that fit that pattern.
	    		// For example, if the requested letter is 'o', the words "book", "cook", "door", etc., all fit the pattern "-oo-".
	    		// Another example, if the requested letter is 't', the words "tree", "toad", "this", "team", etc., all fit the pattern "t---".
	    		Map<String,ArrayList<String>> families = new TreeMap<String,ArrayList<String>>();
	    		for(String w : words)
	    		{
	    			// For each word in the ArrayList "words", replace every letter EXCEPT for the requested letter with a dash (-).
	    			String family = w.replaceAll("[^"+letter+"]","-");
	    			// Check to see if there are already words that fit that same pattern.
	    			ArrayList<String> familyContents = families.get(family);
	    			// If there are not yet any words that fit that pattern, create a new ArrayList to hold all of the words that match that pattern
					if(familyContents == null)
					{
						familyContents = new ArrayList<String>();
					}
					// Add the word to the list of words that fit that pattern.
					familyContents.add(w);
					// Store the list of words that fit that pattern back into the map.
					// Technically, this is not needed, but it is good practice.
					families.put(family,familyContents);
	    		}
	    		// Find largest familiy, and fill the ArrayList "words" with the contents of that family.
	    		int largestSize = -1;
	    		String largestKey = "";
	    		Set<String> keys = families.keySet();
	    		for(String key : keys)
	    		{
	    			if(families.get(key).size() > largestSize)
	    			{
	    				largestSize = families.get(key).size();
	    				largestKey = key;
	    			}
	    		}
	    		words = (ArrayList<String>)(families.get(largestKey).clone());

	    		// Go through the blanks that are displayed on the screen, and fill in the letter that was just guessed based on what family was selected above.
	    		for(int i=0;i<largestKey.length();i++)
	    		{
	    			if(largestKey.charAt(i) != '-')
	    			{
	    				word = word.substring(0,i) + largestKey.charAt(i) + word.substring(i+1);
	    			}
	    		}
	    	}
	    	// If there are more words in the dictionary without that letter than with, claim that the word does not contain the letter
	    	else
	    	{
	    		// Fill the ArrayList "words" with all of the words that do not contain the requested letter.
	    		words = (ArrayList<String>)(noLetter.clone());
	    		guesses--;
	    	}

	    	guessedLetters.add(letter);
    	}
    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    	if(guesses==0)
    	{
    		System.out.println("YOU LOSE");
    		System.out.println("GAME OVER");
    	}
    	else
    	{
    		System.out.println("YOU WIN");
    		System.out.println("The word was \""+word+"\"");
    		System.out.println("You used "+(guessedLetters.size()) + " guesses.");
    	}
    }
}