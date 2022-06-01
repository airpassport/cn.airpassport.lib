package org.dekuan.airpassport.lib.models.wechat;

import org.dekuan.airpassport.lib.models.GsonBase;

/**
 * 	公众号开发指南 access_token
 * 	<a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html">...</a>
 */
public abstract class WxBase extends GsonBase
{
	/**
	 * 	error code and message
	 * 	for more details, please visit the documentation
	 */
	protected long errcode;
	protected String errmsg;


	protected WxBase()
	{
		super();
	}
	protected WxBase( String error )
	{
		super( error );
	}


	public long getErrcode()
	{
		return errcode;
	}
	public void setErrcode( long errcode )
	{
		this.errcode = errcode;
	}


	public String getErrmsg()
	{
		return errmsg;
	}
	public void setErrmsg( String errmsg )
	{
		this.errmsg = errmsg;
	}
}
