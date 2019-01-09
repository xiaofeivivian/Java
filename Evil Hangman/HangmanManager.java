// Xiaofei Wu
//
// This program implements a object which can record the process of the game of hangman.
// It will store a dictionary to the list and keep track of all the guessed letters and
// the correct letters. 

import java.util.*;

public class HangmanManager {
   // a list of words that is the basis of this hangman game and where the guessed word
   // comes from
   private Set<String> words;
   // a list of letter that stores the letters that have been guessed
   private SortedSet<Character> letters;
   // a pattern that shows the current state of the guessed word, showing the correct 
   // guessing letters and those hiden ones
   private String pattern;
   // an integer that represents the remaining guessing times
   private int max;
   
   // dictionary: a String Collection representation whose the list of words 
   // will be stored to the list for hangman game
   // length: the length of words for this particular hangman game
   // max: the max guessing times for this particular hangman game
   // pre: the length should be greater than or equal to 1 and max should be greater
   // than or equal to 0 (throws IllegalArgumentException if not)
   // post: create a HangmanManager object and store all those words which have the
   // given length to the guessing list, and the the max guessing times is stored in
   // the remaining guessing times
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      words = new TreeSet<String>();
      letters = new TreeSet<Character>();
      pattern = "";
      for (int i = 0; i < length; i++) {
         pattern = pattern + "- ";
      }
      pattern = pattern.substring(0, pattern.length() - 1);
      this.max = max;
      for (String word : dictionary) {
         if (word.length() == length) {
            words.add(word);
         }
      }
   }
   
   // words: a list that stores the the current guessing game list
   // wordsFamily: a list that stores the particular word's pattern and those words that
   // have that pattern 
   // pattern: the current Stirng pattern of the guessing word
   // guess: the particular guessed letter by client
   // post: return a String representation which is the resulting pattern; if the guessed
   // letter is correct it should be a new pattern. if the guessed letter is incorrect, the
   // pattern will be the original one.
   private String getPattern(Set<String> words, Map<String, Set<String>> wordsFamily, 
      String pattern, char guess) {
      for (String word : words) {
         String currentPattern = pattern;
         for(int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
               currentPattern = currentPattern.substring(0, 2 * i) + guess + 
                                currentPattern.substring(2 * i + 1);
            }
         }
         if (!wordsFamily.containsKey(currentPattern)) {
            wordsFamily.put(currentPattern, new TreeSet<String>());
         }
         wordsFamily.get(currentPattern).add(word);
      }
      boolean firstSwap = false;
      for (String current : wordsFamily.keySet()) {
         if (!firstSwap || wordsFamily.get(current).size() > wordsFamily.get(pattern).size()) {
            pattern = current;
         }
         firstSwap = true;
      }
      String result = pattern;
      return result;
   }
   
   // return the current list of guessing words
   public Set<String> words() {
      return words;
   }
   
   // return the remaining guessing times
   public int guessesLeft() {
      return max;
   }
   
   // return the list which stores the letters have been guessed
   public SortedSet<Character> guesses() {
      return letters;
   }
   
   // return a String pattern that is the current guessing word's pattern
   public String pattern() {
      return pattern;
   }
   
   // guess: a letter that is guessed by the client and will be recorded
   // pre: the remaining guessing times should be greater than or equal to 1
   // or the list that stores the guessing words should not be empty (throws 
   // IllegalStateException if not); the list that stores the guessing words
   // should be empty and the letters being guessed was not guessed previously.
   // (throws IllegalArgumentException if not)
   // post: return the occurences of the letter being guessed in the resulting
   // pattern
   public int record(char guess) {
      if (guessesLeft() < 1 || words().isEmpty()) {
         throw new IllegalStateException();
      }
      if (!words().isEmpty() && letters.contains(guess)) {
         throw new IllegalArgumentException();
      }
      Map<String, Set<String>> wordsFamily = new TreeMap<String, Set<String>>();
      letters.add(guess);
      pattern = getPattern(words, wordsFamily, pattern, guess);
      words = wordsFamily.get(pattern);
      int result = 0;
      for (int i = 0; i < pattern.length(); i++) {
         if (pattern.charAt(i) == guess) {
            result++;
         }
      }
      if (!pattern.contains("" + guess)) {
         max--;
      }
      return result;
   }
}