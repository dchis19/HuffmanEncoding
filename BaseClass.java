import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.Collections;
import java.nio.file.Files;
import java.nio.file.Path;
 
/**
* This BaseClass will take the frequency table and pass it to the huffman class. After an encoding table is created,
* in the huffman class, the encoding table and its respective character table are stored in the BaseClass file. The
* encoding tables and character tables are then passed to either the encoding or decoding classes. 
* 
* @preconditions 1: FreqTable.txt is the input file
*  
* @post condition: None. The class calls either the Encoding or Decoding classes. 
* 
* @author Daniel Chisner
*/

public class BaseClass {


   static int n = 26; //Maximum number of letters = 26 in alphabet
   static List<Character> charList = new ArrayList<>(); //Stores letters A-Z
   static List<Integer> charFrequency = new ArrayList<>(); //Stores frequency of letters
   static List<String> huffmanCode = new ArrayList<>(); //Store the huffman code for each letter
   static List<Character> huffmanLetters = new ArrayList<>(); //Store the letters in huffman code order




   /**
    * Conversion() method removes all spaces from the frequency table. To ensure the file is a frequency table, an
    * if/else is run. The if compares the first, second, and third characters of the file to ensure it is in frequency
    * table format. If it is, then the characters from the frequency table are stored in the charList List and the 
    * frequency of the characters is stored in the charFrequency list. 
    */

   static void conversion(String input){
      String minusString = "-";
      char minus = minusString.charAt(0);
      input = input.replaceAll("\\s", "");
      if (Character.isLetter(input.charAt(0)) && input.charAt(1) == minus && Character.isDigit(input.charAt(2))){
         if (input.length() == 4){ //If the frequency is greater than 1-9 then use this.
            String value = input.charAt(2) + "" + input.charAt(3);
            Integer integer = Integer.valueOf(value);
            charList.add(input.charAt(0));
            charFrequency.add(integer);
         }
         else{ //If the frequency is less 9 or less then use this. 
            charList.add(input.charAt(0));
            String value = input.charAt(2) + "";
            Integer integer = Integer.valueOf(value);
            charFrequency.add(integer);
         }
      }
      else{
         System.out.println("ERROR FILE IS NOT A FREQUENCY TABLE!!");
      }
   } //End Conversion() method




   /**
    * huffman() method calls the huffman() class and converts the frequency table into the encoded format. 
    */

   static void huffman() throws IOException{
      Huffman huffClass = new Huffman();
      huffClass.start(charList, charFrequency);
   }//End huffman() method




   /**
    * The choice() asks the user for what they are trying to do. If it is encoding from plain text to cipher
    * text then they input 1, if it is cipher text to plain text then they input 2. An error message is presented
    * if they input anything else. 
    */

   static void choice(List<String> encoding, List<Character> letters) throws IOException{
      huffmanCode = encoding;
      huffmanLetters = letters;
      System.out.println("To encode a string input, type in 1. To convert cipher text to a string, type 2.");    
      Scanner input = new Scanner(System.in); //Allow user input    
      int userChoice = input.nextInt(); 
      if (userChoice == 1)
      {
         EncodingClass encodingClass = new EncodingClass();
         encodingClass.fileInput(huffmanCode, huffmanLetters); //Run encodingClass and pass huffmanCode/letters
      }
      else if (userChoice == 2){
         DecodingClass decodingClass = new DecodingClass();
         decodingClass.fileInput(huffmanCode, huffmanLetters); //Run encodingClass and pass huffmanCode/letters
      }
      else{
         System.out.println("ERROR!!! NUMBER MUST EQUAL 1 OR 2 TRY AGAIN!");
      }
   } //choice() method




   /**
    * The main() method asks the user for the frequency table file. If the file does not exist, the user
    * is shown an error message. 
    */

   public static void main(String[] args) throws IOException
   {
      //Ask for input file directory from user
      System.out.println("Please provide the input file directory for Frequency Table:");    
      Scanner input = new Scanner(System.in);
      String inputString = input.nextLine();

      File file = new File(inputString);
      //Start reading the input file
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
         String line;
  
         //While line in input string does not equal empty, continue running
         while ((line = br.readLine()) != null) {
            try{
               conversion(line);   //Start conversion function
               }
            catch (NoSuchElementException exceptionError){   //Catch empty list exception
               String errorEmpty = "ERROR! Queue is empty!";
               System.out.println(errorEmpty);
            }
         }
         huffman();
      }
      catch (FileNotFoundException exception) { //Catch incorrect user input file
         System.out.println("ERROR!!! Incorrect file name!!");
      }       
   } //End main() method
}   //End BaseClass
