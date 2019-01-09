// Xiaofei Wu
//
// This program implements a object which can record
// the inventory of letters of the alphabet and is allowed to
// change the data of letters.

public class LetterInventory {
   // a constant that limits the boundary of the Inventory which can be changed accordingly
   public static final int CAPACITY = 26;
   // a list of integer to store the counts of each letter
   private int[] elementData;
   // an integer as the sum of all the counts in the inventory
   private int size;
   
   // take a string as parameter
   // post: create a LetterInventory object
   public LetterInventory(String data) {
      elementData = new int[CAPACITY];
      size = 0;
      for (int i = 0; i < data.length(); i++) {
         int result = getPlace(data.charAt(i));
         if (result >= 0 && result < CAPACITY) {
            elementData[result]++;
            size++;
         }
      }
   }
   
   // take a letter as parameter that needed to be counted in the inventory
   // post: return an integer which represents the place of the given letter 
   // in the inventory
   private int getPlace(char letter) {
      letter = Character.toLowerCase(letter);
      int result = (int) letter - 'a';
      return result;
   }
   
   // post: return an integer which stores the sum of all the counts in the inventory
   public int size() {
      return size;
   }
   
   // post: return whether the inventory is empty
   public boolean isEmpty() {
      return (size == 0);
   }
   
   // take a letter as parameter that needed to be counted in the inventory
   // pre : the given letter is an alphabetic character (throws IllegalArgumentException if not)
   // post: returns the integer which is the count of given letter in the inventory
   public int get(char letter) {
      int result = getPlace(letter);
      if (result < 0 || result >= CAPACITY) {
         throw new IllegalArgumentException();
      }
      return elementData[result];
   }
   
   // post: return the bracketed version of the inventory
   public String toString() {
      String list = "[";
      for (int i = 0; i < CAPACITY; i++) {
         for (int j = 0; j < elementData[i]; j++) {
            char letter = (char) (i + 'a');
            list = list + letter;
         }
      }
      list = list + "]";
      return list;
   }
   
   // take a letter and a integer count of given letter (value) as parameters
   // pre : the given letter is an alphabetic character and value is greater than 0
   // (throws IllegalArgumentException if not)
   // post: change the count of particular letter to the given value in this inventory
   public void set(char letter, int value) {
      int result = getPlace(letter);
      if (value < 0 || result < 0 || result >= CAPACITY) {
         throw new IllegalArgumentException();
      }
      size = size + value - elementData[result];
      elementData[result] = value;
   }
   
   // take a LetterInventory object as parameter whose inventory would be added to the current
   // LetterInventory object
   // post: return a new LetterInventory object which is the sum of current and other
   // LetterInventory and the current and other LetterInventory won't be changed
   public LetterInventory add(LetterInventory other) {
      LetterInventory result = new LetterInventory("");
      for (int i = 0; i < CAPACITY; i++) {
         result.elementData[i] = elementData[i] + other.elementData[i];
      }
      result.size = size + other.size;
      return result;
   }
   
   // take a LetterInventory object as parameter whose inventory would be substracted by the
   // current LetterInventory object
   // pre: count of every letter in this inventory should be greater than that of the other
   // post: if the difference is positive return a new LetterInventory object which is the
   // difference of current and other LetterInventory. if the difference is negative, return
   // null. the current and other LetterInventory won't be changed.
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory result = new LetterInventory("");
      for (int i = 0; i < CAPACITY; i++) {
         if (elementData[i] - other.elementData[i] < 0) {
            return null;
         }
         result.elementData[i] = elementData[i] - other.elementData[i];
      }
      result.size = size - other.size;
      return result;
   }
}