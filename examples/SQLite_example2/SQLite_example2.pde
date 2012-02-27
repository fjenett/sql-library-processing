// fjenett 20120226

import de.bezier.data.sql.*;

SQLite db;

void setup()
{
    size( 100, 100 );

    db = new SQLite( this, "test.db" );  // open database file

    if ( db.connect() )
    {
        String[] tableNames = db.getTableNames();
        
        db.query( "SELECT * FROM %s", tableNames[0] );
        
        while (db.next())
        {
            TableOne t = new TableOne();
            db.setFromRow( t );
            println( t );
        }
    }
}

class TableOne
{
    public String fieldOne;
    public int fieldTwo;
    
    public String toString ()
    {
        return String.format("fieldOne: %s fieldTwo: %d", fieldOne, fieldTwo);
    }
}

