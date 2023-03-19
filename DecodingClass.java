import java.util.*;
import java.lang.*;
import java.io.*;
 
/**
* The DecodingClass takes a user input and compares the encoded text to the huffmanCode list. 
* If equal, the respective letter is added to String called StringToBeDecoded. 
* 
* @preconditions 1: input file (plain text)
*  
* @post condition: output file (cipher text)
* 
* @author Daniel Chisner
*/

public class DecodingClass {
	static List<String> huffmanCode = new ArrayList<>(); //Store huffman code
	static List<Character> characters = new ArrayList<>(); //Store characters for huffman code 
   static Stack<Character> stack = new Stack<Character>(); //Initialize stack of characters
   static String stringToBeDecoded = ""; //Make a string to build out the final sentence

   /**
   *The fileInput() method starts the program and saves the huffman encoding and letters to ArrayLists
   */
	static void fileInput(List<String> encoding, List<Character> huffmanLetters) throws IOException{
		huffmanCode = encoding;
		characters = huffmanLetters;
		start();
	}//End fileInput() method




   /**
   *The decodeString() takes the input string and compares sections of the string to huffman 
   *encoded list. If it is equal, the respective character is pushed onto a stack and the 
   *characters are deleted from teh stringToBeDecoded String. The loop restarts and begins 
   *comparing sections of the string until the string equals 0 characters long. 
   * 
   * NOTE: To prevent the code from crashing the computer due to the while loop never breaking 
   * if the user input is incorrect, if the String is shorter than the huffmanCode it is being 
   * compared to, an if statement is run and a counter is started. Once the counter runs through 
   * all 26 codes in the huffmanCode list, the for/while loops are broken. 
   */

	static String decodeString(String line) throws IOException{
		//Initialize variables
		stringToBeDecoded = line;
		int counter = 0;
		String huffmanCodeString = "", finalString = "", question = "?";
		char questionMark = question.charAt(0);

		//If question mark, delete the character. 
		stringToBeDecoded = stringToBeDecoded.replace(question, "");

		//Run while loop until the stringToBeDecoded reaches 0 characters long
		while (stringToBeDecoded.length() != 0){
			for (int i = huffmanCode.size() - 1; i >= 0; i--){ //iterate through huffmanCode list
				huffmanCodeString = huffmanCode.get(i);

				//if stringToBeDecoded is too short, every comparison is ran and if no match, the 
				//for loop is broken. 
				if (stringToBeDecoded.length() < huffmanCodeString.length()){
					counter++;
					if (counter <= 26){
						;
					}
					else{
						break;
					}
				} 

				else{ //compare parts of the string that match up to the length of the respective huffmanCode 
					String sectionOfString = stringToBeDecoded.substring(0, huffmanCodeString.length());
				
					if (sectionOfString.equals(huffmanCodeString)){ //if equal push the respective char onto stack
						stack.push(characters.get(i));
						StringBuffer sb = new StringBuffer (stringToBeDecoded);
						sb.delete(0, huffmanCodeString.length()); //delete portion of string that was compared from String
						stringToBeDecoded = sb.toString();
						break;
					}
				}
			}
         if (finalString == ""){ //If the input failed accross the board show invalid input. 
          	if (stack.isEmpty()){
           		System.out.println("INVALID INPUT!!");
           		finalString = "INVALID INPUT!!";
           	}
           	else{ //else pop stack to start the finalString. 
           		finalString = "" + stack.pop();
           	}
         }
         else{
           	if (stack.isEmpty()){ //if stack is empty but the while loop is still running show error message.
           		System.out.println("ERROR! CHARACTERS REMAINING!");
           		break;
           	}
           	else{
           		finalString = finalString + stack.pop(); //else continue adding characters to finalString. 
           	}
         }
		}
		return finalString; //return finalString for writing to output file
	}//End decodeString() method




    /**
    * The write() method writes to an output file what the plain text and its generated cipher text is. 
    */

   static void write(String outputStringSave, File fileName, String line) throws IOException{
      FileWriter fw = new FileWriter(fileName, true);
      fw.write("Cipher Text: " + line + "\n"+ "Plain Text: " + outputStringSave + "\n\n");
      fw.close();
   }//End write() method




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
               write(decodeString(line), fileName, line);   //Start write function
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
}//End DecodingClass