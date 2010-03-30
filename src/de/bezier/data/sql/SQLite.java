package de.bezier.data.sql;

import processing.core.*;

/**
 *		SQLite wrapper for SQL library for Processing 1.0
 *		<p>
 *		A wrapper around some of sun's java.sql.* classes
 *		and the pure java "org.sqlite.JDBC" driver by zentus.com (BSD).
 *		</p>
 *		see:<ul>
 *			<li>http://www.zentus.com/sqlitejdbc/</li>
 *			<li>http://files.zentus.com/sqlitejdbc/</li>
 *			<li>http://java.sun.com/products/jdbc/</li>
 *		</ul>
 *
 *
 *		@author 		Florian Jenett - mail@florianjenett.de
 *
 *		created:		2008-11-29 12:15:15 - fjenett
 *		modified:		fjenett 20081129
 *
 *		@since 			0.0.7
 *		@version 		0.0.7
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
}
