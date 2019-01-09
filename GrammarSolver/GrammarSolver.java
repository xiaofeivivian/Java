// Xiaofei Wu
//
// This program implements a object which can recorded all the Backus-Naur Form (BNF)
// and randomly generate the elements of grammar based on the given grammar in given
// times.

import java.util.*;
public class GrammarSolver{
   // a list of grammar that stores the grammars in BNF (nonterminal) and their
   // elements (terminals)
   private SortedMap<String, String[]> grammarList;
   
   // grammar: a list of String representation where stores all the grammars and their elements
   // and is needed to be stored in the grammarList
   // pre: the given list of grammar should not be empty and it shouldn't have two or more  
   // entries in the list of grammar for the same nonterminal (throws IllegalArgumentException
   // if not)
   // post: create a GrammarSolver object. It has a grammar list that contains all the 
   // nonterminal symbols and their matching rules, and stores them to the grammarList
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      grammarList = new TreeMap<String, String[]>();
      for (String symbol : grammar) {
         String[] symbols = symbol.split("::=");
         if (grammarList.containsKey(symbols[0])) {
            throw new IllegalArgumentException();
         }
         grammarList.put(symbols[0], symbols[1].trim().split("[|]"));
      }
   }
   
   // symbol: a grammar that needed to be checked if it is one of the nonterminals in the 
   // grammar list
   // post: return if the given symbol is one of the nonterminals in the grammar list
   public boolean grammarContains(String symbol) {
      return grammarList.containsKey(symbol);
   }
   
   // symbol: a nonterminal grammar whose elements will be randomly generated
   // times: a number that sets up the times for generating the given symbol
   // pre: the given symbol should be one of the nonterminals in grammar list and the given 
   // times should be greater than or equal to 0 (throws IllegalArgumentException if not)
   // post: return a list that contains given times of string generation
   public String[] generate(String symbol, int times) {
      if (!grammarList.containsKey(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      String[] result = new String[times];
      for (int i = 0; i < times; i++) {
         result[i] = generateHelper(symbol).trim();
      }
      return result;
   }
   
   // symbols: a grammar whose elements will be randomly generated
   // post: return the symbols itself if the given symbol is not one of the nonterminals in
   // grammar list; while if the symbols is one of the nonterminals, it will return the result
   // String representation that is made of all the elements of given symbol.
   private String generateHelper(String symbols) {
      if (!grammarContains(symbols)) {
         return symbols + " ";
      } else {
         String result = "";
         Random rand = new Random();
         String symbol = grammarList.get(symbols)[rand.nextInt(grammarList.get(symbols).length)];
         String[] terminal = symbol.trim().split("[ \t]+");
         for (String rules : terminal) {
            result += generateHelper(rules);
         }
         return result;
      }
   }
   
   // post: return a String representation where contains all the nonterminals in the 
   // grammar List 
   public String getSymbols() {
      return grammarList.keySet().toString();
   }
}