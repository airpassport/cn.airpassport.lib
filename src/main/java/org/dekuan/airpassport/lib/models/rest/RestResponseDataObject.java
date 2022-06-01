package org.dekuan.airpassport.lib.models.rest;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;


/**
 *	REST Response
 *
 * 	{
 * 		"header ..."
 *		"body" :
 *		{
 *		 	"data" : { ... } / [ ... ] ...
 *		}
 * 	}
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder( builderMethodName = "responseDataObjectBuilder" )
public class RestResponseDataObject extends RestHeader
{
	@Builder.Default
	public final Body body	= new Body();



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
		public Object data	= new HashMap<>();
	}
}