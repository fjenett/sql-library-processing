package de.bezier.data.sql; 

import processing.core.*;
import java.util.ArrayList;

/**
 *		PostgreSQL wrapper for SQL library for Processing 2+
 *		<p>
 *		This is a wrapper around some of sun's java.sql.* classes
 *		and the "org.postgresql.Driver" driver by postgresql.org (BSD).
 *		</p>
 *		see:<ul>
 *			<li>http://jdbc.postgresql.org/download.html</li>
 *			<li>http://java.sun.com/products/jdbc/</li>
 *		</ul>
 *		
 *		PostgreSQL on OS-X (i used a mix of these on 10.5.x):<ul>
 *			<li>http://developer.apple.com/internet/opensource/postgres.html</li>
 *			<li>http://shifteleven.com/articles/2008/03/21/installing-postgresql-on-leopard-using-macports</li>
 *			<li>http://systems.takizo.com/2008/03/10/installing-postgresql-82-on-leopard-with-macports/</li>
 *		</ul>
 *		PostgreSQL documentation is at:<ul>
 *			<li>http://www.postgresql.org/docs/8.3/interactive/index.html</li>
 *		</ul>
 *
 *		@author 		Florian Jenett - mail@florianjenett.de
 *
 *		created:		2008-11-29 17:49:23 - fjenett
 *		modified:		fjenett 2012-02
 *
 */

public class PostgreSQL
extends de.bezier.data.sql.SQL
{
	public PostgreSQL ( PApplet _papplet, String _database )
	{
		// should not be used
	}

	/**
	 *	Creates a new PostgreSQL connection.
	 *
	 *	@param	_papplet	Normally you'd pass "this" in for your sketch
	 *	@param	_server		The server running the database, try "localhost"
	 *	@param	_database	Name of the database
	 *	@param	_user		Username for that database
	 *	@param	_pass		Password for user
	 */
	 
	public PostgreSQL (  PApplet _papplet, String _server, String _database, String _user, String _pass )
	{
		super( _papplet, _server, _database, _user, _pass );
		init();
	}
	
	private void init ()
	{
		this.driver = "org.postgresql.Driver";
		this.type = "postgresql";
		
		this.url = "jdbc:" + type + "://" + server +  "/" + database;
	}

	public String[] getTableNames ()
	{
		if ( tableNames == null )
		{
			tableNames = new ArrayList<String>();
	        query( "SELECT relname AS 'table_name' FROM pg_stat_user_tables WHERE schemaname='public'" );
			while ( next() ) {
				tableNames.add( getObject("table_name").toString() );
			}
		}
		return tableNames.toArray(new String[0]);
	}
}
