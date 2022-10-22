package org.dekuan.airpassport.lib.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dekuan.airpassport.lib.common.LibCommon;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

import javax.crypto.SecretKey;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@SuperBuilder
public class JwtHelper
{
	@Builder.Default
	private SecretKey signKey = null;


	/**
	 *	set sign key from string
	 * 	@param signKey	- load from application.yml
	 */
	public void setSignKeyFromString( String signKey )
	{
		if ( StringUtils.isBlank( signKey ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid signKey" );
		}

		try
		{

			this.signKey = Keys.hmacShaKeyFor( Decoders.BASE64.decode( signKey ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			log.error( LibCommon.calcErrorCode( new Object() {}, e.getMessage() ) );
			throw new AirExceptions.ReadData( "failed to decode sign key of jwt" );
		}
	}

	/**
	 * get Encrypted Jwt String
	 *
	 * @param authData
	 * 	-
	 */
	public String createJwtString( JwtData authData )
	{
		if ( null == authData || ! authData.isValid() )
		{
			throw new AirExceptions.InvalidParameter( "invalid authData" );
		}
		if ( null == this.signKey )
		{
			throw new AirExceptions.InvalidParameter( "invalid signKey" );
		}

		//	...
		String sJsonValue = authData.toJson();
		if ( StringUtils.isBlank( sJsonValue ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid json" );
		}

		//	...
		return Jwts.builder().setPayload( sJsonValue ).signWith( signKey ).compact();
	}

	/**
	 * decrypt jwt
	 */
	public JwtData decryptJwtString( String jwtString )
	{
		if ( null == this.signKey )
		{
			throw new AirExceptions.InvalidParameter( "invalid signKey" );
		}
		if ( StringUtils.isBlank( jwtString ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid jwtString" );
		}

		try
		{
			Jws<Claims> jws = Jwts.parserBuilder().setSigningKey( this.signKey ).build().parseClaimsJws( jwtString );
			if ( null != jws )
			{
				String userMid = ( String ) jws.getBody().getOrDefault( "userMid", "" );
				String authMid = ( String ) jws.getBody().getOrDefault( "authMid", "" );
				String token   = ( String ) jws.getBody().getOrDefault( "token", "" );
				String expire  = ( String ) jws.getBody().getOrDefault( "expire", "" );
				String role    = ( String ) jws.getBody().getOrDefault( "role", "" );

				JwtData jwtData = JwtData.jwtDataBuilder()
					.userMid( userMid )
					.authMid( authMid )
					.token( token )
					.expire( expire )
					.role( role )
					.jwt( jwtString )
					.build();
				if ( jwtData.isValid() )
				{
					return jwtData;
				}
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			// we *cannot* use the JWT as intended by its creator

			throw new AirExceptions.ParseJwt( "failed to decrypt jwt string" );
		}

		return null;
	}
}