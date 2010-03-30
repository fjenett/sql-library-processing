package de.bezier.data.sql;

import processing.core.*;

import java.io.*;
import java.sql.*;

/**
 *		SQL library for Processing 1.0
 *		
 *		see:<ul>
 *		<li>http://www.mysql.com/products/connector/j/</li>
 *		<li>http://java.sun.com/products/jdbc/</li>
 *		<li>http://www.toxi.co.uk/blog/2007/07/using-javadb-and-db4o-in-processing.htm</li>
 *		<li>http://www.tom-carden.co.uk/2007/07/30/a-quick-note-on-using-sqlite-in-processing/</li>
 *		</ul>
 *
 *		@author 		Florian Jenett - mail@florianjenett.de
 *
 *		created:		07.05.2005 - 12:46 Uhr
 *		modified:		fjenett 20070801
 *
 *		@since 			004
 *		@version 		005 - added more general SQL, simplyfied MySQL
 *
 */

public class SQL
{
	PApplet papplet;

	public String server;
	public String database;
	public String url;
	public String user;
	protected String pass;
	public String driver = "";
	public String type = "";
	
	public java.sql.Connection connection;
	public String previousQuery;
	
	public java.sql.Statement statement;
	public java.sql.ResultSet result;
	
	private boolean DEBUG = true;
	
	/**
	 *	Do not use this contructor.
	 */
	 
	public SQL ()
	{
		System.out.println("SQL(): Please use this constructor\r\tSQL ( String _serv, String _db, String _u, String _p, PApplet _pa )");
	}
	
	/**
	 *	You should not directly use the SQL.class instead use the classes for your database type.
	 */
	 
	public SQL ( PApplet _pa, String _db )
	{
		this.user = "";
		this.pass = "";
		this.server = "";
		
		String f = _pa.dataPath(_db);
		File ff = new File(f);
		if ( !ff.exists() || !ff.canRead() )
		{
			f = _pa.sketchPath( _db );
			ff = new File(f);
			
			if ( !ff.exists() || !ff.canRead() )
			{
				f = _db;
				ff = new File(f);
				
				if ( !ff.exists() || !ff.canRead() )
				{
					System.err.println("Sorry can't find any file named "+_db+" make sure it exists and the path is correct.");
				}
			}
		}
		
		_pa.println( "Using this database: " + f );
		
		this.database = f;
	
		this.url = "jdbc:" + type + ":" + database;
	
		this.papplet = _pa;
		papplet.registerDispose( this );
	}
	
	
	/**
	 *	You should not directly use the SQL.class instead use the classes for your database type.
	 */
	
	public SQL ( PApplet _pa, String _serv, String _db, String _u, String _p )
	{
		this.server = _serv;
		this.database = _db;
		
		this.url = "jdbc:" + type + "://" + server +  "/" + database;
		
		this.user = _u;
		this.pass = _p;
		
		this.papplet = _pa;
		papplet.registerDispose( this );
	}
	
	/**
	 *	Open the database connection with the parameters given in the contructor.
	 */
	 
	public boolean connect()
	{
		if ( driver == null || driver.equals("") ||
			 type == null || type.equals("") )
		{
			System.out.println( "SQL.connect(): You have to set a driver and type first." );
			return false;
		}
		
		// TODO: need to add mechanisms for different connection types and parameters, see:
		// http://jdbc.postgresql.org/documentation/83/connect.html
	
		try
		{
			Class.forName(driver);
			connection = java.sql.DriverManager.getConnection(url, user, pass);
			
		}
		catch (ClassNotFoundException e)
		{
			System.out.println( "SQL.connect(): Could not find the database driver ( "+driver+" ).\r" );
			if (DEBUG) e.printStackTrace();
			return false;
			
		}
		catch (java.sql.SQLException e)
		{
			System.out.println( "SQL.connect(): Could not connect to the database ( "+url+" ).\r" );
			if (DEBUG) e.printStackTrace();
			return false;
			
		}
		
		// removed finally block, thanks nao
		
		return true;
	}
	
	/**
	 *	Execute a SQL command on the open database connection.
	 *
	 *	@param	_sql	The SQL command to execute
	 */
	
