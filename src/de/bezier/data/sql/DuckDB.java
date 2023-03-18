package de.bezier.data.sql;

import processing.core.*;
import java.util.ArrayList;

/**
 *		DuckDB wrapper for SQL library for Processing 2+
 */
public class DuckDB
        extends de.bezier.data.sql.SQL
{
    /**
     *	Creates a new DuckDB connection.
     *
     *	@param	_papplet	Your sketch, pass "this" in here
     *	@param	_database	Path to the database file, if this is just a name the data and sketch folders are searched for the file
     */
    public DuckDB ( PApplet _papplet, String _database  )
    {
        super( _papplet, _database );
        init();
    }

    /**
     *	Creates a new DuckDB connection, same as DuckDB( PApplet, String )
     *
     *	@param	_papplet	Your sketch, pass "this" in here
     *	@param	_server		Ignored
     *	@param	_database	Path to the database file, if this is just a name the data and sketch folders are searched for the file
     *	@param	_user		Ignored
     *	@param	_pass		Ignored
     */
    public DuckDB (  PApplet _papplet, String _server, String _database, String _user, String _pass )
    {
        this( _papplet, _database );
    }


    private void init ()
    {
        this.driver = "org.duckdb.DuckDBDriver";
        this.type = "duckdb";

        this.url = "jdbc:" + type + ":" + database;
    }

    public String[] getTableNames ()
    {
        if ( tableNames == null )
        {
            tableNames = new ArrayList<String>();
            query( "SHOW TABLES" );
            while ( next() ) {
                tableNames.add( getObject(1).toString() );
            }
        }
        return tableNames.toArray(new String[0]);
    }
}
