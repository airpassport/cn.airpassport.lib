package org.dekuan.airpassport.lib.http;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@SuperBuilder
@Slf4j
public class LibHttpHead extends LibHttpBaseRequest
{
	public HttpModel headRequest()
	{
		return this.executeRequest( HttpMethod.HEAD );
	}
}
