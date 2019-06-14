//Title: CSVFileReader.java
//Author: Angel J. Martinez-Perez
//Program: Reads the content of a CSV file and separate enrollees by insurance company in its own file
// Additionally, sort the contents of each file by las and first name (ascending).  Lastly, if there are duplicate User Ids for the same Insurance Company,
// then only the record with the highest version should be included.
//Date: June 14, 2019

import java.io.*;
import java.net.*;
import java.util.*;

public class CSVFileReader {
   public static void main (String [] args) throws IOException{
   
   // input file name variable
   String fileName = "";
   
   // Create String variable to hold file name from HashSet<String> set InsuranceCompanyFileName
   String insuranceCompanyFileName = "";
   String insuranceCompanyName = "";

   
   //Declare input variable of type Scanner
   Scanner input = new Scanner(System.in);
      
   // create variable of type ArrayList<string> to store reconstructed Strings that will be sorted later 
   ArrayList<String> arrayListOfReconstructedStrings = new ArrayList<>();
   
   // create variable of type HashSet<String> to store Insurance Company names to help create respective files for each Insurance Company
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
      // read from provided CSV file
      String line = inputFile.readLine();
      String firstLine = line;
      String [] firstLineSplit = firstLine.split(",");
      String [] firstLineSplitFirstandLastName = firstLineSplit[1].split(" ");
      
      // store last name and first name separately for future re-construction
      String lastName = firstLineSplitFirstandLastName[1];
      String firstName = firstLineSplitFirstandLastName[0];
      
      //Reconstruct into a one-line string but starting with lastname so that it can be sorted by last name and later be de-constructed again
      String reconstructedWithLastNameFirst = lastName + "," + firstName + "," + firstLineSplit[0] + "," + firstLineSplit[2] + "," + firstLineSplit[3];
      
      // add reconstructed string to variable of type ArrayList<string> to store reconstructed Strings that will be sorted later
      arrayListOfReconstructedStrings.add(reconstructedWithLastNameFirst); 
      
      // add first InsuranceCompany in first line to HashSet
      setInsuranceCompanyFileName.add(firstLineSplit[3]);

      //System.out.println(line); 
      while(line != null){
      line = inputFile.readLine();
      // System.out.println(line);
      // Create string array to hold values from a line of input after spliting the separating commas
      if (line != null){
      String [] lineArray = line.split(",");
      
      // Since Availity wants this to be sorted by "last and first name(ascending)", I assumed there is a " "(blank space) in between and split the 2nd 
      // item in the line array which is First and Last Name.  This can later be sorted in a Collections.sort method after placing last name first in the 
      // reconstructed String
      String [] splitFirstandLastName = lineArray[1].split(" ");
      // store last name and first name separately for future re-construction
      lastName = splitFirstandLastName[1];
      firstName = splitFirstandLastName[0];
            
      //Reconstruct into a one-line string but starting with lastname so that it can be sorted by last name and later be de-constructed again
      reconstructedWithLastNameFirst = lastName + "," + firstName + "," + lineArray[0] + "," + lineArray[2] + "," + lineArray[3];
            
      // add reconstructed string to variable of type ArrayList<string> to store reconstructed Strings that will be sorted later
      arrayListOfReconstructedStrings.add(reconstructedWithLastNameFirst); 
      
      // add Insurance Company name to a HashSet<String> to later help create output files for each Insurance Company
      setInsuranceCompanyFileName.add(lineArray[3]);
      
      }
      }
      } catch (EOFException ex) { // Catch end-of-file exception
         System.out.println("End of File");
      }      
      
      // Proceed to sort arrayListOfReconstructedStrings by last and first name (ascending) as requested by Availity
      Collections.sort(arrayListOfReconstructedStrings);
      //System.out.println(setInsuranceCompanyFileName);      
      
