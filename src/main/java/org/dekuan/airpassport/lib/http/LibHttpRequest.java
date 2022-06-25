package org.dekuan.airpassport.lib.http;

import java.io.IOException;

public interface LibHttpRequest
{
	String fetchString() throws IOException;
	HttpModel fetchHttpModel();
}