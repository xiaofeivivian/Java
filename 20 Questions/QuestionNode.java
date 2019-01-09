// Xiaofei Wu
//
// This program implements a object which can keep track of a list of questions and answers
// that would be used to play the yes/no game. 

import java.util.*;
public class QuestionNode{
    // the representation of the current question or answer.
    public String data;	
    // the representation of the yes response to the question. It could be either a question
    // or an answer
    public QuestionNode left; 
    // the representation of the no response to the question. It could be either a question
    // or an answer
    public QuestionNode right; 
	 
    // data: an answer that want to be stored in the list of guessing game
    // post: create an answer code for guessing game			    
    public QuestionNode(String data) {
      this(data, null, null);
    }
    
    // data: a question that want to be stored in the list of guessing game
    // left: the representation of the yes response to the question. It could be either a 
    // question or an answer
    // right: the representation of the no response to the question. It could be either a 
    // question or an answer
    // post: create a question node for guessing game with its corresponding yes/no sub 
    // questions node.
    public QuestionNode(String data, QuestionNode left, QuestionNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }   
}