      // Two duplicate ArrayList<InsuranceUser> type (of type InsuranceUser) have to be created first
      // the complete list includes all the objects
      ArrayList<InsuranceUser> completeListOfInsuranceUsersBase = new ArrayList<>(); 
      // the noDuplicate list will exclude dublicate User IDs for the same insurance and only keep the one with the highest version
      ArrayList<InsuranceUser> noDuplicateListOfInsuranceUsers = new ArrayList<>();
      // create ArrayList<Integer> to store the index of duplicates to be erased
      ArrayList<Integer> indexTracker = new ArrayList<>();
      
      // Initiate post extraction and sorting by merging Last name and first name after being sorted and returning data to its original data points
      // Namely: User Id (String), First and Last Name(String)
      for (int i = 0; i < arrayListOfReconstructedStrings.size(); i++){
         String [] reconstructedStringArray = arrayListOfReconstructedStrings.get(i).split(",");
         //System.out.println(reconstructedStringArray[0] + " " + reconstructedStringArray[1] + " " 
         //+ reconstructedStringArray[2] + " " + reconstructedStringArray[3] + " " + reconstructedStringArray[4]);
   
         // Availity requires that for duplicate User Ids for the same insurance Company, only the record with the highest 
         // version data point should be included in the file
         // As such each string will be used to create an object of type InsuranceUser to the CompareTo method implemented in that Class can be used
         // This same for loop goes through all the strings and uses the arguments to assign values to the fields in each Class object
         // also need to convert String version into an integer 
         int versionInt = Integer.parseInt(reconstructedStringArray[3]);
      
         // InsuranceUser records are instantiated and stored in ArrayLists that will later be compared to delete duplicates from noDuplicate...
         completeListOfInsuranceUsersBase.add(new InsuranceUser(reconstructedStringArray[0], reconstructedStringArray[1], reconstructedStringArray[2],
            versionInt, reconstructedStringArray[4]));
         noDuplicateListOfInsuranceUsers.add(new InsuranceUser(reconstructedStringArray[0], reconstructedStringArray[1], reconstructedStringArray[2],
            versionInt, reconstructedStringArray[4]));
         
                       
      }
      int counter = 0;
      //System.out.println(completeListOfInsuranceUsersBase.size());   
      // outer for loop that will be used to compare the InsuranceUser objects stored in ArrayLists and identify index were the condition is met
      for (int insuranceUserOuterLoopCount = 0; insuranceUserOuterLoopCount < completeListOfInsuranceUsersBase.size(); insuranceUserOuterLoopCount ++){
         //InsuranceUser insuranceUserAtThisPoint = completeListOfInsuranceUsersBase.get(insuranceUserCount);
         for (int insuranceUserInnerLoopCount = 0; insuranceUserInnerLoopCount < noDuplicateListOfInsuranceUsers.size(); insuranceUserInnerLoopCount ++){ 
         //System.out.println(completeListOfInsuranceUsersBase.get(insuranceUserOuterLoopCount).getVersion() + " " + insuranceUserOuterLoopCount + " " + insuranceUserInnerLoopCount );
         if ((completeListOfInsuranceUsersBase.get(insuranceUserOuterLoopCount).compareTo(noDuplicateListOfInsuranceUsers.get(insuranceUserInnerLoopCount)) == 0) && 
            (completeListOfInsuranceUsersBase.get(insuranceUserOuterLoopCount).getVersion() < 
            (noDuplicateListOfInsuranceUsers.get(insuranceUserInnerLoopCount).getVersion()))){
            //If a duplicate insurance record was found, it is removed from the noDuplicateListOfInsuranceUsers
            noDuplicateListOfInsuranceUsers.remove(insuranceUserInnerLoopCount); 
            counter ++;
            } else {
            // do nothing System.out.println("test was NOT met");
            }
         }// end of inner loop
      }// end of outer loop
       //System.out.println("test was met " + counter + " times");
       //System.out.println(noDuplicateListOfInsuranceUsers);
       //System.out.println(completeListOfInsuranceUsersBase);
       //System.out.println(noDuplicateListOfInsuranceUsers.get(5).toString());
       //******************
       //Data has been extracted from the file, sorted and stored in objects of Type InsuranceUser
       //Now it has be restored in files separated by Insurance Company 
       //System.out.println(setInsuranceCompanyFileName.size());
       
