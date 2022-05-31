package org.dekuan.airpassport.lib.http;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.HashMap;


/**
 *	REST Base Response
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder( builderMethodName = "httpModelBuilder" )
public class HttpModel implements Serializable
{
	//	HTTP status code
	@Builder.Default
	protected int status		= 0;

	//	service name
	@Builder.Default
	protected String service	= "";

	@Builder.Default
	protected String version	= "";

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
	protected Object body		= new HashMap<>();



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
	public static HttpModel fromJson( String jsonString )
	{
		try
		{
			if ( null == jsonString || jsonString.isEmpty() )
			{
				throw new InvalidParameterException( "invalid jsonString" );
			}

			Gson gson = new Gson();
			return gson.fromJson( jsonString, HttpModel.class );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			throw new JsonSyntaxException( e.getMessage() );
		}
	}

	public String toJson()
	{
		try
		{
			if ( ! this.isValid() )
			{
				throw new InvalidParameterException( "invalid data for json serialize." );
			}

			Gson gson = new Gson();
			return gson.toJson( this );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			throw new JsonSyntaxException( e.getMessage() );
		}
	}


	@Override
	public String toString()
	{
		return this.toJson();
	}
}