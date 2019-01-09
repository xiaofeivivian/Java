// Xiaofei Wu
//
// This program implements a object which can store a dictionary in a list and find out
// and print out all possible combinations of words using the same letters from a given
// phrase.

import java.util.*;
public class AnagramSolver {
   // a list of dictionary which stores all the words and their own letters from the given
   // list unorderly and words would be used to test if containing letters from a given phrase.
   private Map<String, LetterInventory> dictionary;
   // a list of words which stores the original given list orderly
   private List<String> originalList;
   
   // list: the given list of words that would be stored in the dictionary list unorderly
   // and the original list orderly.
   // pre: the given list is a nonempty list of nonempty sequences of letters and that it
   // contains no duplicates.
   // post: create a new Anagram Solver with given list of words.The dictionary list 
   // stores all the words and their own letters from the given list unorderly. The original
   // list stores all the words from the given list orderly.
   public AnagramSolver(List<String> list) {
      dictionary = new HashMap<String, LetterInventory>();
      originalList = list;
      for (String word : list) {
         LetterInventory letters = new LetterInventory(word);
         dictionary.put(word, letters);
      }
   }
   
   // s: a phrase of String representation where stores the given phrase that would be used
   // to find all possible combination of words have the same letters as the given phrase
   // max: the maxium of words of a particular combination using the same letters as the given
   // phrase.
   // pre: the maxium of words in one combination should be equal to or greater than 0 (throws
   // IllegalArgumentException if not)
   // post: those words using the same letters as the given phrase from originarl would be copied
   // to a list of small dictionary and this small dictionary would be used to find out all the
   // combinations of words.
   // if max equals to 0, it would be changed to the number of letters in the given phrase.
   // print out all the possible combinations of words having the same letters as the
   // given phrase. If the max is greater than 0, each combination would contain words at a
   // number of max. If the max equals to 0, each combination would not have the limit in number
   // of words it contains.
   // if the given String phrase is empty, it would not print anything
   public void print(String s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      if (s != null) {
         Stack<String> result = new Stack<String>();
         List<String> smallDictionary = new ArrayList<String>();
         LetterInventory letters = new LetterInventory(s);
         for (String word : originalList) {
            if (letters.subtract(dictionary.get(word)) != null) {
               smallDictionary.add(word);
            }
         }
         if (max == 0) {
            max = s.length();
         }
         print(letters, max, result, smallDictionary);
      }
   }
   
   // letters: all the letters of the given phrase.
   // max: the maxium of words of a particular combination using the same letters as the given
   // phrase.
   // result: a String list of words which is one combination of words having the same letters 
   // as the given phrase.
   // smallDictionary: a String list of words which all have the same letters as the given phrase
   // and would be used to find all the combinations of words.
   // post: All the possible combinations of words having the same letters as the given phrase
   // are stored in the result String list and the list would be printed out 
   private void print(LetterInventory letters, int max, Stack<String> result,
   List<String> smallDictionary) {
      if (letters.isEmpty()) {
         System.out.println(result);
      } else if (max >= 1){
         for (String words : smallDictionary) {
            if (letters.subtract(dictionary.get(words)) != null) {
               result.push(words);
               print(letters.subtract(dictionary.get(words)), max - 1, result, smallDictionary);
               result.pop();
            }
         }
      }
   }
}