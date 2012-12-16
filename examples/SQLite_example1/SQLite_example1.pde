// fjenett 20081129

import de.bezier.data.sql.*;

SQLite db;

void setup()
{
    size( 100, 100 );

    db = new SQLite( this, "test.db" );  // open database file

    if ( db.connect() )
    {
        // list table names
        db.query( "SELECT name as \"Name\" FROM SQLITE_MASTER where type=\"table\"" );
        
        while (db.next())
        {
            println( db.getString("Name") );
        }
        
        // read all in table "table_one"
        db.query( "SELECT * FROM table_one" );
        
        while (db.next())
        {
            println( db.getString("field_one") );
            println( db.getInt("field_two") );
        }
    }
}