       //transfer HashSet<String> setInsuranceCompanyFileName to insuranceCompanyFileNameArray
       String [] insuranceCompanyFileNameArray = new String[setInsuranceCompanyFileName.size()];
       insuranceCompanyFileNameArray = setInsuranceCompanyFileName.toArray(insuranceCompanyFileNameArray);
       String [] insuranceCompanyFileNameWithCSV = new String[insuranceCompanyFileNameArray.length];
       
       //find separator to help split output into separate rows 
       String newLine = System.getProperty("line.separator");
              
       //concatenate the file extension ".csv" to insuranceCompanyFileNameArray and create insuranceCompanyFileNameWithCSV array
       for (int i = 0; i < insuranceCompanyFileNameArray.length; i++){
         insuranceCompanyFileNameWithCSV[i] = insuranceCompanyFileNameArray[i].concat(".csv"); 
       }//end of for loop 
       
       //create for loop to go through each Insurance Company
       
       
       
       
       //create ArrayList of DataOutputStreams for multiple files
       //This below           ********************* did not work 
       //ArrayList<DataOutputStream> dataOutPutStreamFilesList = new ArrayList<>();
       //for (int i = 0; i < insuranceCompanyFileNameWithCSV.length; i++){
         //dataOutPutStreamFilesList.add(new FileOutputStream(insuranceCompanyFileNameWithCSV[i]));
       //}//end of for loop  
       
       //find separator to help split output into separate rows 
       String newLine = System.getProperty("line.separator");

                
       // for loop to go through each Insurance Company name in insuranceCompanyFileNameArray and send data to respective files
       for (int insuranceCompanyFileCounter = 0; insuranceCompanyFileCounter < insuranceCompanyFileNameArray.length; insuranceCompanyFileCounter++){
           //invoke method to create DataOutputStream object
           DataOutputStreamToMultipleFiles outputToFile = new DataOutputStreamToMultipleFiles(insuranceCompanyFileNameWithCSV[insuranceCompanyFileCounter]);
                                            
           // inner loop needed to iterate through objects of type InsuranceUser
            for (int insuranceCompanyFileInnerLoopCounter = 0; insuranceCompanyFileInnerLoopCounter < noDuplicateListOfInsuranceUsers.size(); 
               insuranceCompanyFileInnerLoopCounter ++){
               // If the Insurance Company name matches the name of the file, stream data to file
               if ((noDuplicateListOfInsuranceUsers.get(insuranceCompanyFileInnerLoopCounter).getInsuranceCompany()).equals( 
                  insuranceCompanyFileNameArray[insuranceCompanyFileCounter])){
                  outputToFile.getCreatedFile().writeUTF(noDuplicateListOfInsuranceUsers.get(insuranceCompanyFileInnerLoopCounter).toString());
                  outputToFile.getCreatedFile().writeBytes(newLine);
                  
                  //System.out.println(noDuplicateListOfInsuranceUsers.get(insuranceCompanyFileInnerLoopCounter).toString() + "test");
                  //System.out.println(noDuplicateListOfInsuranceUsers.get(5).toString());

               // *** this below did not work
               //dataOutPutStreamFilesList.get(insuranceCompanyFileCounter).writeUTF(noDuplicateListOfInsuranceUsers.get(insuranceCompanyFileInnerLoopCounter).toString());
               } else {
               //do nothing
               }// end of if else    
            }// end of inner loop
            outputToFile.getCreatedFile().flush();
            outputToFile.getCreatedFile().close();
            
         }// end of outer loop
                        
   }//end of main method
                  
}
   
   
 