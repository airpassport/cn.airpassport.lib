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
 *		 	"key1" : { ... },
 *		 	"key2" : [ ... ],
 *		 	"key3" " "",
 *		 	"key4" " n,
 *		}
 * 	}
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder( builderMethodName = "responseKeyObjectBuilder" )
public class RestResponseKeyObject extends RestHeader
{
	@Builder.Default
	public HashMap<String, Object> body = new HashMap<>();


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

		this.body.put( key, item );
	}

	public void removeMapItem( String key )
	{
		if ( StringUtils.isBlank( key ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid key" );
		}

		if ( null != this.body )
		{
			this.body.remove( key );
		}
	}

	public void clearMap()
	{
		if ( null != this.body )
		{
			this.body.clear();
		}
	}
}