package org.dekuan.airpassport.lib.phone;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *        @class LibPhone
 *
 * 	com.google.i18n.phonenumbers
 * 	Class PhoneNumberUtil
 * 	<a href="https://javadoc.io/doc/com.googlecode.libphonenumber/libphonenumber/8.0.0/com/google/i18n/phonenumbers/PhoneNumberUtil.html#getRegionCodeForCountryCode-int-">...</a>
 *
 * 	Utility for international phone numbers. Functionality includes formatting, parsing and validation.
 *	If you use this library, and want to be notified about important changes, please sign up to our mailing list.
 *	NOTE: A lot of methods in this class require Region Code strings.
 *	These must be provided using ISO 3166-1 two-letter country-code format. These should be in upper-case.
 *	The list of the codes can be found here: <a href="http://www.iso.org/iso/country_codes/iso_3166_code_lists/country_names_and_code_elements.htm">...</a>
 *
 *	Author:
 *	Shaopeng Jia
 */
public class LibPhone
{
	/**
	 * 	check for China Mobile Number
	 *	@param	sPhoneNumber	-
	 *	@return	boolean
	 */
	public static boolean isValidChinaMobileNumber( String sPhoneNumber )
	{
		return LibPhone.isValidMobileNumber( sPhoneNumber, "CN" );
	}


	/**
	 *	check for Global Mobile Number
	 *	@param	sPhoneNumber		-
	 *	@param	nCountryCode	-
	 *		https://baike.baidu.com/item/ISO%203166-1
	 *	@return	boolean
	 */
	public static boolean isValidMobileNumber( String sPhoneNumber, int nCountryCode )
	{
		return LibPhone.isValidMobileNumber( sPhoneNumber, LibPhone.getRegionByCountryCode( nCountryCode ) );
	}

	/**
	 *	check for Global Mobile Number
	 *	@param	sPhoneNumber		-
	 *	@param	sCountryRegion	-
	 *		https://baike.baidu.com/item/ISO%203166-1
	 *	@return	boolean
	 */
	public static boolean isValidMobileNumber( String sPhoneNumber, String sCountryRegion )
	{
		if ( StringUtils.isBlank( sPhoneNumber ) )
		{
			return false;
		}
		if ( StringUtils.isBlank( sCountryRegion ) )
		{
			return false;
		}

		boolean bValid = false;

		if ( "CN".equalsIgnoreCase( sCountryRegion ) )
		{
			/**
			 * 运营商号段如下：
			 * 中国联通号码：130、131、132、145（无线上网卡）、155、156、185（iPhone5上市后开放）、186、176（4G号段）、
			 *               175（2015年9月10日正式启用，暂只对北京、上海和广东投放办理）
			 * 中国移动号码：134、135、136、137、138、139、147（无线上网卡）、150、151、152、157、158、159、182、183、187、188、178
			 * 中国电信号码：133、153、180、181、189、177、173、149 虚拟运营商：170、1718、1719
			 * 手机号前3位的数字包括：
			 * 1 :1
			 * 2 :3,4,5,7,8
			 * 3 :0,1,2,3,4,5,6,7,8,9
			 * 总结： 目前java手机号码正则表达式有：
			 * a :"^1[3|4|5|7|8][0-9]\\d{4,8}$"    一般验证情况下这个就可以了
			 * b :"^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,4-9]))\\d{8}$"
			 * Pattern和Matcher详解（字符串匹配和字节码）http://blog.csdn.net/u010700335/article/details/44616451
			 */
			if( 11 == sPhoneNumber.length() )
			{
				String sRegex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16[0-9])|(17[013678])|(18[0,4-9])|(19[0-9]))\\d{8}$";
				Pattern pattern = Pattern.compile( sRegex );
				Matcher matcher = pattern.matcher( sPhoneNumber );
				bValid = matcher.matches();
			}
		}
		else
		{
			//
			//	Use the libphonenumber library to validate Number
			//
			PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
			Phonenumber.PhoneNumber swissNumberProto = null;

			try
			{
				swissNumberProto = phoneUtil.parse( sPhoneNumber, sCountryRegion );
				if ( phoneUtil.isValidNumber( swissNumberProto ) )
				{
					bValid = true;
				}
			}
			catch ( NumberParseException e )
			{
			}
		}

		return bValid;
	}

	public static int getCountryCodeByRegion( String sRegion )
	{
		if ( StringUtils.isBlank( sRegion ) ||
			2 != sRegion.length() )
		{
			return 0;
		}

		//	...
		int nCountryCode = 0;

		try
		{
			nCountryCode = PhoneNumberUtil.getInstance().getCountryCodeForRegion( sRegion );
		}
		catch ( Exception e )
		{
			//	don't care
		}

		return nCountryCode;
	}


	public static String getRegionByCountryCode( int nCountryCode )
	{
		String sRegion = "";

		try
		{
			sRegion = PhoneNumberUtil.getInstance().getRegionCodeForCountryCode( nCountryCode );
		}
		catch ( Exception e )
		{
			//	don't care
		}

		return sRegion;
	}


	/**
	 * 	check if the sRegion is valid
	 *	@param	sRegion		-
	 *	@return	boolean
	 */
	public static boolean isValidRegion( String sRegion )
	{
		if ( StringUtils.isBlank( sRegion ) ||
			2 != sRegion.length() )
		{
			return false;
		}

		//	...
		boolean isValidRegion = false;

		try
		{
			int nCountryCode = PhoneNumberUtil.getInstance().getCountryCodeForRegion( sRegion );
			if ( nCountryCode > 0 )
			{
				isValidRegion = true;
			}
		}
		catch ( Exception e )
		{
		}

		return isValidRegion;
	}
}