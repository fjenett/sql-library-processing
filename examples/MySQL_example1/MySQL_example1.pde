/**
 *    MySQL example 1
 *    https://github.com/fjenett/sql-library-processing
 *
 *    created 2005-05-10 by fjenett
 *    updated fjenett 20130718
 *    tested with Processing 2.0.x
 */

import de.bezier.data.sql.*;

MySQL msql;


void setup()
{
    size( 100, 100 );
	
    // this example assumes that you are running the 
    // mysql server locally (on "localhost")
    
    // 1
    // replace your mysql login:
    
    String user     = "sql_user";
    String pass     = "sql_pass";

    // 2
    // name of the database to use
    
    String database = "sql_library_test_db";
    
    // add additional parameters like this:
    // sql_library_test_db?useUnicode=true&characterEncoding=UTF-8

    // 3
    // connect to database of server "localhost"
    
    msql = new MySQL( this, "localhost", database, user, pass );
    
    if ( msql.connect() )
    {
        msql.query( "SELECT COUNT(*) FROM %s", "example1" );
        msql.next();
        println( "this table has " + msql.getInt(1) + " number of rows" );
    }
    else
    {
        // connection failed !
        
        // - check your login, password
        // - check that your server runs on localhost and that the port is set right
        // - try connecting through other means (terminal or console / MySQL workbench / ...)
    }
}

void draw()
{
    // i know this is not really a visual sketch ...
}
