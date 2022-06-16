package org.dekuan.airpassport.lib.http;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@SuperBuilder
@Slf4j
public class LibHttpPut extends LibHttpEntityRequest
{
	public HttpModel putRequest()
	{
		return this.executeRequest( HttpMethod.PUT );
	}
}