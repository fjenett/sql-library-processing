import de.bezier.data.sql.*;

// created 2005-05-10 by fjenett
// updated fjenett 20080605


MySQL dbconnection;


void setup()
{
    size( 100, 100 );
	
    // this example assumes that you are running the 
    // mysql server locally (on "localhost").
    //
	
    // replace --username--, --password-- with your mysql-account.
    //
    String user     = "ffu";
    String pass     = "ffu";
	
    // name of the database to use
    //
    String database = "ffu";
	
    // name of the table that will be created
    //
    String table    = "";
	
    // connect to database of server "localhost"
    //
    dbconnection = new MySQL( this, "localhost", database, user, pass );
    
    if ( dbconnection.connect() )
    {
        // now read it back out
        //
        dbconnection.query( "SELECT * FROM file_uploads" );
        
        while (dbconnection.next())
        {
            String s = dbconnection.getString("name");
            int n = dbconnection.getInt("fuid");
            println(s + "   " + n);
        }
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
