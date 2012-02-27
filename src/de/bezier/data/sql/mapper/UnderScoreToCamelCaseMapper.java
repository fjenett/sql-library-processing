package de.bezier.data.sql.mapper;

/** 
 *	UnderScoreToCamelCaseMapper, does as it says.
 */
public class UnderScoreToCamelCaseMapper implements NameMapper
{
	public String backward ( String name ) 
	{
		String newName = "";
		for ( int i = 0, k = name.length(); i < k; i++ )
		{
			String c = name.charAt(i) + "";
			if ( c.toUpperCase().equals(c) && !c.toLowerCase().equals(c) ) {
				if ( i > 0 && i < k-1 )
					c = "_" + c.toLowerCase();
				else
					c = c.toLowerCase();
			}
			newName += c;
		}
		return newName;
	}
	
	public String forward ( String name ) 
	{
		String[] pieces = name.split("_");
		String newName = pieces[0];
		for ( int i = 1; i < pieces.length; i++ )
		{
			if ( pieces[i] != null && pieces[1].length() > 0 )
			{
				newName += pieces[i].substring(0,1).toUpperCase() + 
						   pieces[i].substring(1);
			}
		}
		return newName;
	}
	
	public static void main ( String ... args )
	{
		String[] test = new String[]{
			"created_at",
			"created_at_and_else",
			"rank_1",
			"_rank_abc",
			"rank_abc_",
			"camelCase_and_more"
		};
		UnderScoreToCamelCaseMapper mapper = new UnderScoreToCamelCaseMapper();
		for ( String t : test )
		{
			String fwd = mapper.forward(t);
			System.out.println( t + " -> " + fwd );
			String back = mapper.backward(fwd);
			System.out.println( fwd + " -> " + back );
			System.out.println( t.equals(back) );
			System.out.println( "" );
		}
	}
}