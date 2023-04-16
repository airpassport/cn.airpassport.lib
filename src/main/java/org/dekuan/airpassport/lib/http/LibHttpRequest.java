package org.dekuan.airpassport.lib.http;

import org.apache.http.HttpResponse;

import java.io.IOException;

public interface LibHttpRequest
{
	HttpResponse fetchRaw() throws IOException;

	String fetchString() throws IOException;

	HttpModel fetchHttpModel();
}