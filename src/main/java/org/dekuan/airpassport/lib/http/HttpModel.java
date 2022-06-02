package org.dekuan.airpassport.lib.http;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.dekuan.airpassport.lib.models.rest.RestHeader;

import java.util.HashMap;


/**
 *	REST Base Response
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder( builderMethodName = "httpModelBuilder" )
public class HttpModel extends RestHeader
{
	@Builder.Default
	protected Object body		= new HashMap<>();
}