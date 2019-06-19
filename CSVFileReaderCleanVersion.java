//Title: CSVFileReaderCleanVersion.java
//Author: Angel J. Martinez-Perez
//Program: Reads the content of a CSV file and separate enrollees by insurance company in its own file
//Additionally, sort the contents of each file by last and first name (ascending).  Lastly, if there are duplicate User Ids for the same Insurance Company,
//then only the record with the highest version should be included.
//Date: June 19, 2019

import java.io.*;
import java.util.*;

public class CSVFileReaderCleanVersion {
   public static void main (String [] args) throws IOException{
   
   // input file name variable
   String fileName = "";
   
   // Create String variable to hold file name from HashSet<String> set InsuranceCompanyFileName
   String insuranceCompanyFileName = "";
   String insuranceCompanyName = "";
   
   //Declare input variable of type Scanner
   Scanner input = new Scanner(System.in);
      
   //Create variable of type ArrayList<string> to store reconstructed Strings that will be sorted later 
   ArrayList<String> arrayListOfReconstructedStrings = new ArrayList<>();
   
   //Create variable of type HashSet<String> to store Insurance Company names to help create respective files for each Insurance Company
   HashSet<String> setInsuranceCompanyFileName = new HashSet<>();
      
   //Prompt user to enter file name
   System.out.println("Please enter CSV file name that resides in the C:\\ drive:");
   fileName = input.nextLine();
   
   //Makes sure the user entered the file with .csv extension.  Otherwise, concatenate .csv extension to the fileName input
   if (fileName.contains(".csv")){
      fileName = fileName;
      } else {
         fileName = fileName.concat(".csv");
      }
   
   try (
      //Create and input stream for the file
      FileInputStream file = new FileInputStream("c:\\" + fileName);
      DataInputStream inputFile = new DataInputStream(file);
      ){
      //Read from provided CSV file
      String line = inputFile.readLine();
      String firstLine = line;
      String [] firstLineSplit = firstLine.split(",");
      String [] firstLineSplitFirstandLastName = firstLineSplit[1].split(" ");
      
      //Store last name and first name separately for future re-construction
      String lastName = firstLineSplitFirstandLastName[1];
      String firstName = firstLineSplitFirstandLastName[0];
      
      //Reconstruct into a one-line string but starting with lastname so that it can be sorted by last name and later be de-constructed again
      String reconstructedWithLastNameFirst = lastName + "," + firstName + "," + firstLineSplit[0] + "," + firstLineSplit[2] + "," + firstLineSplit[3];
      
      //Add reconstructed string to variable of type ArrayList<string> to store reconstructed Strings that will be sorted later
      arrayListOfReconstructedStrings.add(reconstructedWithLastNameFirst); 
      
      //Add first InsuranceCompany in first line to HashSet
      setInsuranceCompanyFileName.add(firstLineSplit[3]);

      //Initiate while loop to continue reading from file as long as line is NOT null 
      while(line != null){
         line = inputFile.readLine();
      
         // Create string array to hold values from a line of input after spliting the separating commas
         if (line != null){
            String [] lineArray = line.split(",");
         
            // Since Availity wants this to be sorted by "last and first name(ascending)", I assumed there is a " "(blank space) in between and split the 2nd 
            // item in the line array which is First and Last Name.  This can later be sorted in a Collections.sort method after placing last name first in the 
            // reconstructed String
            String [] splitFirstandLastName = lineArray[1].split(" ");
         
            //Store last name and first name separately for future re-construction
            lastName = splitFirstandLastName[1];
            firstName = splitFirstandLastName[0];
                  
            //Reconstruct into a one-line string but starting with lastname so that it can be sorted by last name 
            reconstructedWithLastNameFirst = lastName + "," + firstName + "," + lineArray[0] + "," + lineArray[2] + "," + lineArray[3];
                  
            //Add reconstructed string to variable of type ArrayList<string> to store reconstructed Strings that will be sorted later
            arrayListOfReconstructedStrings.add(reconstructedWithLastNameFirst); 
            
            //Add Insurance Company name to a HashSet<String> to later help create output files for each Insurance Company
            //HashSets do not store duplicates so only unique Insurance Company names will be stored 
            setInsuranceCompanyFileName.add(lineArray[3]);
         
         }//End of If conditional statement
      }//End of While loop
         } catch (EOFException ex) { // Catch end-of-file exception
         System.out.println("End of File");
         }// End of try catch       
      
      // Proceed to sort arrayListOfReconstructedStrings by last and first name (ascending) as requested by Availity
      Collections.sort(arrayListOfReconstructedStrings);
            
      //Two duplicate ArrayList<InsuranceUser> type (of type InsuranceUser) have to be created first
      // The complete list includes all the InsuranceUser objects and will retain all the objecgs 
      ArrayList<InsuranceUser> completeListOfInsuranceUsersBase = new ArrayList<>(); 
      //The noDuplicate list include all the InsuranceUser objects at first but dublicate User IDs
      //for the same insurance company that have a lower  version will be removed
      ArrayList<InsuranceUser> noDuplicateListOfInsuranceUsers = new ArrayList<>();
      
      //Instantiate the InsuranceUser objects with each of the data strings from the input file with separated 
      //last name from first name and add the objects to the two array lists 
      // InsuranceUser records are instantiated and stored in ArrayLists that will later be compared to delete duplicates from noDuplicate...
      for (int i = 0; i < arrayListOfReconstructedStrings.size(); i++){
         String [] reconstructedStringArray = arrayListOfReconstructedStrings.get(i).split(",");
            
         // This same for loop goes through all the strings and uses the arguments to assign values to the fields in each Class object
         
         //Need to convert String version into an integer named versionInt 
         int versionInt = Integer.parseInt(reconstructedStringArray[3]);
      
         // The are all added to both lists at first 
         completeListOfInsuranceUsersBase.add(new InsuranceUser(reconstructedStringArray[0], reconstructedStringArray[1], reconstructedStringArray[2],
            versionInt, reconstructedStringArray[4]));
         noDuplicateListOfInsuranceUsers.add(new InsuranceUser(reconstructedStringArray[0], reconstructedStringArray[1], reconstructedStringArray[2],
            versionInt, reconstructedStringArray[4]));
      }// end of for loop
      
      // Availity requires that for duplicate User Ids for the same insurance Company, only the record with the highest 
      // version data point should be included in the file
      // As such each string will be used to create an object of type InsuranceUser to the CompareTo method implemented in that Class can be used

      // outer for loop that will be used to compare the InsuranceUser objects stored in ArrayLists 
      for (int insuranceUserOuterLoopCount = 0; insuranceUserOuterLoopCount < completeListOfInsuranceUsersBase.size(); insuranceUserOuterLoopCount ++){
         // inner for loop will be used to remove from noDuplicateListOfInsuranceUser those that are duplicates and have lower version number
          for (int insuranceUserInnerLoopCount = 0; insuranceUserInnerLoopCount < noDuplicateListOfInsuranceUsers.size(); insuranceUserInnerLoopCount ++){ 
            
            //Conditional that determines which InsuranceUser objects to remove from noDuplicateListOfInsuranceUser
            if ((completeListOfInsuranceUsersBase.get(insuranceUserOuterLoopCount).compareTo(noDuplicateListOfInsuranceUsers.get(insuranceUserInnerLoopCount)) == 0) && 
            (completeListOfInsuranceUsersBase.get(insuranceUserOuterLoopCount).getVersion() >
            (noDuplicateListOfInsuranceUsers.get(insuranceUserInnerLoopCount).getVersion()))){
            //If a duplicate insurance record was found, it is removed from the noDuplicateListOfInsuranceUsers
            noDuplicateListOfInsuranceUsers.remove(insuranceUserInnerLoopCount); 
         
            } else {
            // do nothing
            }//end of if else
         }// end of inner loop
      }// end of outer loop
      
              
       //transfer HashSet<String> setInsuranceCompanyFileName to insuranceCompanyFileNameArray to facilitate looping further below
       String [] insuranceCompanyFileNameArray = new String[setInsuranceCompanyFileName.size()];
       insuranceCompanyFileNameArray = setInsuranceCompanyFileName.toArray(insuranceCompanyFileNameArray);
       
       //create insuranceCompanyFileNameWithCSV to later add ".csv" extension to the file names extracted from HashShet<String>
       String [] insuranceCompanyFileNameWithCSV = new String[insuranceCompanyFileNameArray.length];
       
                     
       //concatenate the file extension ".csv" to insuranceCompanyFileNameArray and update strings in insuranceCompanyFileNameWithCSV array
       for (int i = 0; i < insuranceCompanyFileNameArray.length; i++){
         insuranceCompanyFileNameWithCSV[i] = insuranceCompanyFileNameArray[i].concat(".csv"); 
       }//end of for loop 
       
       //find separator to help split output into separate rows 
       //the data was coming up in the file but only in one long row of Strings
       //inserting this property will cause output to file to jump to the next line after input
       String newLine = System.getProperty("line.separator");

                
       //Outer for loop to go through each Insurance Company name in insuranceCompanyFileNameArray and send data to respective files
       for (int insuranceCompanyFileCounter = 0; insuranceCompanyFileCounter < insuranceCompanyFileNameArray.length; insuranceCompanyFileCounter++){
           
           //Instantiate an object to type DataOutputStream with a name that matches the Insurance Company name in this outer for loop
           DataOutputStreamToMultipleFiles outputToFile = new DataOutputStreamToMultipleFiles(insuranceCompanyFileNameWithCSV[insuranceCompanyFileCounter]);
                                            
           //Inner for  loop needed to iterate through objects of type InsuranceUser
            for (int insuranceCompanyFileInnerLoopCounter = 0; insuranceCompanyFileInnerLoopCounter < noDuplicateListOfInsuranceUsers.size(); 
               insuranceCompanyFileInnerLoopCounter ++){
               
               //If the Insurance Company name matches the name of the Insurance Company file in the outer for loop,
               //stream data to the Insuranc Company file in outer for loop
               if ((noDuplicateListOfInsuranceUsers.get(insuranceCompanyFileInnerLoopCounter).getInsuranceCompany()).equals( 
                  insuranceCompanyFileNameArray[insuranceCompanyFileCounter])){
                  outputToFile.getCreatedFile().writeUTF(noDuplicateListOfInsuranceUsers.get(insuranceCompanyFileInnerLoopCounter).toString());
                  
                  //makes output to file jump to the next row instead of placing all the data in one row
                  //variable newLine was initiated above outer for loop
                  outputToFile.getCreatedFile().writeBytes(newLine);
                  
                  } else {
               //do nothing
               }// end of if else    
            }// end of inner loop
            
            //Flush and close the file connection
            outputToFile.getCreatedFile().flush();
            outputToFile.getCreatedFile().close();
            
         }// end of outer loop
   //Display number of files that were created, which matches number of unique Insurance Company names
   System.out.println("The number of files created is: " + insuranceCompanyFileNameArray.length);
   
   System.out.println("The names of the files are: ");
   //Displays the names of all the files that were created
   for (String eachFile : insuranceCompanyFileNameArray)
      System.out.println(eachFile);
   
                        
   }//end of main method
                  
}
   
   
 