package org.dekuan.airpassport.lib.models.wechat;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;


/**
 * 	公众号开发指南 access_token
 * 	<a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">...</a>
 */
public class WxAccessToken extends WxBase
{
	/**
	 * 	access_token 的存储至少要保留 512 个字符空间
	 */
	private String access_token;

	/**
	 * 	凭证有效时间，单位：秒
	 *
	 * 	access_token 的有效期目前为 2 个小时，需定时刷新
	 * 	重复获取将导致上次获取的 access_token 失效
	 */
	private long expires_in;


	public WxAccessToken()
	{
		super();
	}
	public WxAccessToken( String error )
	{
		super( error );
	}
	public WxAccessToken( String access_token, long expires_in )
	{
		this.setAccess_token( access_token );
		this.setExpires_in( expires_in );
	}


	public String getAccess_token()
	{
		return access_token;
	}
	public void setAccess_token( String access_token )
	{
		if ( StringUtils.isBlank( access_token ) )
		{
			throw new AirExceptions.InvalidParameter( "invalid access_token" );
		}

		this.access_token = access_token;
	}


	public long getExpires_in()
	{
		return expires_in;
	}
	public void setExpires_in( long expires_in )
	{
		if ( expires_in <= 0 )
		{
			throw new AirExceptions.InvalidParameter( "invalid expires_in" );
		}

		this.expires_in = expires_in;
	}


	public boolean isValid()
	{
		return StringUtils.isNotBlank( this.access_token ) &&
			this.expires_in > 0
		;
	}


	/**
	 *	parse json string to Object
	 *
	 *	@param	jsonString	string
	 *	@return	AirAuth
	 */
	public static WxAccessToken fromJson( String jsonString )
	{
		try
		{
			if ( StringUtils.isBlank( jsonString ) )
			{
				throw new AirExceptions.InvalidParameter( "invalid jsonString" );
			}

			//	...
			Gson gson = new Gson();
			return gson.fromJson( jsonString, WxAccessToken.class );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			return new WxAccessToken( e.getMessage() );
		}
	}

	@Override
	public String toJson()
	{
		try
		{
			Gson gson = new Gson();
			return gson.toJson( this );
		}
		catch ( JsonSyntaxException | IllegalArgumentException e )
		{
			e.printStackTrace();
			throw new AirExceptions.BuildJson( e.getMessage() );
		}
	}
}
