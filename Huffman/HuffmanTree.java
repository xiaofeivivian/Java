// Xiaofei Wu
//
// This program implements a object which can store a list of characters
// based on Huffman method which would use a few bits for characters used
// frequently and it would be helpful for compressing the text file.

import java.io.*;
import java.util.*;
public class HuffmanTree {
   // the basic character in the list
   private HuffmanNode overallRoot;
   
   // count: a list of characters that would be stored in the list
   // in a way based on their frequency.
   // post: create a HuffmanTree object that stores a list of characters.
   // There is a special character that would be used for decoding would be stored in
   // the list and the characters that have frequency that is greater than 0 would
   // be stored in the list. These characters we can call them original characters and
   // they would be combined together by pairs repeatly. The basic character is stored and
   // it should be the combination of all characters.
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> initialTree = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            initialTree.add(new HuffmanNode(i, count[i]));
         }
      }
      initialTree.add(new HuffmanNode(count.length, 1));
      while (initialTree.size() > 1) {
         HuffmanNode firstNode = initialTree.remove();
         HuffmanNode secondNode = initialTree.remove();
         int frequency = firstNode.frequency + secondNode.frequency;
         initialTree.add(new HuffmanNode(-1, frequency, firstNode, secondNode));
      }
      overallRoot = initialTree.remove();
   }
   
   // input: a list of original characters that once be put in the list of the object.
   // pre: the original characters come from a list of characters built in standard format
   // which means each characters that are not original would be composed by two sub characters
   // post: it would store both original characters and characters that are not
   // original to the list of the object. The characters that are not original are all the same.
   public HuffmanTree(Scanner input) {
      while (input.hasNextLine()) {
         int result = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = HuffmanTreeHelper(overallRoot, result, code);
      }
   }
   
   // root: the current character that would be stored in the list
   // result: the original character that would be stored in the list
   // code: a String representation which is the location of the original character
   // post: it would store both original characters and characters that are not
   // original to the list of the object. The characters that are not original are all the same.
   // When the code is not empty, if the code starts with "0", it would add
   // a left sub character that is not original to the list; if the code starts with "1",
   // it would add a right sub character that is not original to the list. If the code is
   // empty, it would add the original character to the list.
   private HuffmanNode HuffmanTreeHelper(HuffmanNode root,int result, String code) {
      if (code.length() == 0) {
         root = new HuffmanNode(result);
      } else {
         if (root == null) {
            root = new HuffmanNode(-1);
         }
         if (code.charAt(0) == '0') {
            root.left = HuffmanTreeHelper(root.left, result, code.substring(1));
         } else if (code.charAt(0) == '1') {
            root.right = HuffmanTreeHelper(root.right, result, code.substring(1));
         }
      }
      return root;
   }
   
   // output: the tool for print out the characters in the file
   // post: it would first print out the orginal characters and then print out a String
   // representation that contains "0" and "1" that represents the location of the
   // original character. Repeating this process and until all the original characters
   // are printed
   public void write(PrintStream output) {
      writeHelper(overallRoot, output, "");
   }
   
   // root: the current character that would be tested if would be printed in the file
   // output: the tool for print out the characters in the file
   // code: a String representation that contains "0" and "1" that represents the location
   // of the original character.
   // post: if the current character is not original character, it would check either left
   // sub character or right character, if it tests the left sub character, it would add
   // a "0" to the code; if it tests the right sub character, it would add a "1" to the code.
   // if the character is the original character that would be printed out first
   // and then print out a String representation that contains "0"s and "1"s that
   // represents the location of the original character.
   private void writeHelper(HuffmanNode root, PrintStream output, String code) {
      if (ifLeaf(root)) {
         output.println(root.data);
         output.println(code);
      } else {
         writeHelper(root.left, output, code + "0");
         writeHelper(root.right, output, code + "1");
      }
   }
   
   // root: the current character that would be tested if it is the original character
   // post: return if the current character is the original character
   private boolean ifLeaf(HuffmanNode root) {
      return (root.left == null && root.right == null);
   }
   
   // input: it stores the "0"s or "1"s that represents the location of the character
   // output: the tool for print out the characters in the file
   // eof: the special character that has been put into the list
   // post: if the character is not the special character, it would continue testing
   // the character. It starts from the basic character and if the input is "0", it
   // would test the left sub character; if the input is "1", it would test the sub right
   // character. if the test character is the original character, it would be printed out
   // in the file. if not, it would continue searching. if the character is the special
   // character, the process would stop.
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode root = overallRoot;
      while (root != null && root.data != eof) {
         if (ifLeaf(root)) {
            output.write(root.data);
            root = overallRoot;
         } else if (input.readBit() == 0) {
            root = root.left;
         } else {
            root = root.right;
         }
      }
   }
}