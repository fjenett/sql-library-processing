// fjenett 20120226

import de.bezier.data.sql.*;

MySQL db;

void setup()
{
    size( 100, 100 );

    db = new MySQL( this, "localhost", "x", "x", "x" );  // open database file
    db.setDebug(false);

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
        
        TableOne t1 = new TableOne();
        t1.fieldOne = "one, two, three";
        t1.fieldTwo = 123;
        t1.id = 101;
        db.saveToDatabase(t1);
    }
}

class TableOne
{
    int id;
    public String fieldOne;
    public int fieldTwo;
    
    public String toString ()
    {
        return String.format("id: %d, fieldOne: %s fieldTwo: %d", id, fieldOne, fieldTwo);
    }
    
    public void setId ( int id ) {
        this.id = id;
    }
    
    public int getId () {
        return id;
    }
}

