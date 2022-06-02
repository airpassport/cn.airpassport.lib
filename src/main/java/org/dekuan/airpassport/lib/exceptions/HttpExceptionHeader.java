package org.dekuan.airpassport.lib.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.dekuan.airpassport.lib.models.rest.RestHeader;


/**
 * <a href="https://mkyong.com/spring-boot/spring-rest-error-handling-example/">...</a>
 */
@Getter
@Setter
@SuperBuilder( builderMethodName = "httpExceptionHeaderBuilder" )
public class HttpExceptionHeader extends RestHeader
{
	//	url
	@Builder.Default
	protected String path = "";

	//	client ip and port
	@Builder.Default
	protected String client = "";
}