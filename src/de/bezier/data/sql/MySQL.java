package de.bezier.data.sql;

import processing.core.*;

/**
 *		MySQL wrapper for SQL library for Processing 1.0
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
 *		modified:		fjenett 20081129
 *
 *		@since 			0.0.1
 *		@version 		0.0.7
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
}
