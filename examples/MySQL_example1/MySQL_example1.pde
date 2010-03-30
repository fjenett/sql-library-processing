import de.bezier.data.sql.*;


// created 2005-05-10 by fjenett
// updated fjenett 20081129


MySQL msql;


void setup()
{
    size( 100, 100 );
	
    // this example assumes that you are running the 
    // mysql server locally (on "localhost").
    //
	
    // replace --username--, --password-- with your mysql-account.
    //
    String user     = "root";
    String pass     = "admin";
	
    // name of the database to use
    //
    String database = "bildwelt";
    // add additional parameters like this:
    // bildwelt?useUnicode=true&characterEncoding=UTF-8
	
    // connect to database of server "localhost"
    //
    msql = new MySQL( this, "localhost", database, user, pass );
    
    if ( msql.connect() )
    {
        msql.query( "SELECT COUNT(*) FROM image" );
        msql.next();
        println( "number of rows: " + msql.getInt(1) );
    }
    else
    {
        // connection failed !
    }
}

void draw()
{
    // i know this is not really a visual sketch ...
}
