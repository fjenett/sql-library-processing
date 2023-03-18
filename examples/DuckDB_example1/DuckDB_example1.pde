import de.bezier.data.sql.*;

DuckDB db;

void setup()
{
    size( 100, 100 );

    db = new DuckDB( this, "test.db" );  // open database file

    if ( db.connect() )
    {
        // list table names
        db.query( "SHOW TABLES" );
        
        while (db.next())
        {
            println( db.getString(1) );
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

