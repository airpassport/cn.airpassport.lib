package org.dekuan.airpassport.lib.http;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;

import java.io.IOException;

@NoArgsConstructor
@SuperBuilder
@Slf4j
public class LibHttpGet extends LibHttpBaseRequest
{
	@Override
	public HttpResponseRaw fetchRaw() throws IOException
	{
		return this.fetchRaw( HttpMethod.GET );
	}

	@Override
	public String fetchString() throws IOException
	{
		return this.fetchString( HttpMethod.GET );
	}

	@Override
	public HttpModel fetchHttpModel()
	{
		return this.fetchHttpModel( HttpMethod.GET );
	}
}