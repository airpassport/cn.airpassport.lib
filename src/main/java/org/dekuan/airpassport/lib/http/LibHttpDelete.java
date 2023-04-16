package org.dekuan.airpassport.lib.http;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;

import java.io.IOException;

@NoArgsConstructor
@SuperBuilder
@Slf4j
public class LibHttpDelete extends LibHttpBaseRequest
{
	//
	//	The 2014 update to the HTTP 1.1 specification (RFC 7231) explicitly permits an entity-body in a DELETE request:
	//
	//	A payload within a DELETE request message has no defined semantics;
	//	sending a payload body on a DELETE request might cause some existing implementations to reject the request.
	//

	@Override
	public HttpResponseRaw fetchRaw() throws IOException
	{
		return this.fetchRaw( HttpMethod.DELETE );
	}

	@Override
	public String fetchString() throws IOException
	{
		return this.fetchString( HttpMethod.DELETE );
	}

	@Override
	public HttpModel fetchHttpModel()
	{
		return this.fetchHttpModel( HttpMethod.DELETE );
	}
}
