package org.dekuan.airpassport.lib.auth;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.dekuan.airpassport.lib.common.LibCommon;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder( builderMethodName = "jwtDataBuilder" )
public class JwtData
{
	@Builder.Default
	protected String error = "";

	@Builder.Default
	@Min( 0 )
	protected long tenantId = 0;

	//	user id/mid
	@Builder.Default
	private long userId = 0;

	@Builder.Default
	private String userMid = null;

	//	auth id/mid
	@Builder.Default
	private long authId = 0;

	@Builder.Default
	private String authMid = null;

	//	token string
	@Builder.Default
	private String token = null;

	@Builder.Default
	private String expire = null;

	//	is logged in
	@Builder.Default
	private boolean loggedIn = false;

	//	role
	@Builder.Default
	private String role = "";

	//	jwt string
	@Builder.Default
	private String jwt = "";


	public JwtData( String error )
	{
		this.error = error;
	}

	public void setExpire( String expire )
	{
		this.expire = Strings.nullToEmpty( expire );
	}


	/**
	 * check if this is valid
	 *
	 * @return boolean
	 */
	public boolean isValid()
	{
		return LibCommon.isValidMid( this.userMid ) &&
			LibCommon.isValidMid( this.authMid ) &&
			StringUtils.isNotBlank( this.token ) &&
			StringUtils.isNotBlank( this.expire );
	}


	/**
	 * validate
	 */
	public void validate() throws IllegalArgumentException
	{
		if ( !LibCommon.isValidMid( this.userMid ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid userMid" );
		}
		if ( !LibCommon.isValidMid( this.authMid ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid authMid" );
		}
		if ( StringUtils.isBlank( this.token ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid tokString" );
		}
	}


	/**
	 * parse json string to Object
	 *
	 * @param        jsonString        string
	 * @return AirAuth
	 */
	public static JwtData fromJson( String jsonString )
	{
		try
		{
			if ( StringUtils.isBlank( jsonString ) )
			{
				throw new AirExceptions.InvalidParameter( "invalid jsonString" );
			}

			//	...
			Gson gson = new Gson();
			return gson.fromJson( jsonString, JwtData.class );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			e.printStackTrace();
			return new JwtData( e.getMessage() );
		}
	}

	public String toJson()
	{
		try
		{
			this.validate();

			//	...
			Gson gson = new Gson();
			return gson.toJson( this );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString()
	{
		return this.toJson();
	}
}