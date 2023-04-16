package org.dekuan.airpassport.lib.http;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;

import java.io.IOException;

@NoArgsConstructor
@SuperBuilder
@Slf4j
public class LibHttpPut extends LibHttpEntityRequest
{
	@Override
	public HttpResponse fetchRaw() throws IOException
	{
		return fetchRaw( HttpMethod.PUT );
	}

	@Override
	public String fetchString() throws IOException
	{
		return fetchString( HttpMethod.PUT );
	}

	@Override
	public HttpModel fetchHttpModel()
	{
		return fetchHttpModel( HttpMethod.PUT );
	}
}