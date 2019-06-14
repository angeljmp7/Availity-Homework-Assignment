//Title: InsuranceUser (class file)
//Author: Angel J. Martinez-Perez
//Program: Class file to create data structure for InsruranceUsers
//Date: June 14, 2019

import java.io.Serializable;

public class InsuranceUser implements Serializable, Comparable<InsuranceUser> {

   private String lastName;
   private String firstName;
   private String userID;
   private int version;
   private String insuranceCompany;

   // default constructor   
   InsuranceUser (){
      this.lastName = "";
      this.firstName = "";
      this.userID = "";
      this.version = 0;
      this.insuranceCompany = "";
   }

   // constructor which accepts 4 args to set properties
   InsuranceUser (String lastName, String firstName, String userID, int version, String insuranceCompany){
      this.lastName = lastName;
      this.firstName = firstName;      
      this.userID = userID;
      this.version = version;
      this.insuranceCompany = insuranceCompany;
   }
   
   // accessors
    public String getLastName() { return this.lastName; }
    public String getFirstName() { return this.firstName; }
    public String getUserID() { return this.userID; }    
    public int getVersion() { return this.version; }
    public String getInsuranceCompany() {return this.insuranceCompany;}
    
    // mutators
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setUserID(String userID) { this.userID = userID; }    
    public void setVersion(int version) {this.version = version;}
    public void setInsuranceCompany(String insuranceCompany) {this.insuranceCompany = insuranceCompany;}

    public String toString()
    {
        return this.userID + "," + this.lastName + "," + this.firstName + "," + this.version + "," + this.insuranceCompany;
    }
    
   @Override // Implement the compareTo method defined in Comparable interface
   // if an InsuranceUser has the same user ID AND the insurance ompany is the same, returns 0.  Otherwise,  returns -1.  
    public int compareTo(InsuranceUser iu)
    {
      if ((this.getUserID().equals(iu.getUserID())) && (this.getInsuranceCompany().equals(iu.getInsuranceCompany())))
         return 0;
      else 
         return -1;
    }
}