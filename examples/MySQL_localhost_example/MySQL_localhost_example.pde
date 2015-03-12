// Library by fjenett
// Example created on 2014-03-12 by Powder
// Tested with Processing 3.0a5

import de.bezier.data.sql.*;

String dbHost = "localhost"; // if you are using a using a local database, this should be fine
String dbPort = "8889"; // replace with database port, MAMP standard is 8889
String dbUser = "root"; // replace with database username, MAMP standard is "root"
String dbPass = "root";  // replace with database password, MAMP standard is "root"
String dbName = "database_name"; // replace with database name
String tableName = "table_name"; // replace with table name

MySQL msql;

void setup() { 
  msql = new MySQL( this, dbHost + ":" + dbPort, dbName, dbUser, dbPass );

  if (msql.connect()) {
    // get number of rows
    msql.query("SELECT COUNT(*) FROM " + tableName);
    msql.next();
    println("Number of rows: " + msql.getInt(1));
    println();
    
    // access table
    msql.query("SELECT * FROM " + tableName);
    while (msql.next()){
      // replace "first_name" and "last_name" with column names from your table
      String s1 = msql.getString("first_name");
      String s2 = msql.getString("last_name");
      println(s1 + " " + s2);
    }
  } else {
    println("Yikes, there was an error!");
  }
}
