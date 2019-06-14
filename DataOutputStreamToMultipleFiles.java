import java.io.*;
import java.net.*;
import java.util.*;

public class DataOutputStreamToMultipleFiles {
   private DataOutputStream outputFile;
   private String fileName = "";
   DataOutputStreamToMultipleFiles(String fileName) throws IOException{
   this.fileName = fileName;
   this.outputFile = new DataOutputStream(new FileOutputStream(this.fileName));
   }
   
   public DataOutputStream getCreatedFile() {return this.outputFile;}
         
}