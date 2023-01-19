package org.dekuan.airpassport.lib.models.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

import java.io.Serializable;


/**
 *	REST Base Response
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder( builderMethodName = "restHeaderBuilder" )
public class RestHeader implements Serializable
{
	final static public String CURRENT_VERSION	= "1.0";

	//	HTTP status code
	@Builder.Default
	protected int status		= 0;

	//	service name
	@Builder.Default
	protected String service	= "";

	//	version of the service
	@Builder.Default
	protected String version	= CURRENT_VERSION;

	//	perceivable error description in English, for example: invalid.parameter
	@Builder.Default
	protected String error		= "";

	//	details message
	@Builder.Default
	protected String message	= "";

	//	SkyWalking trace id
	@Builder.Default
	protected String traceId	= "";

	//	current timestamp in milliseconds
	@Builder.Default
	protected long timestamp	= System.currentTimeMillis();

	@Builder.Default
	protected int pageNo = 1;

	@Builder.Default
	protected int pageSize = 0;


	public RestHeader( String error )
	{
		this.setError( error );
	}

	public boolean isValid()
	{
		return StringUtils.isNotBlank( this.version );
	}


	/**
	 *	parse json string to Message Object
	 *
	 *	@param	jsonString	string
	 *	@return	Message
	 */
	public static RestHeader fromJson( String jsonString )
	{
		try
		{
			if ( null == jsonString || jsonString.isEmpty() )
			{
				throw new AirExceptions.InvalidParameter( "invalid jsonString" );
			}

			Gson gson = new Gson();
			return gson.fromJson( jsonString, RestHeader.class );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			throw new AirExceptions.ParseJson( e.getMessage() );
		}
	}

	public String toJson()
	{
		try
		{
			if ( ! this.isValid() )
			{
				throw new AirExceptions.InvalidParameter( "invalid data for json serialize." );
			}

			Gson gson = new Gson();
			return gson.toJson( this );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			throw new AirExceptions.BuildJson( e.getMessage() );
		}
	}


	@Override
	public String toString()
	{
		return this.toJson();
	}
}