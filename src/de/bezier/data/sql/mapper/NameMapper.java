package de.bezier.data.sql.mapper;

/**
 *	NameMapper is used to map database names to instance names
 *	When setting objects from objects with SQL.setFromRow().
 *
 *	This is just an interface and only one implementation is
 *  provided in form of the default UnderScoreToCamelCaseMapper
 *	which does: field_name -> fieldName and vv.
 */

public interface NameMapper
{
	/**
	 *	Maps a database name to an object name, typically
	 *	this might look like: field_name -> fieldName. 
	 */
	public String forward ( String name );
	
	/**
	 *	Reverse of forward, maps object names to database
	 *	names like: fieldName -> field_name.
	 */
	public String backward ( String name );
}