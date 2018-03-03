package com.incedoinc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class PostgreSQLJDBCCreate {
   public static void main( String args[] ) {
      Connection c = null;
      Statement stmt = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/postgres",
            "manish", "tomar");
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         /*String sql = "CREATE TABLE COMPANY " +
            "(ID INT PRIMARY KEY     NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " AGE            INT     NOT NULL, " +
            " ADDRESS        CHAR(50), " +
            " SALARY         REAL)";*/
         
        /* String sql = "CREATE TABLE Patient_Visit_Clinical_Data " + 
         "( Visit_ID INT PRIMARY KEY NOT NULL," + 
         "Patient_ID  INT NOT NULL, " + 
         "Site_Investigator_ID INT NOT NULL, " + 
         "Urobilinogen TEXT, " + 
         "Bilirubin TEXT, " + 
         "Ketone TEXT, " + 
         "Blood TEXT, " + 
         "Protien TEXT, " + 
         "Nitrile TEXT, " + 
         "Leukocytes TEXT, " + 
         "SpecificGravity TEXT, " + 
         "PH TEXT, " + 
         "Microalbumin TEXT, " + 
         "min TEXT)" ;*/
         
         String sql = "CREATE TABLE Patient_Visit_Clinical_Data " + 
                 "( Visit_ID SERIAL PRIMARY KEY NOT NULL," + 
                 " Data JSON)" ;
         
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table created successfully");
   }
}