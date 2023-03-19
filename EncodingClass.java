import java.util.*;
import java.lang.*;
import java.io.*;

/**
* The EncodingClass takes a user input and breaks down the plain text string into characters.
* Characters are then compared to the the generated character/huffman code and an encoded string
* is written to an output file.  
* 
* @preconditions 1: input file (plain text)
*  
* @post condition: output file (cipher text)
* 
* @author Daniel Chisner
*/

public class EncodingClass {
	static List<String> huffmanCode = new ArrayList<>(); //Store huffman code
	static List<Character> characters = new ArrayList<>(); //Store characters for huffman code 

    /**
    * The fileInput() method starts the program and saves the huffman encoding and letters to ArrayLists
    */

	static void fileInput(List<String> encoding, List<Character> huffmanLetters) throws IOException{
		huffmanCode = encoding;
		characters = huffmanLetters;
		start();
	}//End fileInput() method




    /**
    * The encodeString() method takes the input string and iterates through it character 
    * by character. Each character is compared to the HuffmanCode letters and if equal
    * the respective huffmanCode is added to the finalString variable. 
    */

	static String encodeString(String line) throws IOException{

		//Respective variables are initialized. 
		String stringLine = line.toUpperCase(), finalString = "", spaceString = " ", 
		periodString = ".", questionString = "?", commaString = ",", reversedString = "";
		int charactersLength = characters.size(), length = stringLine.length();
		char period = periodString.charAt(0), space = spaceString.charAt(0), 
		question = questionString.charAt(0), comma = commaString.charAt(0);

		//Iterates through string character by character.
		for (int i = length - 1; i >= 0; i--){
			if (stringLine.charAt(i) == space){
            	finalString = " " + finalString;
         }

         //If equal to ".", ",", or "?" remove it
         else if (stringLine.charAt(i) == period || stringLine.charAt(i) == comma 
           	|| stringLine.charAt(i) == question){
           	finalString = finalString + "";
         }

         //Compare the letters with the characters in the Huffman list
         //If true, add to the finalString variable. 
         else{
				for (int n = charactersLength - 1; n >= 0; n--){
					if (characters.get(n) == stringLine.charAt(i)){
						finalString = huffmanCode.get(n) + finalString;
					}
				}
         }
		}
		return finalString;
	}




    /**
    * The write() method writes to an output file what the plain text and its generated cipher text is. 
    */

   static void write(String outputStringSave, File fileName, String line) throws IOException{
      FileWriter fw = new FileWriter(fileName, true);
      fw.write("Plain Text: " + line + "\n"+ "Cipher Text: " + outputStringSave + "\n\n");
      fw.close();
   }




   /**
    * The start() method asks the user for the input file. If the file does not exist, the user
    * is shown an error message. 
    */

	static void start() throws IOException {
    	//Ask for input file directory from user
 		System.out.println("Please provide the input file directory:");    
      Scanner input = new Scanner(System.in);
      String inputString = input.nextLine();

      //Ask for output file directory from user
      System.out.println("Please provide the output file directory:");   
      Scanner output = new Scanner(System.in);
      String outputString = output.nextLine();

      File file = new File(inputString);
      //Start reading the input file
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
         String line;

         //While line in input string does not equal empty, continue running
         while ((line = br.readLine()) != null) {
            try{
               File fileName = new File(outputString);   
               write(encodeString(line), fileName, line);   //Start write function
            }
            catch (NoSuchElementException exceptionError){   //Catch empty stack exception
               String errorEmpty = "ERROR!!";
               File fileName = new File(outputString); 
               write(errorEmpty, fileName, line);   //Start write function
            }
         }
      }
      catch (FileNotFoundException exception) { //Catch incorrect user input file
         System.out.println("ERROR!!! Incorrect user input or output!!");
      }
	}//End start() method
}//End EncodingClass