//Title: LISPCodeChecker.java
//Author: Angel J. Martinez-Perez
//Program: Takes in a string as an input and returns true if all the parentheses in the string are properly closed and nested.
//Date: June 14, 2019

//Assumptions and logic: 
//1. As a result of lack of knowledge of the LISP programming language, it is assumed that all LISP codes are supposed to start with an opening parenthesis
//1. cont. and LISP codes are supposed the end with a closing parenthesis 
//2. ultimately the number of opening parentheses matches the number of closing parentheses
//3. Opening parentheses should always be >= closing parentheses until the end.  If at any time closing parentheses > opening parentheses,
//3. cont. the is a missing opening parentheses and the expected pattern is broken

import java.util.Scanner;

public class LISPCodeCheckerPROTOTYPE{
   public static void main(String[] args){
   //Declare and initialize variables
   String lISPCodeString = "";
   //The counter variables below will be used to keep count of opening and closing parentheses in the string
   int openingParenthesesCounter = 0;
   int closingParenthesesCounter = 0;
   //The boolean variable will track if string is still a valid LISP code string 
   boolean isStillValidLISPCodeString = false;
   
   //Declare input variable of type Scanner
   Scanner input = new Scanner(System.in);
   
   //Prompt user for LISP code string input
   System.out.println("Please, enter a string of LISP code for program to check if all parentheses are in place as required: ");
   lISPCodeString = input.nextLine();
   // Converts String to char array in order to be able to iterate and count parentheses
   char[] arrayOfLISPCodeString = lISPCodeString.toCharArray();

   //Check to see of string starts with opening parentheses "(" as LISP codes are assumed to begin (see assumptions in comments above)
   //if the string does not start with "(" and ends with ")", notify user that it is NOT a proper LISP code because it did not start with "(" and did not end with ")"
   if ((lISPCodeString.startsWith("(")) && (lISPCodeString.endsWith(")"))){
   System.out.println("It was verified that the LISP code entered starts as expected with an opening parenthesis \"(\" and ends with a closing \")\" parenthesis.");

   // change value of variable to continue with program
      isStillValidLISPCodeString = true;
      } else {
      System.out.println("The string does NOT start with opening parenthesis \"(\" and does NOT end with a closing \")\" parenthesis as expected from LISP code.");
      System.out.println("It is \"" + isStillValidLISPCodeString + "\" that all the parentheses in the string are properly closed and nested.");

      }   
   
      
   // only if the isStillValidLISPCodeString was changed to true the following section will be performed 
   if (isStillValidLISPCodeString == true){
   
   System.out.println("Checking for number of opening parentheses \"(\" to match number of closing \")\" parentheses.");
   
   for (int i = 0; i < arrayOfLISPCodeString.length; i++){
      if (arrayOfLISPCodeString[i] == '(') {
      openingParenthesesCounter ++;
      }
      if (arrayOfLISPCodeString[i] == ')') {
      closingParenthesesCounter ++;
      }
   }
   // Checks to see if the respective parentheses counter variable match 
   if (openingParenthesesCounter != closingParenthesesCounter){
      // change value of variable to stop program
      isStillValidLISPCodeString = false;
      System.out.println("The number of opening parentheses \"(\" does NOT match the number of closing \")\" parentheses as expected from LISP code.");
      System.out.println("It is \"" + isStillValidLISPCodeString + "\" that all the parentheses in the string are properly closed and nested.");
   }
   }   
   
   // only if the isStillValidLISPCodeString is still true the following section will be performed 
   if (isStillValidLISPCodeString){
      System.out.println("The number of opening parentheses \"(\" matches the number of closing \")\" parentheses as expected from LISP code.");
      // invoke method that checks if tracker for closing parentheses is always <= to tracker for opening parentheses
      System.out.println("It is \"" + parenthesesTracker(arrayOfLISPCodeString) + "\" that all the parentheses in the string are properly closed and nested.");
   
   }
   
   }
   
   // this method is invoked at the end of the main method to track parentheses sequence and return false if closing parentheses is > opening parentheses at any point in the string
   public static boolean parenthesesTracker(char[] trackingArray){
      //The tracker variables below will be used to keep track that opening parentheses is always >= closing parentheses until the end of the string
      int openingParenthesesTracker = 0;
      int closingParenthesesTracker = 0;
      //
      for (int i = 0; i < trackingArray.length; i++){
         if (trackingArray[i] == '(') {
         openingParenthesesTracker ++;
         }
         if (trackingArray[i] == ')') {
         closingParenthesesTracker ++;
            if (closingParenthesesTracker > openingParenthesesTracker)
               return false;
         }
      }
      return true;
   }
}
