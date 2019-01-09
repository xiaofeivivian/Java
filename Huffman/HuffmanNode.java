// Xiaofei Wu
//
// This program implements a object which can keep track of a list of Huffman nodes
// and its sub trees. The nodes without the sub trees would be the leaf nodes
// and those with the sub trees are branch nodes.

public class HuffmanNode implements Comparable<HuffmanNode>{
   // the ascii of the current Huffman node
   public int data;
   // the representation of the left sub tree
   public HuffmanNode left;
   // the representation of the right sub tree
   public HuffmanNode right;
   // the frequency of particular Huffman node
   public int frequency;
   
   // data: the ascii that would be stored in the leaf node
   // post: create an Huffman leaf node that represents a character whose
   // ascii is the given data. The frequency of the node would be stored -1.
   public HuffmanNode(int data) {
      this(data, -1, null, null);
   }
   
   // data: the ascii that would be stored in the branch node
   // frequency: the given frequency that would be stored in the leaf node
   // post: create an Huffman leaf node that represents a character whose
   // ascii is the given data. The given frequency is also stored in the
   // Huffman node
   public HuffmanNode(int data, int frequency) {
      this(data, frequency, null, null);
   }
   
   // data: the ascii that would be stored in the branch node
   // left: the representation of the left sub tree
   // right representation of the right sub tree
   // frequency: the given frequency that would be stored in the branch node
   // post: create a Huffman branch node
   public HuffmanNode(int data, int frequency, HuffmanNode left, HuffmanNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
      this.frequency = frequency;
   }
   
   // other: the other Huffman node
   // post: return the difference of two Huffman nodes' frequency. if the
   // difference is greater than 0, the current Huffman node would come ahead
   // of the other Huffman node in the list. if the difference is 0, they
   // both stay at the same postion. if the difference is less than 0, the other
   // Huffman node would come ahead of the current Huffman node.
   public int compareTo(HuffmanNode other) {
      return frequency - other.frequency;
   }
}