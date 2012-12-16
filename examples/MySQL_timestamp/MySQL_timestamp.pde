import de.bezier.data.sql.*;

/*
CREATE TABLE `test` (
  `id` int(10) NOT NULL auto_increment,
  `time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
)
*/

MySQL ms;
java.sql.Timestamp last_ts;

String database = "ddd", user = "", pass = "";

void setup ()
{
    ms = new MySQL( this, "localhost", database, user, pass );
    last_ts = new java.sql.Timestamp( 0 ); 

    if ( ms.connect() )
    {
        ms.query( "SELECT * FROM test ORDER BY time ASC LIMIT 1" );
        ms.next();
        last_ts = ms.getTimestamp("time");
    }
}

void draw ()
{
    if ( ms.connect() )
    {
        ms.query( "SELECT * FROM test WHERE time > '"+last_ts+"' ORDER BY time ASC" );
        while( ms.next() )
        {
            println( ms.getInt( "id" ) );
            last_ts = ms.getTimestamp( "time" );
        }
    }
}

