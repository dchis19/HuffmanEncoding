import java.util.*;
import java.lang.*;
import java.io.*;

/**
* The SortMe class ensures that the HuffmanTree is compared and then sorted appropriately
* based on the letters frequencies from the frequency table. 
* 
* @preconditions 1: Huffman Nodes min1 and min2.
*  
* @post condition: Properly sorted nodes. 
* 
* @author Daniel Chisner
*/
 
class SortMe implements Comparator<HuffmanNode> {
   public int compare(HuffmanNode min1, HuffmanNode min2)
   {
      return min1.frequency - min2.frequency;
   }
}//End SortMe class




/**
* HuffmanNode class gives the structures for the Huffman Tree.
* 
* It is called from the Huffman Class below. 
* 
*/

class HuffmanNode {
 
   char letter;
   int frequency;
 
   HuffmanNode left;
   HuffmanNode right;
}//End HuffmanNode class




/**
* The Huffman class does the vast majority of the work for the huffman sorting. 
* Based on frequency tables, lower priroity nodes go to the left and higher ones
* go to the right. Each right movement adds 1 to the encoding number. Each left
* movement results in a 0 being added. 
* 
* @preconditions 1: Huffman Nodes min1 and min2.
*  
* @post condition: Properly sorted nodes. 
* 
*/

public class Huffman {

   static List<String> encoding = new ArrayList<>(); //Store the encoded huffman code
   static List<Character> letters = new ArrayList<>(); //Store letters from huffman code

   /**
    * The printEncoding()function recursively adds the letters and encoding to the encoding
    * and letters arrayLists. While traversing down the tree, if the program goes left, 
    * it adds a 0 to the encoding. If it goes right, it adds a one. Once all the elements 
    * (encoding = 26) are added to the arraylist, the printEncoding() function calls the 
    * returnList() function. 
    */

   static void printEncoding(HuffmanNode root, String huffmanCodeGenerator) throws IOException
   {
      if (root.left == null && root.right == null && Character.isLetter(root.letter)) {
         // c is the character in the node
         encoding.add(huffmanCodeGenerator);
         letters.add(root.letter);
         System.out.println(root.letter + " = " + huffmanCodeGenerator);
         if (encoding.size() == 26){
            returnList();
            return;
         }
         else{
            return;
         }
      }
      printEncoding(root.left, huffmanCodeGenerator + "0"); //Lefts add "0" to encoding. 
      printEncoding(root.right, huffmanCodeGenerator + "1"); //Rights add "1" to encoding. 
   } //End printEncoding() method




   /**
    * returnList() function calls the BaseClass and passes the new encoded and letters 
    * arrayLists.
    */

   static void returnList() throws IOException{
      BaseClass baseClass = new BaseClass();
      baseClass.choice(encoding, letters);
   } //End returnList() method




   /**
    * The start() function receives the charList and charFrequency Lists from the BaseClass. 
    * During the first portion of the code, a priority queue (priorityQueue) is created in 
    * the min-Heap format. A for loop is then run creating a huffmanNode for the objects in
    * charArry and charFreq lists. Each node is then added to the priorityQueue. A while loop
    * is then run and the minimum values are taken out of the priorityQueue. The smaller of 
    * the two values goes to the left and the larger one goes to the right until priorityQueue
    * gets down to size = 1. 
    */

   static void start(List<Character> charList, List<Integer> charFrequency) throws IOException
   {
      Scanner huffmanCodeGenerator = new Scanner(System.in);
      int n = 26; // number of elements in both lists.
      List<Character> charArray = charList;
      List<Integer> charFreq = charFrequency;
      PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<HuffmanNode>(n, new SortMe());

      for (int i = 0; i < n; i++) { //Iterate through lists and added to priorityQueue.
         HuffmanNode huffmanNode = new HuffmanNode();
         huffmanNode.letter = charArray.get(i);
         huffmanNode.frequency = charFreq.get(i);
         huffmanNode.left = null;
         huffmanNode.right = null;
         priorityQueue.add(huffmanNode);
      }
 
      HuffmanNode root = null; // create a root node
 
      while (priorityQueue.size() > 1) {

         //Take out a minimum value and label as min1
         HuffmanNode min1 = priorityQueue.peek();
         priorityQueue.poll();
 
         //Take out a minimum value and label as min2
         HuffmanNode min2 = priorityQueue.peek();
         priorityQueue.poll();
 
         HuffmanNode huffNode = new HuffmanNode(); // new huffNode
 
         //Sum of frequencies of min1 and min2 equal parent frequency.
         huffNode.frequency = min1.frequency + min2.frequency;
         huffNode.letter = '-';
 
         huffNode.left = min1; //smaller node is left child.
 
         huffNode.right = min2; //larger node is right child.
 
         root = huffNode; //The huffNode is the rootNode
 
         priorityQueue.add(huffNode); //Add this node to the priority-queue.
      }
      printEncoding(root, ""); // print the codes by traversing the tree
   }//End start() method

}//End Huffman class