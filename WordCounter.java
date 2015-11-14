import java.io.*;
import java.util.*;

/**
 *  A counter used to count number of words, characters, and lines from
 *  a given text.
 * This is a modification of the original code by Sugiharto Widjaja
 *  @author Tomisin Jenrola
 *  @version 31/10/15
*/

public class WordCounter
{
   
   private String text;          // The given text  
   private String selected;      // selected text
   private int numOfWords;       // Number of words
   private int numOfChars;       // Number of characters
   private int numOfLines;        // Number of lines
   private int numOfSelectedWords; // Number of selected words
   private int numOfSelectedCharacters; // Number of selected words

   /**
    *  Consruct the word counter
    * @param text the given text
   */
   public WordCounter(String text, String selected)
   {

      this.text = text;
      numOfWords = 0;
      numOfChars = 0;
      numOfLines = 0;
      numOfSelectedWords = 0;
      numOfSelectedCharacters = 0;
      if(selected != null) {
         this.selected = selected;
      } else {
         this.selected = "";
      }
   }

   /**
    * Count the number of words and characters
    */
   public void countWord() {
      StringTokenizer tokenizer = new StringTokenizer(text, " \n");
      while(tokenizer.hasMoreTokens())
      {
         numOfWords++;
         String token = tokenizer.nextToken();
         numOfChars += token.length();
      }
   }

   /**
    *  Count the number of lines
    */
   public void countLine() {
      StringTokenizer tokenizer = new StringTokenizer(text, "\n");
      while(tokenizer.hasMoreTokens())
      {
         String token = tokenizer.nextToken();
         numOfLines++;
      }
   }

   /**
    * Count the number of characters and words selected
    */
   public void countSelected() {   
      StringTokenizer tokenizer = new StringTokenizer(selected, " ");
      while(tokenizer.hasMoreTokens()) {
         numOfSelectedWords++;
         String token = tokenizer.nextToken();
         numOfSelectedCharacters += token.length();
      }
   }

   /**
    * Return the number of words
    * @return number of words
    */
   public int getNumOfWords() {
      return numOfWords;
   }

   /**
    * Return the number of characters
    * @return number of characters
    */
   public int getNumOfChars() {
      return numOfChars;
   }

   /**
    *  Return the number of lines
    *  @return number of lines
    */
   public int getNumOfLines() {
      return numOfLines;
   }

   public int getNumOfSelectedWords() {
      return numOfSelectedWords;
   }

   public int getNumOfSelectedCharacters() {
      return numOfSelectedCharacters;
   }

}
