package org.dekuan.airpassport.lib.models;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;


public class GsonBase
{
	protected String error;

	public GsonBase()
	{
		this.error	= null;
	}
	public GsonBase( String error )
	{
		this.error	= error;
	}


	public String getError()
	{
		return error;
	}
	public void setError( String error )
	{
		this.error = error;
	}

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