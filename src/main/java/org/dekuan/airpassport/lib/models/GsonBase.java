package org.dekuan.airpassport.lib.models;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GsonBase implements Serializable
{
	@Builder.Default
	protected String error	= null;


	/**
	 *	parse json string to Object
	 *
	 *	@param	jsonString	string
	 *	@return	MessageUserJoin
	 */
	public static GsonBase fromJson( String jsonString )
	{
		try
		{
			if ( StringUtils.isBlank( jsonString ) )
			{
				throw new AirExceptions.InvalidParameter( "invalid jsonString" );
			}

			Gson gson = new Gson();
			return gson.fromJson( jsonString, GsonBase.class );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			e.printStackTrace();
			return new GsonBase( e.getMessage() );
		}
	}

	public String toJson()
	{
		try
		{
			Gson gson = new Gson();
			return gson.toJson( this );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			e.printStackTrace();
			throw new AirExceptions.BuildJson( e.getMessage() );
		}
	}

	@Override
	public String toString()
	{
		return this.toJson();
	}
}