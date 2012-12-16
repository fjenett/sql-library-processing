/** UNPACKAGED to be able to access unpackaged classes **/

import de.bezier.data.sql.*;
import de.bezier.data.sql.mapper.*;
import java.lang.reflect.*;

/**
 *	In Java classes in a package can not gain access to
 *  classes outside any package through reflection.
 */

public class DeBezierDataSQL
{
	public static void setFromRow ( SQL db, Object object )
	{
		String[] colNames = db.getColumnNames();
		if ( colNames == null || colNames.length == 0 ) return;
		
		Class klass = object.getClass();
		for ( String colName : colNames ) 
		{	
			// translate the fieldname via mapper
			
			String fieldName = colName;
			NameMapper mapper = db.getNameMapper();
			if ( mapper != null ) 
			{
				fieldName = mapper.forward( colName );
			}
			
			// fetch value from DB
			
			Object val = null;
			try {
				val = db.getObject(colName);
			} catch  (Exception e) {
				if (db.getDebug()) e.printStackTrace();
			}
			if ( val == null ) {
				if (db.getDebug()) {
					System.err.println( "setFromRow(): Value is null" );
					System.err.println( colName );
				}
				continue; // TODO warn here?
			}
			
			// // try to make a clone of the value ..?
			// 
			// Class vClass = val.getClass();
			// try {
			// 	Method vMeth = vClass.getMethod("clone");
			// 	if ( vMeth != null && 
			// 		Modifier.isPublic(vMeth.getModifiers()) ) 
			// 	{
			// 		Object tmp = vMeth.invoke(val);
			// 		val = tmp;
			// 		//if (DEBUG) System.out.println("Value cloned.");
			// 	}
			// } catch ( Exception ex ) {
			// 	if (DEBUG) ex.printStackTrace();
			// }
			
			//
			// try fields first 
			// obj.fieldName = value
			
			Field f = null;
			try {
				f = klass.getField( fieldName );
			} catch ( Exception e ) {	
				if (db.getDebug()) e.printStackTrace();
			}
			if ( f != null ) 
			{
				try {
					f.set( object, val );
					continue;
				} catch  (Exception e) {
					if (db.getDebug()) e.printStackTrace();
				}
			}
			
			//
			// try setter next ..
			// obj.setFieldName( value )
			// obj.fieldName( value )
			
			Method[] meths = null;
			try {
				meths = klass.getMethods();
			} catch ( Exception e ) {
				if (db.getDebug()) e.printStackTrace();
			}
			if ( meths != null && meths.length > 0 ) 
			{
				String setterName = db.nameToSetter(fieldName);
				//Class[] paramTypes = new Class[]{val.getClass()};
				for ( Method meth : meths ) 
				{
					if ( meth.getName().equals(setterName) ||
						 meth.getName().equals(fieldName) 
						/*&& meth.getParameterTypes().equals(paramTypes)*/ ) 
					{
						//if (db.getDebug()) System.out.println( meth );
						try {
							meth.invoke( object, new Object[]{val} );
							break;
						} catch ( Exception e ) {
							if (db.getDebug()) e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public static Object[] getValuesFromObject ( SQL db, Field[] fields, Method[] getters, Object object )
	{
		if ( db == null || object == null ) return null;
		if ( fields == null || getters == null ) return null;
		if ( fields.length != getters.length ) return null;
		
		Object[] vals = new Object[fields.length];
		
		for ( int i = 0; i < vals.length; i++ ) {
			try {
				if ( fields[i] != null )
					vals[i] = fields[i].get(object);
				else if ( getters[i] != null )
					vals[i] = getters[i].invoke(object);
				else
				{
					System.err.println(String.format(
						"getValuesFromObject() : neither field nor getter given for a value."
					));
					return null;
				}
			} catch ( Exception e ) {
				e.printStackTrace();
				return null;
			}
		}
		
		return vals;
	}
}