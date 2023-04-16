package org.dekuan.airpassport.lib.http;

import org.apache.http.HttpResponse;

import java.io.IOException;

public interface LibHttpRequest
{
	HttpResponseRaw fetchRaw() throws IOException;

	String fetchString() throws IOException;

	HttpModel fetchHttpModel();
}