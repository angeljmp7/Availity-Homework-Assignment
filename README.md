# Availity-Homework-Assignment
Availity's Fullstack Homework Assignment 

https://www.linkedin.com/in/angel-j-martinez-perez-b95116a8/

Problem # 4.
LISPCodeChecker....java

Assumptions and logic: 
1. As a result of lack of knowledge of the LISP programming language, it is assumed that all LISP codes are supposed to start with an opening parenthesis and LISP codes are supposed the end with a closing parenthesis. 
2. Ultimately the number of opening parentheses matches the number of closing parentheses.
3. Opening parentheses should always be >= closing parentheses until the end.  If at any time closing parentheses > opening parentheses, there is a missing opening parentheses and the expected pattern is broken


Problem # 6.

The file with the main method is the CSVFileReader.java.  Two Class files are needed that support this program.  The first Class is InsuranceUser.java.  This Class contains the fields and methods to manage each insurance record.  It has the standard toString method, but it was overridden to fit the data fields in csv format for future storage accordingly.  It has an implementation of the compareTo method specific for this project.  The second Class file was made necessary to instantiate each file streaming connection to be able to iterate through the records to help determine which record was going into which file.  The name of that class is DataOutputStreamToMultipleFiles.  There might be other solutions for that, but after failing with other solutions, this was the only one that worked for me.  I later realized that I could have tried invoking a method that returned a file connection but I'm going to leave it as is because it worked... Everything that I had done for labs or homework before only required one file connection for input or output.  As such, I struggled to come up with a solution to instantiate individual file connections for each loop under each Insurance Company.  One thing that failed after I thought that was going to work for sure was instantiating connection files and placed them into an ArrayList containing DataOutputStream objects, but I kept getting error messages and gave up on that path.  It was then that I decided to create the class DataOutputStreamToMultipleFiles.   That way I could instantiate objects of type DataOutputStreamToMultipleFiles, which included a unique connection file that I could get with a getter method for each outer for the respective Insurance Company name loop and that worked.  I don't know what else could have worked besides instantiating a class or invoking a method... maybe separate threads?  I'll stick with my solution for now.          

A HashSet<String> data structure, which rejects duplicates, was created to capture each unique Insurance Company name.  There might also be other solutions for that, but after failing with other solutions, this was the only one that worked for me.    

Some assumptions were made.  After the initial line input was split by commas (","), and because the problem required that the contents of each file needed to be sorted by "last and first name", the field with "first and and Last Name" had to be split assuming there was only one space (" ") in between them and there are no customers with more than one first name and more than one last name.  For instance, my full name is Angel Javier Martinez-Perez.  It can get complicated so I assumed that this is an exercise that does not take those things into account.  After splitting those files, a temporary one line string was recreated to be able to sort them while protecting the integrity of the data.  A Collections.sort method was used.  After the data is sorted once, it should remain sorted all the way to the respective files.  That recreated string was then deconstructed into the separate fields.  First and last name was not brought back together because it was assumed that Availity wanted them separated.  Otherwise, why sort by last name, right?    

An object of type InsuranceUser was instantiated for each record.  In fact, two ArrayLists<InsuranceUser> were created.  One was needed to hold all the records and the other to "delete" or remove the records according to Problem 6 requirements.  At the end, the list with the removed records was compared to the array of strings that was created from the HashSet<String> to be able to know which record was heading to which file based on matching Insurance Company names in a nested for loop.  

The assignment does not specify which type of files to create at the end.  Is it a .txt file?  A .dat file? Other .csv files?  Was it supposed to stream the objects (namely the instantiated InsuranceUsers?) or the data as Strings again?  If they needed to be sorted by "first and last" name, I assumed that 5 data fields were expected, and not the original 4.  The assumption was made that Availity wanted separate .csv files.  Therefore, .csv files were created.    

Disclaimers:
There are some improvements that can be made to the code.  First, there is a lot of clutter in the CSVFileReader.java that needs to be cleaned up (I ran out of time but on June 19, 2019 I added CSVFileReaderCleanVersion.java).  Second, files should never be stored in the C: drive for security reasons. As such, the option to enter a path or an Http location could be given to the user when prompted.  I used the C: drive to be able to test the program and because I was using fake data.  During the whole process I had to create my own fake/dummy data files to test my program during different stages.  There was obviously no PII (Personally Identifiable Information) that could be compromised.      

Additionally, the program does not check if the original source file exists with a method like isFile(),etc., in order to prevent program from crashing at the very beggining during file opening sequence.  Moreover, the program does not prompt user to select location of where to store the files (drive or http location), and the program does not check first if the files exist already to help protect existing data or to use a different methodology that allows appending data to existing files.  

Finally, the standard structure of a Class file, namely getters, setters, etc. was not followed for the DataOutputStreamToMultipleFiles Class.  I made it as a quick duct tape solution to get this done quickly as I thought I might miss my deadline.   Some comments inside the Class might have helped communicate its purpose, etc.  

In short, many improvements can be made.  However, the purpose of the exercise was met.  

Note: The topic of JSON had not been covered in class until the week after this submission.  I suspect that there is a way to do this exercise with JSON, but that topic will be covered extensively in July and August during the Java III class that includes J2EE, etc. 
