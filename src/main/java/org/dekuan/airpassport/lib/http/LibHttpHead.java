package org.dekuan.airpassport.lib.http;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;

import java.io.IOException;

@NoArgsConstructor
@SuperBuilder
@Slf4j
public class LibHttpHead extends LibHttpBaseRequest
{
	@Override
	public HttpResponseRaw fetchRaw() throws IOException
	{
		return fetchRaw( HttpMethod.HEAD );
	}

	@Override
	public String fetchString() throws IOException
	{
		return fetchString( HttpMethod.HEAD );
	}

	@Override
	public HttpModel fetchHttpModel()
	{
		return fetchHttpModel( HttpMethod.HEAD );
	}
}
