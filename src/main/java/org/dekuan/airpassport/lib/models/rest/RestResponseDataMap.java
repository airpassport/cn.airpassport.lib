package org.dekuan.airpassport.lib.models.rest;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

import java.util.HashMap;


/**
 *	REST Response
 *
 * 	{
 * 		"header ..."
 *		"body" :
 *		{
 *		 	"data" : { ... },
 *		 	"count" : n
 *		}
 * 	}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder( builderMethodName = "responseDataMapBuilder" )
public class RestResponseDataMap extends RestHeader
{
	@Builder.Default
	public Body body	= new Body();



	public void setMapItem( String key, Object item )
	{
		if ( StringUtils.isBlank( key ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid key" );
		}
		if ( null == item )
		{
			throw new AirExceptions.InvalidParameter( "invalid item" );
		}

		this.body.getData().put( key, item );
	}

	public void removeMapItem( String key )
	{
		if ( StringUtils.isBlank( key ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid key" );
		}

		this.body.getData().remove( key );
	}

	public void clearMap()
	{
		this.body.getData().clear();
	}


	/**
	 *	@class	Body
	 */
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class Body
	{
		@Builder.Default
		public HashMap<String,Object> data	= new HashMap<>();

		public int getCount()
		{
			return this.data.size();
		}
	}
}