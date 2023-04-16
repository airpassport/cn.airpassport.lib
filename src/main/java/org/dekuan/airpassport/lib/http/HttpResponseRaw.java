package org.dekuan.airpassport.lib.http;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class HttpResponseRaw
{
	@Builder.Default
	private int status = 0;

	@Builder.Default
	private String body = "";
}