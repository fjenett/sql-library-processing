package de.bezier.data.sql;

import processing.core.*;
import java.util.ArrayList;

/**
 *		MySQL wrapper for SQL library for Processing 2+
 *		<p>
 *		A wrapper around some of sun's java.sql.* classes
 *		and the "com.mysql.jdbc.Driver" driver by mysql.com (GPL).
 *		</p>
 *		see:
 *		- http://www.mysql.com/products/connector/j/
 *		- http://java.sun.com/products/jdbc/
 *
 *
 *		@author 		Florian Jenett - mail@florianjenett.de
 *
 *		created:		07.05.2005 - 12:46 Uhr
 *		modified:		2012-02
 *
 */

public class MySQL
extends de.bezier.data.sql.SQL
{	
	public MySQL ( PApplet _papplet, String _database )
	{
		// should not be used
	}
	
	/**
	 *	Creates a new MySQL connection.
	 *
	 *	@param _papplet	Normally you'd pass "this" in for your sketch
	 *	@param _server		The server running the database, try "localhost"
	 *	@param _database	Name of the database
	 *	@param _user		Username for that database
	 *	@param _pass		Password for user
	 */
	 
	public MySQL (  PApplet _papplet, String _server, String _database, String _user, String _pass)
	{
		super( _papplet, _server, _database, _user, _pass );
		init();
	}
	
	private void init ()
	{
		this.driver = "com.mysql.jdbc.Driver";
		this.type = "mysql";
		
		this.url = "jdbc:" + type + "://" + server +  "/" + database;
	}
	
	public String[] getTableNames ()
	{
		if ( tableNames == null ) 
		{
			tableNames = new ArrayList<String>();
			query( "SHOW TABLES" );
			while ( next() ) {
				tableNames.add( getObject("Tables_in_"+database).toString() );
			}
		}
		return tableNames.toArray(new String[0]);
	}
}
