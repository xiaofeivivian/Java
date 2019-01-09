// Xiaofei Wu
//
// This program implements a object which can keep track of a yes/no guessing game. The 
// list of questions and answers would be updated. When the computer can't guess correctly,
// it would ask the client what the answer is and what is the question for that answer 
// and store these information for future game.

import java.io.*;
import java.util.*;
public class QuestionTree{
   // the basic question in the guessing game list for game to start with
   private QuestionNode overallRoot;
   // the place that records the answers and questions from client
   private Scanner console;
   
   // post: create a QuestionTree object. it would assign the basic question with 
   // the answer "computer" and construct the place for client to type in the responses.
   public QuestionTree() {
      overallRoot = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
   // input: the list of questions and answers that would be stored in the guessing game list
   // pre: the given list in standard format of questions and answers is not empty and
   // each question should have a yes or no answers. 
   // post: all the questions and answers from the input list will be stored in the guessing
   // game list
   public void read(Scanner input) {
      overallRoot = readHelper(input);
   }
   
   // input: the list of questions and answers that would be stored in the guessing game list
   // pre: the given list in standard format of questions and answers is not empty and each 
   // question should have a yes or no answers
   // post: all the questions and answers from the input list will be stored in the guessing
   // game list. if the type of particular input is an answer, it would not follow with extra 
   // questions or answers; if the type of particular input is questions, it would follow with
   // two responses, either questions or answers.
   private QuestionNode readHelper(Scanner input) {
      String type = input.nextLine();
      if (type.equals("A:")) {
         return new QuestionNode(input.nextLine());
      } else {
         String question = input.nextLine();
         QuestionNode left = readHelper(input);
         QuestionNode right = readHelper(input);
         return new QuestionNode(question, left, right);
      }
   }
   
   // output: the tool for print out the list of guessing game in the file
   // post: all the questions and answers in the list would be printed out. If it is a question
   // it would print a "Q: " before the question; If it is an answer, it would print a "A: "
   // before it. The printing output follows would first print the basic question and then it 
   // would print all sub the yes responses, and when it reaches the last yes response, it would
   // print the sub no responses and gradually back to the first no reponse. Then it would print 
   // all the sub yes responses and then sub no responses.
   public void write(PrintStream output) {
      writeHelper(overallRoot, output);
   }
   
   // root: it represents the current question or answer that would be printed out
   // output: the tool for print out the list of guessing game in the file
   // post: all the questions and answers in the list would be printed out. If it is a question
   // it would print a "Q: " before the question; If it is an answer, it would print a "A: "
   // before it. The printing output follows would first print the basic question and then it 
   // would print all sub the yes responses, and when it reaches the last yes response, it would
   // print the sub no responses and gradually back to the first no reponse. Then it would print 
   // all the sub yes responses and then sub no responses.
   private void writeHelper(QuestionNode root, PrintStream output) {
      if (isLeaf(root)) {
         output.println("A:");
         output.println(root.data);
      } else {
         output.println("Q:");
         output.println(root.data);
         writeHelper(root.left, output);
         writeHelper(root.right, output);
      }
   }
   
   // post: it would ask the client questions or give an answer and ask if it guesses
   // correctly. if the answer is not correct, it would ask the client the correct
   // answer and what the question for this answer and store them in the guessing game list;
   // if the client say yes for the question, the given answer by the client would be store
   // in the yes response for the question; if the client say no for the question, the the given
   // by the client would be store in the no response for the question; The previous wrong answer
   // would be set to the opposite respone to the answer given by client in new question.
   public void askQuestions() {
      overallRoot = askQuestionsHelper(overallRoot);
   }
   
   // root: it represents the current question or answer 
   // post: it would ask the client questions or give an answer and ask if it guesses
   // correctly. if the answer is not correct, it would ask the client the correct
   // answer and what the question for this answer and store them in the guessing game list;
   // if the client say yes for the question, the given answer by the client would be stored
   // in the yes response for the question; if the client say no for the question, the the given
   // by the client would be stored in the no response for the question; The previous wrong 
   // answer would be set to the opposite respone to the answer given by client in new question.
   private QuestionNode askQuestionsHelper(QuestionNode root) {
      if (isLeaf(root)) {
         if (yesTo("Would your object happen to be " + root.data + "?")) {
            System.out.println("Great, I got it right!");
         } else {
            root = createQuestion(root);
         }
      } else if (yesTo(root.data)){
         root.left = askQuestionsHelper(root.left);
      } else {
         root.right = askQuestionsHelper(root.right);
      }
      return root;
   }
   
   // post: asks the user a question, forcing an answer of "y " or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
   
   // root: it represents the current question or answer 
   // post: it would ask the client questions or give an answer and ask if it guesses
   // correctly. if the answer is not correct, it would ask the client the correct
   // answer and what the question for this answer and store them in the guessing game list;
   // if the client say yes for the question, the given answer by the client would be stored
   // in the yes response for the question; if the client say no for the question, the the given
   // by the client would be stored in the no response for the question; The previous wrong 
   // answer would be set to the opposite respone to the answer given by client in new question.
   private QuestionNode createQuestion(QuestionNode root) {
      System.out.print("What is the name of your object? ");
      String response = console.nextLine().trim();
      System.out.println("Please give me a yes/no question that");
      System.out.println("distinguishes between your object");
      System.out.print("and mine--> ");
      String question = console.nextLine().trim();
      if(yesTo("And what is the answer for your object?")) {
         return new QuestionNode(question, new QuestionNode(response), root);
      } else {
         return new QuestionNode(question, root, new QuestionNode(response));
      }
   }
   
   // root: it represents the current question or answer
   // post: return if the given representation is an answer.
   private boolean isLeaf(QuestionNode root) {
      return (root.left == null && root.right == null);
   }
}