	public void execute ( String _sql )
	{
		if ( connection == null )
		{
			System.out.println( "SQL.query(): You need to connect() first." );
			return;
		}
		
		previousQuery = _sql;
		
		try
		{
			if ( statement == null )
			{
				statement = connection.createStatement();
			}
			
			statement.execute( _sql );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.query(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
	}
	
	
	/**
	 *	Issue a query on the open database connection
	 *
	 *	@param	_sql	SQL command to execute for the query
	 */
		
	public void query ( String _sql )
	{
		if ( connection == null )
		{
			System.out.println( "SQL.query(): You need to connect() first." );
			return;
		}
		
		previousQuery = _sql;
		
		try
		{
			if ( statement == null )
			{
				statement = connection.createStatement();
			}
			
			result = statement.executeQuery( _sql );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.query(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
	}
	
	/**
	 *	Check if more results (rows) are available. This needs to be called before any results can be retrieved.
	 *
	 *	@return	boolean	true if more results are available, false otherwise
	 */
	
	public boolean next ()
	{	
		if ( result == null )
		{
			System.out.println( "SQL.next(): You need to query() something first." );
			return false;
		}
		
		try
		{
			return result.next();
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.next(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return false;
	}
	
	/**
	 *	Read an integer value from the specified field.
	 *	Represents an INT / INTEGER type:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	int	Value of the field or 0
	 */
	
	public int getInt ( String _field )
	{
		// TODO: 0 does not seem to be a good return value for a numeric field to indicate failure
		// same goes for other numeric fields
		
		if ( result == null )
		{
			System.out.println( "SQL.getInt(): You need to query() something first." );
			return 0;
		}
		
		try
		{
			return result.getInt( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getInt(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return 0;
	}
	
	
	public int getInt ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getInt(): You need to query() something first." );
			return 0;
		}
		
		try
		{
			return result.getInt( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getInt(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 *	Read a long value from the specified field.
	 *	Represents a BIGINT type:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	long	Value of the field or 0
	 */
	 
	public long getLong ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getLong(): You need to query() something first." );
			return 0;
		}
		
		try
		{
			return result.getLong( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getLong(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return 0;
	}
	
	public long getLong ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getLong(): You need to query() something first." );
			return 0;
		}
		
		try
		{
			return result.getLong( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getLong(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 *	Read a float value from the specified field.
	 *	Represents a REAL type:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	float	Value of the field or 0
	 */
	 
	public float getFloat ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getFloat(): You need to query() something first." );
			return 0.0f;
		}
		
		try
		{
			return result.getFloat( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getFloat(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return 0.0f;
	}
	
	public float getFloat ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getFloat(): You need to query() something first." );
			return 0.0f;
		}
		
		try
		{
			return result.getFloat( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getFloat(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return 0.0f;
	}
	
	
	/**
	 *	Read a double value from the specified field.
	 *	Represents FLOAT and DOUBLE types:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	double	Value of the field or 0
	 */
	 
	public double getDouble ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getDouble(): You need to query() something first." );
			return 0.0;
		}
		
		try
		{
			return result.getDouble( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getDouble(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return 0.0;
	}
	
	public double getDouble ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getDouble(): You need to query() something first." );
			return 0.0;
		}
		
		try
		{
			return result.getDouble( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getDouble(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return 0.0;
	}
	
	
	/**
	 *	Read a java.math.BigDecimal value from the specified field.
	 *	Represents DECIMAL and NUMERIC types:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	java.math.BigDecimal	Value of the field or null
	 */
	 
	public java.math.BigDecimal getBigDecimal ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getBigDecimal(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getBigDecimal( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getBigDecimal(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	public java.math.BigDecimal getBigDecimal ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getBigDecimal(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getBigDecimal( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getBigDecimal(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 *	Read a boolean value from the specified field.
	 *	Represents BIT type:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	boolean	Value of the field or 0
	 */
	 
	public boolean getBoolean ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getBoolean(): You need to query() something first." );
			return false;
		}
		
		try
		{
			return result.getBoolean( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getBoolean(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return false;
	}
	
	public boolean getBoolean ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getBoolean(): You need to query() something first." );
			return false;
		}
		
		try
		{
			return result.getBoolean( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getBoolean(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 *	Read a String value from the specified field.
	 *	Represents VARCHAR and CHAR types:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	String	Value of the field or null
	 */
	 
	public String getString ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getString(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getString( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getString(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	public String getString ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getString(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getString( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getString(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 *	Read a java.sql.Date value from the specified field.
	 *	Represents DATE type:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	java.sql.Date	Value of the field or null
	 */
	 
	public java.sql.Date getDate ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getDate(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getDate( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getDate(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	public java.sql.Date getDate ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getDate(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getDate( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getDate(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 *	Read a java.sql.Time value from the specified field.
	 *	Represents TIME type:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	java.sql.Time	Value of the field or null
	 */
	 
	public java.sql.Time getTime ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getTime(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getTime( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getTime(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	public java.sql.Time getTime ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getTime(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getTime( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getTime(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 *	Read a java.sql.Timestamp value from the specified field.
	 *	Represents TIMESTAMP type:
	 *	http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
	 *	"8.9.6	Conversions by ResultSet.getXXX Methods"
	 *
	 *	@param	_field	The name of the field
	 *	@return	java.sql.Timestamp	Value of the field or null
	 */
	 
	public java.sql.Timestamp getTimestamp ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getTimestamp(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getTimestamp( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getTimestamp(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	public java.sql.Timestamp getTimestamp ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getTimestamp(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getTimestamp( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getTimestamp(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 *	Read a value from the specified field to hav it returned as an object.
	 *
	 *	@param	_field	The name of the field
	 *	@return	Object	Value of the field or null
	 */
	 
	public Object getObject ( String _field )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getObject(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getObject( _field );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getObject(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}
	
	public Object getObject ( int _column )
	{
		if ( result == null )
		{
			System.out.println( "SQL.getObject(): You need to query() something first." );
			return null;
		}
		
		try
		{
			return result.getObject( _column );
		}
		catch ( java.sql.SQLException e )
		{
			System.out.println( "SQL.getObject(): java.sql.SQLException.\r" );
			if (DEBUG) e.printStackTrace();
		}
		return null;
	}

	/**
	 *	Close the database connection
	 */
	 
	public void close()
	{
		dispose();
	}
	
	
	/**
	 *	Callback function for PApplet.registerDispose()
	 */
	 
	public void dispose ()
	{
		if ( result != null )
		{
			try
			{
				result.close();
			}
			catch ( java.sql.SQLException e ) { ; }
			
			result = null;
		}
		
		if ( statement != null )
		{
			try
			{
				statement.close();
			}
			catch ( java.sql.SQLException e ) { ; }
			
			statement = null;
		}
		
		if ( connection != null )
		{
			try
			{
				connection.close();
			}
			catch ( java.sql.SQLException e ) { ; }
			
			connection = null;
		}
	}
}
