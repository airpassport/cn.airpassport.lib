package org.dekuan.airpassport.lib.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;


public class DeDatabaseConnUtil
{
	public Connection executeSql( Connection connection, String sqlFileName )
	{
		ClassPathResource rc	= new ClassPathResource( sqlFileName );
		EncodedResource er	= new EncodedResource( rc, "utf-8" );
		ScriptUtils.executeSqlScript( connection, er );

		return connection;
	}
}
