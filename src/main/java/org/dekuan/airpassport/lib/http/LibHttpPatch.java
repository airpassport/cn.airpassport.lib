package org.dekuan.airpassport.lib.http;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@SuperBuilder
@Slf4j
public class LibHttpPatch extends LibHttpEntityRequest
{
	public HttpModel patchRequest()
	{
		return this.executeRequest( HttpMethod.PATCH );
	}
}