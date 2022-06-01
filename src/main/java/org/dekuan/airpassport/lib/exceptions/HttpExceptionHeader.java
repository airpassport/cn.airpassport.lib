package org.dekuan.airpassport.lib.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


/**
 * <a
 * href="https://mkyong.com/spring-boot/spring-rest-error-handling-example/">https://mkyong.com/spring-boot/spring-rest-error-handling-example/</a>
 */
@Getter
@Setter
@SuperBuilder
public class HttpExceptionHeader
{
	@Builder.Default
	protected String version = "";

	//	HTTP Status
	@Builder.Default
	protected int status = 0;

	//	BAD_REQUEST
	@Builder.Default
	protected String error = "";

	//	error message
	@Builder.Default
	protected String message = "";

	//	url
	@Builder.Default
	protected String path = "";

	//	client ip and port
	@Builder.Default
	protected String client = "";

	//	SkyWalking Trace ID
	@Builder.Default
	protected String traceId = "";

	@Builder.Default
	protected long timestamp = System.currentTimeMillis();
}
