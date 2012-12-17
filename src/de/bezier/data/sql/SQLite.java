package de.bezier.data.sql;

import processing.core.*;
import java.util.ArrayList;

/**
 *		SQLite wrapper for SQL library for Processing 2+
 *		<p>
 *		A wrapper around some of sun's java.sql.* classes
 *		and the pure java "org.sqlite.JDBC" driver of the Xerial project (Apache 2 license).
 *		</p>
 *		see:<ul>
 *			<li>http://www.xerial.org/trac/Xerial/wiki/SQLiteJDBC</li>
 *			<li>http://www.xerial.org/maven/repository/site/xerial-project/sqlite-jdbc/apidocs/index.html?index-all.html</li>
 *			<li>http://java.sun.com/products/jdbc/</li>
 *		</ul>
 *
 *
 *		@author 		Florian Jenett - mail@florianjenett.de
 *
 *		created:		2008-11-29 12:15:15 - fjenett
 *		modified:		fjenett 20121217
 *
 */

public class SQLite
extends de.bezier.data.sql.SQL
{	
	/**
	 *	Creates a new SQLite connection.
	 *
	 *	@param	_papplet	Your sketch, pass "this" in here
	 *	@param	_database	Path to the database file, if this is just a name the data and sketch folders are searched for the file
	 */
	 
	public SQLite ( PApplet _papplet, String _database  )
	{
		super( _papplet, _database );
		init();
	}
	
	/**
	 *	Creates a new SQLite connection, same as SQLite( PApplet, String )
	 *
	 *	@param	_papplet	Your sketch, pass "this" in here
	 *	@param	_server		Ignored
	 *	@param	_database	Path to the database file, if this is just a name the data and sketch folders are searched for the file
	 *	@param	_user		Ignored
	 *	@param	_pass		Ignored
	 */
	
	public SQLite (  PApplet _papplet, String _server, String _database, String _user, String _pass )
	{
		this( _papplet, _database );
	}
	
	
	private void init ()
	{
		this.driver = "org.sqlite.JDBC";
		this.type = "sqlite";
		
		this.url = "jdbc:" + type + ":" + database;
	}

	public String[] getTableNames ()
	{
		if ( tableNames == null )
		{
			tableNames = new ArrayList<String>();
	        query( "SELECT name AS 'table_name' FROM SQLITE_MASTER WHERE type=\"table\"" );
			while ( next() ) {
				tableNames.add( getObject("table_name").toString() );
			}
		}
		return tableNames.toArray(new String[0]);
	}
}
